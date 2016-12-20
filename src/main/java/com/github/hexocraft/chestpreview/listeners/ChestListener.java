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
import com.github.hexocraft.chestpreview.configuration.Permissions;
import com.github.hexocraftapi.util.ChestUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Iterator;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ChestListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Block block = event.getBlock();
        Material type = block.getType();
        Location location = block.getLocation();

        if(type == Material.HOPPER)
        {
            if (ChestPreviewApi.isChestPreviewArround(location)) {
                event.setCancelled(true);
                return;
            }
        }
        else if(type == Material.RAILS || type == Material.POWERED_RAIL || type == Material.DETECTOR_RAIL || type == Material.ACTIVATOR_RAIL)
        {
            if (ChestPreviewApi.isChestPreviewArround(location)) {
                event.setCancelled(true);
                return;
            }
        }
        else if(ChestUtil.isChest(block))
        {
            final Player player = event.getPlayer();

            // Récupère le Chest
            Chest chest = (Chest)event.getBlockPlaced().getState();

            // Test si on cherche à mettre un coffre à coté d'un autre coffre
            Chest nearbyChest = ChestUtil.getChestNearby(chest.getLocation());

            // Test si il s'agit d'un chest preview
            boolean isChestPreview = nearbyChest != null ? ChestPreviewApi.isChestPreview(nearbyChest) : false;

            // Test si la création du chest est autorisée
            if(nearbyChest != null && !ChestPreviewApi.isEnable(player))
            {
                event.setCancelled(true);
                return;
            }

            // Test si la création est activée
            if(ChestPreviewApi.isEnable(player) == false)
                return;

            // Création d'un chest preview
            ChestPreviewApi.create(chest, player);
            ChestPreviewApi.enable(false, player);
        }
    }


    /**
     * @param event BlockBreakEvent
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event)
    {
        // Récupère le Chest
        Chest chest = ChestUtil.getChest(event.getBlock());
        if(chest == null) return;

        // Test si le Chest est un chest preview
        if(ChestPreviewApi.isChestPreview(chest) == false)
            return;

        // Test si le joueur à la permission de détruire le coffre
        if(!Permissions.has(event.getPlayer(), Permissions.ADMIN))
        {
            event.setCancelled(true);
            return;
        }

        // Si oui, supprimer de la liste des coffres
        ChestPreviewApi.remove(chest, event.getPlayer());
    }


    /**
     * Cette évènement est appellé lorqu'un item bouge dans l'inventaire
     * Cependant elle n'est pas appelée lorsqu'un joueur intéragit
     * <p/>
     * Cette évènemest est par exemple appelé lors qu'un item passe
     * d'un entonoir à un coffre.
     *
     * @param event InventoryMoveItemEvent
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onInventoryMoveItem(InventoryMoveItemEvent event)
    {
        InventoryHolder holderSource = event.getSource().getHolder();
        InventoryHolder holderDestination = event.getDestination().getHolder();

        // Récupère le Chest
        Chest chestSource = ChestUtil.getChest(holderSource);
        DoubleChest doubleChestSource = ChestUtil.getDoubleChest(holderSource);
        Chest chestDestination = ChestUtil.getChest(holderDestination);
        DoubleChest doubleChestDestination = ChestUtil.getDoubleChest(holderDestination);



        // Empêche un item de sortir du coffre
        if(chestSource != null && ChestPreviewApi.isChestPreview(chestSource) == true)
        {
            event.setCancelled(true);
            return;
        }
        if(doubleChestSource != null && ChestPreviewApi.isChestPreview(ChestUtil.getChest(doubleChestSource.getLeftSide())) == true)
        {
            event.setCancelled(true);
            return;
        }

        // Empêche un item de rentrer du coffre
        if(chestDestination != null && ChestPreviewApi.isChestPreview(chestDestination) == true)
        {
            event.setCancelled(true);
            return;
        }
        if(doubleChestDestination != null && ChestPreviewApi.isChestPreview(ChestUtil.getChest(doubleChestDestination.getLeftSide())) == true)
        {
            event.setCancelled(true);
            return;
        }
    }


    /**
     * Cette évènement est appellé lorqu'un joueur intéragit avec l'inventaire
     *
     * @param event InventoryClickEvent
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onInventoryClickEvent(InventoryClickEvent event)
    {
        InventoryHolder holder = event.getInventory().getHolder();
        Player player = (Player)event.getWhoClicked();

        boolean isChest = ChestUtil.isChest(holder);
        boolean isDoubleChest = ChestUtil.isDoubleChest(holder);

        // Test si il s'agit d'un coffre
        if(!isChest && !isDoubleChest) return;

        // Test si le Chest est un chest preview
        if(isChest && ChestPreviewApi.isChestPreview(ChestUtil.getChest(holder), player) == false)
            return;
        if(isDoubleChest && ChestPreviewApi.isChestPreview(ChestUtil.getChest(ChestUtil.getDoubleChest(holder).getLeftSide()), player) == false)
            return;

        // Test si le joueur à la permission de détruire le coffre
        if(!Permissions.has(player, Permissions.ADMIN))
            event.setCancelled(true);
    }


    /**
     * Cette évènement est appellé lorqu'une entité explose
     *
     * @param event EntityExplodeEvent
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onEntityExplodeEvent(EntityExplodeEvent event)
    {
        Iterator<Block> iter = event.blockList().iterator();
        while (iter.hasNext())
        {
            Block block = iter.next();
            if(ChestUtil.isChest(block) && ChestPreviewApi.isChestPreview(ChestUtil.getChest(block)))
                iter.remove();
            else if(ChestPreviewApi.isChestPreview(ChestUtil.getChestNearby(block)))
                iter.remove();
        }
    }


    /**
     * Cette évènement est appellé lorqu'un block explose
     *
     * @param event BlockExplodeEvent
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onEntityExplodeEvent(BlockExplodeEvent event)
    {
        Iterator<Block> iter = event.blockList().iterator();
        while (iter.hasNext())
        {
            Block block = iter.next();
            if(ChestUtil.isChest(block) && ChestPreviewApi.isChestPreview(ChestUtil.getChest(block)))
                iter.remove();
            else if(ChestPreviewApi.isChestPreview(ChestUtil.getChestNearby(block)))
                iter.remove();
        }
    }
}
