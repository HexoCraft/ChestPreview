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

package com.github.hexocraft.chestpreview;

import com.github.hexocraft.chestpreview.command.CpCommands;
import com.github.hexocraft.chestpreview.configuration.Config;
import com.github.hexocraft.chestpreview.configuration.Messages;
import com.github.hexocraft.chestpreview.listeners.ChestListener;
import com.github.hexocraft.chestpreview.listeners.ChestPreviewListener;
import com.github.hexocraft.chestpreview.listeners.PlayerListener;
import com.github.hexocraftapi.message.Line;
import com.github.hexocraftapi.message.predifined.message.PluginMessage;
import com.github.hexocraftapi.message.predifined.message.PluginTitleMessage;
import com.github.hexocraftapi.plugin.Plugin;
import com.github.hexocraftapi.updater.GitHubUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ChestPreviewPlugin extends Plugin
{
    public static ChestPreviewPlugin instance = null;
    public static Config             config   = null;
    public static Messages           messages = null;


    // Activation du plugin
    @Override
    public void onEnable()
    {
		/* Instance du plugin */
        instance = this;

        /* Chargement de la config */
        config = new Config(this, "config.yml", true);
        messages = new Messages(this, config.messages, true);

        /* Enregistrement du gestionnaire de commandes */
        registerCommands(new CpCommands(this));

        /* Enregistrement des listeners */
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChestListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChestPreviewListener(), this);

		/* Enable message */
        PluginTitleMessage titleMessage = new PluginTitleMessage(this, "ChestPreview is enable ...", ChatColor.YELLOW);
        titleMessage.send(Bukkit.getConsoleSender());

        /* Updater */
        if(config.useUpdater)
            runUpdater(getServer().getConsoleSender(), 20 * 10);

        /* Metrics */
        if(config.useMetrics)
            runMetrics(20 * 2);
    }


    // Désactivation du plugin
    @Override
    public void onDisable()
    {
        ChestPreviewApi.disable();

        super.onDisable();

        PluginMessage.toConsole(this, "Disabled", ChatColor.RED, new Line("ChestPreview is now disabled", ChatColor.DARK_RED));
    }

    public void runUpdater(final CommandSender sender, int delay)
    {
        super.runUpdater(new GitHubUpdater(this, "HexoCraft/ChestPreview"), sender, delay);
    }

    private void runMetrics(int delay)
    {
        super.RunMetrics(delay);
    }
}
