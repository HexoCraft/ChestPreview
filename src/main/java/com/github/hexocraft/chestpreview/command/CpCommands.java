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
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandInfo;
import com.google.common.collect.Lists;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CpCommands extends Command<ChestPreviewPlugin>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CpCommands(ChestPreviewPlugin plugin)
    {
        super("ChestPreview", plugin);
        this.setAliases(Lists.newArrayList("cp"));

        this.addSubCommand(new CpCommandHelp(plugin));
        this.addSubCommand(new CpCommandCreate(plugin));
        this.addSubCommand(new CpCommandRename(plugin));
        this.addSubCommand(new CpCommandList(plugin));
        this.addSubCommand(new CpCommandReload(plugin));
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
        plugin.getServer().dispatchCommand(commandInfo.getSender(), "ChestPreview help");

        return true;
    }
}
