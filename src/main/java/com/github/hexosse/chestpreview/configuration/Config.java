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

package com.github.hexosse.chestpreview.configuration;

import com.github.hexosse.chestpreview.ChestPreview;
import com.github.hexosse.pluginframework.pluginapi.config.ConfigFile;
import com.github.hexosse.pluginframework.pluginapi.config.Location.LocationList;

import java.io.File;
import java.util.ArrayList;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */

@ConfigFile.ConfigHeader(comment = {
        "############################################################",
        "# | ChestPreview by hexosse                              | #",
        "############################################################"
})
@ConfigFile.ConfigFooter(comment = {
        " ",
        " ",
        "############################################################"
})

public class Config extends ConfigFile<ChestPreview>
{
    /* Plugin */
    @ConfigComment(path = "plugin")
    @ConfigOptions(path = "plugin.useMetrics")              public boolean useMetrics = (boolean) true;
    @ConfigOptions(path = "plugin.useUpdater")              public boolean useUpdater = (boolean) true;
    @ConfigOptions(path = "plugin.downloadUpdate")          public boolean downloadUpdate = (boolean) true;

    /* Message */
    @ConfigOptions(path = "messages")                       public String messages = "messages.yml";

    /* Chest */
    @ConfigOptions(path = "chest.list")                     public LocationList chests = new LocationList();

    /* Worlds */
    @ConfigOptions(path = "worlds.list")                    public ArrayList<String> worlds = new ArrayList<String>();
    @ConfigOptions(path = "creativeWorlds.list")            public ArrayList<String> creativeWorlds = new ArrayList<String>();
    @ConfigOptions(path = "survivalWorlds.list")            public ArrayList<String> survivalWorlds = new ArrayList<String>();
    @ConfigOptions(path = "adventureWorlds.list")           public ArrayList<String> adventureWorlds = new ArrayList<String>();
    @ConfigOptions(path = "spectatorWorlds.list")           public ArrayList<String> spectatorWorlds = new ArrayList<String>();


    public Config(ChestPreview plugin, File dataFolder, String filename)
    {
        super(plugin, new File(dataFolder, filename), filename);
    }

    public void reloadConfig() {
        load();
    }
}
