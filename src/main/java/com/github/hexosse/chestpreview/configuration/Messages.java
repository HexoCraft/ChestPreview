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
import com.github.hexosse.pluginframework.pluginapi.config.PluginConfig;

import java.io.File;
import java.util.List;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */

@PluginConfig.ConfigHeader(comment = {
        "############################################################",
        "# | ChestPreview by hexosse                              | #",
        "############################################################"
})
@PluginConfig.ConfigFooter(comment = {
        " ",
        " ",
        "############################################################"
})

public class Messages extends PluginConfig<ChestPreview>
{
    /* Chat */
    @ConfigComment(path = "chat")
    @ConfigOptions(path = "chat.prefix")
    public String chatPrefix;

    /* Help */
    @ConfigComment(path = "help")
    @ConfigOptions(path = "help.HelpCreate")
    public List<String> helpCreate;
    @ConfigOptions(path = "help.HelpList")
    public List<String> helpList;
    @ConfigOptions(path = "help.HelpReload")
    public List<String> helpReload;

    /* Messages */
    @ConfigComment(path = "messages")
    @ConfigOptions(path = "messages.Active")
    public String active;
    @ConfigComment()
    @ConfigOptions(path = "messages.Inactive")
    public String inactive;
    @ConfigComment()
    @ConfigOptions(path = "messages.Reloaded")
    public String reloaded;
    @ConfigComment()
    @ConfigOptions(path = "messages.Created")
    public String created;
    @ConfigComment()
    @ConfigOptions(path = "messages.Destroyed")
    public String destroyed;


    public Messages(ChestPreview plugin, File dataFolder, String filename)
    {
        super(plugin, new File(dataFolder, filename), filename);
    }

    public void reloadConfig() {
        load();
    }
}
