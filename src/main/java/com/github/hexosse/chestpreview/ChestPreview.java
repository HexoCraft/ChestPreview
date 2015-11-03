package com.github.hexosse.chestpreview;

import com.github.hexosse.BasePlugin.BasePlugin;
import com.github.hexosse.chestpreview.command.Commands;
import com.github.hexosse.chestpreview.configuration.Config;
import com.github.hexosse.chestpreview.configuration.Messages;
import com.github.hexosse.chestpreview.events.ChestListener;
import com.github.hexosse.chestpreview.events.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ChestPreview extends BasePlugin
{
    public Config config = new Config(getDataFolder(), "config.yml");
    public Messages messages = new Messages(getDataFolder(), "message.yml");

    private boolean createChest = false;


    // Activation du plugin
    @Override
    public void onEnable()
    {
        /* Chargement de la config */
        config.load();
        messages.load();

        /* Enregistrement des listeners */
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChestListener(this), this);

        /* Enregistrement du gestionnaire de commandes */
        this.getCommand("cp").setExecutor(new Commands(this));
        this.getCommand("cp").setTabCompleter(new Commands(this));

        /* Updater */
        //if(this.getConfig().getBoolean("plugin.useUpdater"))
        //    RunUpdater(this.getConfig().getBoolean("plugin.downloadUpdate"));

        /* Metrics */
        //if(this.getConfig().getBoolean("plugin.useMetrics"))
        //    RunMetrics();
    }


    // Désactivation du plugin
    @Override
    public void onDisable()
    {
        super.onDisable();
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
        if(createChest)
            pluginLogger.help(ChatColor.AQUA + messages.chatPrefix + ChatColor.WHITE +" " +  messages.Active, player);
        else
            pluginLogger.help(ChatColor.AQUA + messages.chatPrefix + ChatColor.WHITE +" " +  messages.Inactive, player);
    }

    /**
     * @param chest The chest to test
     * @return true if the chest is a chest preview
     */
    public boolean isChestPreview(Chest chest)
    {
        return config.chests.contains(chest.getLocation());
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
}
