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
import com.github.hexocraft.chestpreview.configuration.Permissions;
import com.github.hexocraftapi.command.predifined.CommandHelp;
import org.apache.commons.lang.StringUtils;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CpCommandHelp extends CommandHelp<ChestPreviewPlugin>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CpCommandHelp(ChestPreviewPlugin plugin)
    {
        super(plugin);
        this.setDescription(StringUtils.join(plugin.messages.cHelp,"\n"));
        this.setPermission(Permissions.ADMIN.toString());
        this.setDisplayInlineDescription(true);
        this.removeArgument("page");
    }
}
