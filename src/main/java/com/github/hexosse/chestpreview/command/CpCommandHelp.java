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
import com.github.hexosse.pluginframework.pluginapi.command.predifined.CommandHelp;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CpCommandHelp extends CommandHelp<ChestPreview>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CpCommandHelp(ChestPreview plugin)
    {
        super(plugin);
        this.removeArgument("page");
        this.setPermission(Permissions.ADMIN.toString());
    }
}
