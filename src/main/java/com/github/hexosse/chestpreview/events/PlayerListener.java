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

package com.github.hexosse.chestpreview.events;

import com.github.hexosse.chestpreview.ChestPreview;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.utilapi.ChestUtil;
import com.github.hexosse.pluginframework.utilapi.LocationUtil;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class PlayerListener extends PluginListener<ChestPreview>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public PlayerListener(ChestPreview plugin)
    {
        super(plugin);
    }

    /**
     * @param event PlayerInteractEvent
     */
    @EventHandler(priority= EventPriority.NORMAL)
    public void onPlayerInteract(final PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();

        // Test si la création est activée
        if(plugin.isActive() == false)
            return;

        //
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            // L'utilisateur clique sur un coffre avec un coffre
            if(ChestUtil.isChest(event.getClickedBlock()) && player.getItemInHand()!=null && ChestUtil.isChest(player.getItemInHand()))
            {
                Chest chest = ChestUtil.getChest(event.getClickedBlock());

                // Sauvegarde du chestpreview
                plugin.addChestPreview(chest);

                // Message
                Message message = new Message();
                message.setPrefix(plugin.messages.chatPrefix);
                message.add(plugin.messages.created + " " +  LocationUtil.locationToString(chest.getLocation()));
                messageManager.send(event.getPlayer(), message);

                // Fin de la création
                plugin.setActive(false, event.getPlayer());
            }
        }
    }

}
