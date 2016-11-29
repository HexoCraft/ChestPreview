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

import com.github.hexocraft.chestpreview.ChestPreviewApi;
import com.github.hexocraft.chestpreview.ChestPreviewPlugin;
import com.github.hexocraft.chestpreview.configuration.Permissions;
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.message.Sentence;
import com.github.hexocraftapi.message.predifined.line.Title;
import com.github.hexocraftapi.message.predifined.message.EmptyMessage;
import com.github.hexocraftapi.message.predifined.message.SimpleMessage;
import com.github.hexocraftapi.message.predifined.message.TitleMessage;
import com.github.hexocraftapi.util.LocationUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CpCommandList extends Command<ChestPreviewPlugin>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CpCommandList(ChestPreviewPlugin plugin)
    {
        super("list", plugin);
        this.setAliases(Lists.newArrayList("l"));
        this.setDescription(StringUtils.join(plugin.messages.cList,"\n"));
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
        if(ChestPreviewApi.count() == 0)
            return true;

		// Empty
		EmptyMessage.toSender(commandInfo.getPlayer());

		// Title line
		Title title = new Title('-', ChatColor.AQUA, new Sentence(plugin.messages.mList, ChatColor.YELLOW));
		TitleMessage.toPlayer(commandInfo.getPlayer(), title);

		//
		for(Location location : plugin.config.chests)
			SimpleMessage.toPlayer(commandInfo.getPlayer(), LocationUtil.toReadableString(location));

		return true;
    }
}
