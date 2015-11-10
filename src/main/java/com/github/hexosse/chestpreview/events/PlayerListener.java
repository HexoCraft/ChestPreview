package com.github.hexosse.chestpreview.events;

import com.github.hexosse.baseplugin.event.BaseListener;
import com.github.hexosse.baseplugin.utils.LocationUtil;
import com.github.hexosse.baseplugin.utils.block.ChestUtil;
import com.github.hexosse.chestpreview.ChestPreview;
import org.bukkit.ChatColor;
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
public class PlayerListener extends BaseListener<ChestPreview>
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
                pluginLogger.help(ChatColor.AQUA + plugin.messages.chatPrefix + ChatColor.WHITE + " " +  plugin.messages.created + " " +  LocationUtil.locationToString(chest.getLocation()), event.getPlayer());

                // Fin de la création
                plugin.setActive(false, event.getPlayer());
            }
        }
    }

}
