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

package com.github.hexocraft.chestpreview.configuration;

import com.github.hexocraftapi.configuration.Configuration;
import com.github.hexocraftapi.configuration.annotation.ConfigFooter;
import com.github.hexocraftapi.configuration.annotation.ConfigHeader;
import com.github.hexocraftapi.configuration.annotation.ConfigPath;
import com.github.hexocraftapi.configuration.annotation.ConfigValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */

@ConfigHeader(comment = {
"# ===--- ChestPreview ---------------------------------------------------------------------------------------------=== #",
"#                                                                                                                      ",
"#     Add preview chest to you server                                                                                  ",
"#                                                                                                                      ",
"# ===------------------------------------------------------------------------------------------ © 2016 Hexosse ---=== #"
})
@ConfigFooter(comment = {
" ",
"# ===--- Enjoy -------------------------------------------------------------------------------- © 2016 Hexosse ---=== #"
})
public class Messages extends Configuration
{
    /* Chat */
    @ConfigPath(path = "chat")
    @ConfigValue(path = "chat.prefix")              public String chatPrefix;

    /* Commands */
    @ConfigPath(path = "commands", 		comment = "List of Messages used in commands")
    @ConfigValue(path = "commands.help.cmd")		public List<String>   cHelp    = Arrays.asList("Display ChestPreview help");
    @ConfigValue(path = "commands.reload.cmd")		public List<String>   cReload  = Arrays.asList("Reload ChestPreview");
    @ConfigValue(path = "commands.create.cmd")		public List<String>   cCreate  = Arrays.asList("Enable chest preview creation!", " use /chestpreview create, then place your chest", " or right click an existing chest with a chest", " in your hand.");
    @ConfigValue(path = "commands.list.cmd")		public List<String>   cList    = Arrays.asList("List all chest preview");

    /* Messages */
    @ConfigPath(path = "messages")
    @ConfigValue(path = "messages.list")			public String mList      = "List of chest preview";
    @ConfigValue(path = "messages.Active")          public String mActive = "ChestPreview creation is active";
    @ConfigValue(path = "messages.Inactive")        public String mInactive = "ChestPreview creation is inactive";
    @ConfigValue(path = "messages.Created")         public String mCreated = "ChestPreview created at location";
    @ConfigValue(path = "messages.Destroyed")       public String mDestroyed = "ChestPreview destroyed at location";

    /* Success */
    @ConfigPath(path = "success", 		comment = "List of Messages used after a sucess command")
    @ConfigValue(path = "success.relaod")		    public String sReload = "ChestPreview has been reloaded";


    public Messages(JavaPlugin plugin, String fileName, boolean load)
    {
        super(plugin, fileName);

        if(load) load();
    }
}
