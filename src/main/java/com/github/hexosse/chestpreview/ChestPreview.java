/*
 * Copyright 2016 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.hexosse.chestpreview;

import com.github.hexosse.chestpreview.command.CpCommands;
import com.github.hexosse.chestpreview.configuration.Config;
import com.github.hexosse.chestpreview.configuration.Messages;
import com.github.hexosse.chestpreview.events.ChestListener;
import com.github.hexosse.chestpreview.events.PlayerListener;
import com.github.hexosse.githubupdater.GitHubUpdater;
import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.metric.MetricsLite;
import com.github.hexosse.pluginframework.utilapi.ChestUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ChestPreview extends Plugin
{
    public Config config = null;
    public Messages messages = null;
    private String repository = "hexosse/ChestPreview";

    private boolean createChest = false;


    // Activation du plugin
    @Override
    public void onEnable()
    {
        /* Chargement de la config */
        config = new Config(this, getDataFolder(), "config.yml");           config.load();
        messages = new Messages(this, getDataFolder(), config.messages);     messages.load();

        /* Enregistrement des listeners */
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChestListener(this), this);

        /* Enregistrement du gestionnaire de commandes */
        registerCommands(new CpCommands(this));

        /* Updater */
        if(config.useUpdater)
            RunUpdater(config.downloadUpdate);

        /* Metrics */
        if(config.useMetrics)
            RunMetrics();

		/* Console message */
		Message message = new Message();
        message.setPrefix("§3[§b" + this.getDescription().getName() + " " + this.getDescription().getVersion() + "§3]§r");
        message.add(new Message(ChatColor.YELLOW, "Enable"));
		messageManager.send(Bukkit.getConsoleSender(), message);
    }


    // Désactivation du plugin
    @Override
    public void onDisable()
    {
        super.onDisable();

		/* Console message */
		Message message = new Message();
        message.setPrefix("§3[§b" + this.getDescription().getName() + " " + this.getDescription().getVersion() + "§3]§r");
		message.add(new Message(ChatColor.YELLOW, "Disabled"));
		messageManager.send(Bukkit.getConsoleSender(), message);
    }


    /**
     * Tells if the plugin is enable or not
     *
     * @return boolean
     */
    public boolean isActive()
    {
        return createChest;
    }

    /**
     * @param createChest Enable or Disable the plugin
     * @param player the player which are creating the ChestPreview
     */
    public void setActive(boolean createChest, Player player)
    {
        this.createChest = createChest;

		// Message
		Message message = new Message();
		message.setPrefix(this.messages.chatPrefix);
		message.add(createChest ? messages.active : messages.inactive);
		messageManager.send(player, message);    }

    /**
     * @param chest The chest to test
     * @return true if the chest is a chest preview
     */
    public boolean isChestPreview(Chest chest)
    {
        String worldChest = chest.getWorld().getName();

        if(config.worlds.contains(worldChest))              return true;
        if(config.chests.contains(chest.getLocation()))     return true;

        return false;
    }

    /**
     * @param chest The chest to test
     * @param player The player to test
     * @return true if the chest is a chest preview
     */
    public boolean isChestPreview(Chest chest, Player player)
    {
        if(isChestPreview(chest))
            return true;

        String worldChest = chest.getWorld().getName();

        if(player.getGameMode()==GameMode.CREATIVE && config.creativeWorlds.contains(worldChest))
            return true;
        if(player.getGameMode()==GameMode.SURVIVAL && config.survivalWorlds.contains(worldChest))
            return true;
        if(player.getGameMode()==GameMode.ADVENTURE && config.adventureWorlds.contains(worldChest))
            return true;
        if(player.getGameMode()==GameMode.SPECTATOR && config.spectatorWorlds.contains(worldChest))
            return true;

        return false;
    }

    /**
     * @param location The location to test
     * @return true if a chest preview exist arround the location
     */
    public boolean isChestPreviewArround(Location location)
    {
        // Test si il existe un ChestPreview autour de la location
        Chest nearbyChest = ChestUtil.getChestNearby(location);
        // Test si il s'agit d'un chest preview
        return nearbyChest != null ? isChestPreview(nearbyChest) : false;
    }

    /**
     * @param chest The chest to add
     */
    public void addChestPreview(Chest chest)
    {
        config.chests.add(chest.getLocation());
        config.save();
    }

    /**
     * @param chest The chest to remove
     */
    public void removeChestPreview(Chest chest)
    {
        config.chests.remove(chest.getLocation());
        config.save();
    }

     public void RunUpdater(final boolean download)
    {
        GitHubUpdater updater = new GitHubUpdater(this, this.repository, this.getFile(), download?GitHubUpdater.UpdateType.DEFAULT:GitHubUpdater.UpdateType.NO_DOWNLOAD, true);
    }

    /**
     * Run metrics
     */
    private void RunMetrics()
    {
        try
        {
            MetricsLite metrics = new MetricsLite(this);
            if(metrics.start())
                pluginLogger.info("Succesfully started Metrics, see http://mcstats.org for more information.");
            else
                pluginLogger.info("Could not start Metrics, see http://mcstats.org for more information.");
        } catch (IOException e){
            // Failed to submit the stats :-(
        }
    }
}
