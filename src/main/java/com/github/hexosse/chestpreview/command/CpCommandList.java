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

package com.github.hexosse.chestpreview.command;

import com.github.hexosse.chestpreview.ChestPreview;
import com.github.hexosse.chestpreview.configuration.Permissions;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.MessageManager;
import com.github.hexosse.pluginframework.pluginapi.message.MessageSeverity;
import com.github.hexosse.pluginframework.pluginapi.message.predifined.Line;
import com.github.hexosse.pluginframework.utilapi.LocationUtil;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CpCommandList extends PluginCommand<ChestPreview>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CpCommandList(ChestPreview plugin)
    {
        super("List", plugin);
        this.setAliases(Lists.newArrayList("list", "ls"));
        this.setDescription(StringUtils.join(plugin.messages.helpList,"\n"));
        this.setPermission(Permissions.ADMIN.toString());
    }

    /**
     * Executes the given command, returning its success
     *
     * @param commandInfo Info about the command
     *
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandInfo commandInfo)
    {
        if(plugin.config.chests.size()==0)
            return true;

		MessageManager mm = messageManager;
		MessageSeverity ms = new MessageSeverity(Level.INFO, ChatColor.YELLOW);
		final Player player = commandInfo.getPlayer();

		mm.send(player, new Line(ms));
		mm.send(player, new Message(ms).add(plugin.getDescription().getName() + " list (" + String.valueOf(plugin.config.chests.size()) + ")"));
		for(Location location : plugin.config.chests)
			mm.send(player, new Message(LocationUtil.locationToString(location)));
		mm.send(player, new Line(ms));

		return true;
    }
}
