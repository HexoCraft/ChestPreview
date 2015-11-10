package com.github.hexosse.chestpreview.configuration;

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

import com.github.hexosse.baseplugin.config.BaseConfig;
import com.github.hexosse.baseplugin.config.Location.LocationList;
import com.github.hexosse.chestpreview.ChestPreview;

import java.io.File;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */

@BaseConfig.ConfigHeader(comment = {
        "############################################################",
        "# | ChestPreview by hexosse                              | #",
        "############################################################"
})
@BaseConfig.ConfigFooter(comment = {
        " ",
        " ",
        "############################################################"
})

public class Config extends BaseConfig<ChestPreview>
{
    /* Plugin */
    @ConfigComment(path = "plugin")
    @ConfigOptions(path = "plugin.useMetrics")
    public boolean useMetrics = (boolean) true;
    @ConfigOptions(path = "plugin.useUpdater")
    public boolean useUpdater = (boolean) true;
    @ConfigOptions(path = "plugin.downloadUpdate")
    public boolean downloadUpdate = (boolean) true;

    /* Message */
    @ConfigOptions(path = "messages")
    public String messages = "messages.yml";

    /* Chest */
    @ConfigOptions(path = "chest.list")
    public LocationList chests = new LocationList();


    public Config(ChestPreview plugin, File dataFolder, String filename)
    {
        super(plugin, new File(dataFolder, filename), filename);
    }

    public void reloadConfig() {
        load();
    }
}
