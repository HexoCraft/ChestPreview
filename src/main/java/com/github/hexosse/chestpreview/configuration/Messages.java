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

public class Messages extends BaseConfig<ChestPreview>
{
    /* Chat */
    @ConfigComment(path = "chat")
    @ConfigOptions(path = "chat.prefix")
    public String chatPrefix;

    /* Help */
    @ConfigComment(path = "help")
    @ConfigOptions(path = "help.HelpCreate")
    public String helpCreate;
    @ConfigOptions(path = "help.HelpReload")
    public String helpReload;

    /* Errors */
    @ConfigComment(path = "errors")
    @ConfigOptions(path = "errors.AccesDenied")
    public String AccesDenied;

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
