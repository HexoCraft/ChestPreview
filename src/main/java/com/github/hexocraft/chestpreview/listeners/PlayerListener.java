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

package com.github.hexocraft.chestpreview.listeners;

import com.github.hexocraft.chestpreview.ChestPreviewApi;
import com.github.hexocraftapi.util.ChestUtil;
import com.github.hexocraftapi.util.PlayerUtil;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * This file is part of ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class PlayerListener implements Listener
{
    /**
     * @param event PlayerInteractEvent
     */
    @EventHandler(priority= EventPriority.NORMAL)
    public void onPlayerInteract(final PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();

        // Test si la création est activée
        if(ChestPreviewApi.isEnable(player) == false)
            return;

        //
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            boolean onChest = ChestUtil.isChest(event.getClickedBlock());
            boolean withChest = PlayerUtil.getItemInHand(player)!=null && ChestUtil.isChest(PlayerUtil.getItemInHand(player));

            // L'utilisateur clique sur un coffre avec un coffre
            if(withChest && onChest)
            {
                Chest chest = ChestUtil.getChest(event.getClickedBlock());
                Chest nearbyChest = ChestUtil.getChestNearby(chest.getLocation());

                // Création d'un chest preview
                ChestPreviewApi.create(chest, event.getPlayer());
                ChestPreviewApi.create(nearbyChest, event.getPlayer());
                ChestPreviewApi.enable(false, player);
            }
        }
    }

}
