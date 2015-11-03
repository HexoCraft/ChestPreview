package com.github.hexosse.chestpreview.events;

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

import com.github.hexosse.baseplugin.event.BaseListener;
import com.github.hexosse.baseplugin.utils.ChestUtils;
import com.github.hexosse.chestpreview.ChestPreview;
import com.github.hexosse.chestpreview.configuration.Permissions;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ChestListener extends BaseListener<ChestPreview>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public ChestListener(ChestPreview plugin)
    {
        super(plugin);
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        // Récupère le Chest
        Chest chest = ChestUtils.getChest(event.getBlockPlaced());
        if(chest == null) return;

        // Test si on cherche à mettre un coffre à coté d'un autre coffre
        Chest nearbyChest = ChestUtils.getChestNearby(chest.getLocation());

        // Test si il s'agit d'un chest preview
        boolean isChestPreview = nearbyChest != null ? plugin.isChestPreview(nearbyChest) : false;

        // Test si la création du chest est autorisée
        if(nearbyChest != null && !(isChestPreview == plugin.isActive()))
        {
            event.setCancelled(true);
            return;
        }

        // Test si la création est activée
        if(plugin.isActive() == false)
            return;

        // Sauvegarde du chestpreview
        plugin.addChestPreview(chest);

        // Fin de la création
        plugin.setActive(false,event.getPlayer());
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onChestCombine(BlockCanBuildEvent event)
    {
        // Récupère le Chest
        Chest chest = ChestUtils.getChest(event.getBlock());
        if(chest == null) return;

    }


    /**
     * @param event BlockBreakEvent
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event)
    {
        // Récupère le Chest
        Chest chest = ChestUtils.getChest(event.getBlock());
        if(chest == null) return;

        // Test si le Chest est un ChestPreview
        if(plugin.isChestPreview(chest) == false)
            return;

        // Test si le joueur à la permission de détruire le coffre
        if(!Permissions.has(event.getPlayer(), Permissions.ADMIN))
            event.setCancelled(true);

        // Si oui, supprimer de la liste des coffres
        plugin.removeChestPreview(chest);
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
        Chest chestSource = ChestUtils.getChest(holderSource);
        Chest chestDestination = ChestUtils.getChest(holderDestination);

        // Empêche un item de sortir du coffre
        if(chestSource != null && plugin.isChestPreview(chestSource) == true)
        {
            event.setCancelled(true);
            return;
        }

        // Empêche un item de rentrer du coffre
        if(chestDestination != null && plugin.isChestPreview(chestDestination) == true)
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

        // Test si il s'agit d'un coffre
        if(ChestUtils.isChest(holder) == false && ChestUtils.isDoubleChest(holder) == false) return;

        // Test si le Chest est un ChestPreview
        if(ChestUtils.isChest(holder) == true && plugin.isChestPreview(ChestUtils.getChest(holder)) == false)
            return;
        if(ChestUtils.isDoubleChest(holder) == true && plugin.isChestPreview(ChestUtils.getChest(ChestUtils.getDoubleChest(holder).getLeftSide())) == false)
            return;

        // Test si le joueur à la permission de détruire le coffre
        if(!Permissions.has(event.getWhoClicked(), Permissions.ADMIN))
            event.setCancelled(true);
    }
}
