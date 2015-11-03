package com.github.hexosse.chestpreview.command;

/*
 * Copyright 2015 hexosse
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

import com.github.hexosse.BasePlugin.command.BaseCommand;
import com.github.hexosse.chestpreview.ChestPreview;
import com.github.hexosse.chestpreview.configuration.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class CommandReload extends BaseCommand<ChestPreview>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CommandReload(ChestPreview plugin)
    {
        super(plugin);
    }

    /**
     * Abstarct metode
     */
    @Override
    public void execute(CommandSender sender)
    {
        final Player player = (sender instanceof Player) ? (Player)sender : null;

        if(!Permissions.has(sender, Permissions.ADMIN))
        {
            pluginLogger.help(ChatColor.RED +plugin.messages.AccesDenied, player);
            return;
        }

        new BukkitRunnable()
        {
            @Override
            public void run()
            {

                plugin.config.reloadConfig();
                plugin.messages.reloadConfig();

                pluginLogger.info(plugin.messages.Reloaded);
                pluginLogger.help(ChatColor.RED + plugin.messages.Reloaded, player);

            }

        }.runTask(plugin);
    }
}
