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
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */

@ConfigHeader(comment = {
"# ===--- ChestPreview---------------------------------------------------------------------------------------------=== #",
"#                                                                                                                      ",
"#     Add preview chest to you server                                                                                  ",
"#                                                                                                                      ",
"# ===------------------------------------------------------------------------------------------ © 2016 Hexosse ---=== #"
})
@ConfigFooter(comment = {
" ",
"# ===--- Enjoy -------------------------------------------------------------------------------- © 2016 Hexosse ---=== #"
})
public class Config extends Configuration
{
    /* Plugin */
    @ConfigPath(path = "plugin", comment = "*---------------- Plugin configuration --------------------*")
    @ConfigValue(path = "plugin.useMetrics", comment = "Enable metrics")            public boolean useMetrics     = (boolean) true;
    @ConfigValue(path = "plugin.useUpdater", comment = "Enable updater")            public boolean useUpdater     = (boolean) true;
    @ConfigValue(path = "plugin.downloadUpdate", comment = "Download update")       public boolean downloadUpdate = (boolean) true;

    /* Message */
    @ConfigValue(path = "messages", comment = {"*----------------- Messages configuration -----------------*", "You can use the default messages provided or translate as you whish."})
    public String messages = "messages.yml";

    /* Chest */
    @ConfigValue(path = "chest.list", comment = {"*--------------------- Chest location ---------------------*", "List all chest location's."})
    public ArrayList<Location> chests = new ArrayList<>();

    /* Worlds */
    @ConfigValue(path = "worlds.list", comment = {"*------------------ Worlds Chest Preview ------------------*"
                                                , "all chests present in the worlds listed below will be considered"
                                                , "as chest preview."
                                                , "By doing this your players won't be able to use any chest."
                                                , "worlds:"
                                                , "  list :"
                                                , "  - world"
                                                , "  - world_nether"
                                                , "  - world_the end"})
    public ArrayList<String> worlds = new ArrayList<>();

    @ConfigValue(path = "creativeWorlds.list", comment = {"all chests present in the worlds listed below will be considered"
                                                , "as chest preview for all player in CREATIVE game mode."
                                                , "creativeWorlds:"
                                                , "  list :"
                                                , "  - world"
                                                , "  - world_nether"
                                                , "  - world_the end"})
    public ArrayList<String> creativeWorlds = new ArrayList<>();


    @ConfigValue(path = "survivalWorlds.list", comment = {"all chests present in the worlds listed below will be considered"
                                                , "as chest preview for all player in SURVIVAL game mode."
                                                , "creativeWorlds:"
                                                , "  list :"
                                                , "  - world"
                                                , "  - world_nether"
                                                , "  - world_the end"})
    public ArrayList<String> survivalWorlds = new ArrayList<>();


    @ConfigValue(path = "adventureWorlds.list", comment = {"all chests present in the worlds listed below will be considered"
                                                , "as chest preview for all player in ADVENTURE game mode."
                                                , "creativeWorlds:"
                                                , "  list :"
                                                , "  - world"
                                                , "  - world_nether"
                                                , "  - world_the end"})
    public ArrayList<String> adventureWorlds = new ArrayList<>();


    @ConfigValue(path = "adventureWorlds.list", comment = {"all chests present in the worlds listed below will be considered"
                                                , "as chest preview for all player in SPECTATOR game mode."
                                                , "creativeWorlds:"
                                                , "  list :"
                                                , "  - world"
                                                , "  - world_nether"
                                                , "  - world_the end"})
    public ArrayList<String> spectatorWorlds = new ArrayList<>();


    public Config(JavaPlugin plugin, String fileName, boolean load)
    {
        super(plugin, fileName);

        if(load) load();
    }
}
