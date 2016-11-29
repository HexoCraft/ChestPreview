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

package com.github.hexocraft.chestpreview.command;

import com.github.hexocraft.chestpreview.ChestPreviewPlugin;
import com.github.hexocraft.chestpreview.configuration.Config;
import com.github.hexocraft.chestpreview.configuration.Messages;
import com.github.hexocraft.chestpreview.configuration.Permissions;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.command.predifined.CommandReload;
import com.github.hexocraftapi.message.predifined.message.PluginMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.hexocraft.chestpreview.ChestPreviewPlugin.*;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class CpCommandReload extends CommandReload<ChestPreviewPlugin>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CpCommandReload(ChestPreviewPlugin plugin)
    {
        super(plugin, Permissions.ADMIN.toString());
        this.setDescription(StringUtils.join(messages.cReload,"\n"));
    }

    /**
     * Executes the given command, returning its success
     *
     * @param commandInfo Info about the command
     *
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(final CommandInfo commandInfo)
    {
        final Player player = commandInfo.getPlayer();

        super.onCommand(commandInfo);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {

                config = new Config(instance, "config.yml", true);
                messages = new Messages(instance, config.messages, true);

                // Send message
                PluginMessage.toSenders(commandInfo.getSenders(),plugin, plugin.messages.sReload, ChatColor.YELLOW);
            }

        }.runTask(plugin);

        return true;
    }
}
