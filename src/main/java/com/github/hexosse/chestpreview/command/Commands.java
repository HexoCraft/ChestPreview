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

import com.github.hexosse.baseplugin.BaseObject;
import com.github.hexosse.chestpreview.ChestPreview;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Commands extends BaseObject<ChestPreview> implements CommandExecutor, TabCompleter
{
    private CommandHelp cmdHelp = null;
    private CommandReload cmdReload = null;
    private CommandCreate cmdCreate = null;
    private CommandList cmdList = null;

    /**
     * @param plugin The plugin that this object belong to.
     */
    public Commands(ChestPreview plugin)
    {
        super(plugin);
        cmdHelp = new CommandHelp(plugin);
        cmdReload = new CommandReload(plugin);
        cmdCreate = new CommandCreate(plugin);
        cmdList = new CommandList(plugin);
    }

    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player))
            return false;

        if(args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?"))
            cmdHelp.execute(sender);

        else if(args[0].equalsIgnoreCase("reload"))
            cmdReload.execute(sender);

        else if(args[0].equalsIgnoreCase("create"))
            cmdCreate.execute(sender);

        else if(args[0].equalsIgnoreCase("list"))
            cmdList.execute(sender);

        else
            return false;

        return true;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        List<String> list = Arrays.asList("create", "list", "reload", "help");
        List<String> fList = Lists.newArrayList();

        // Retrourne toutes les commandes
        if(args.length==1 && args[0].isEmpty())
            return list;

        // Retrourne les commandes en fonction de ce qui est deja saisi
        if(args.length==1)
        {
            for(String s : list)
                if(s.toLowerCase().startsWith(args[0])) fList.add(s);
        }

        return fList;
    }
}
