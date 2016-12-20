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

package com.github.hexocraft.chestpreview;

import com.github.hexocraft.chestpreview.events.ChestPreviewCreateEvent;
import com.github.hexocraft.chestpreview.events.ChestPreviewRemoveEvent;
import com.github.hexocraft.chestpreview.events.ChestPreviewRenameEvent;
import com.github.hexocraftapi.message.Prefix;
import com.github.hexocraftapi.message.predifined.message.SimplePrefixedMessage;
import com.github.hexocraftapi.util.ChestUtil;
import com.github.hexocraftapi.util.LocationUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.github.hexocraft.chestpreview.ChestPreviewPlugin.*;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class ChestPreviewApi
{
	private static Set<UUID> activePlayers = new HashSet<>();


	// Enable player to create a chest preview
	public static void enable(UUID uuid) { activePlayers.add(uuid); }
	public static void enable(Player player) { enable(player.getUniqueId()); }

	// Disable player from creating a chest preview
	public static void disable(UUID uuid) { activePlayers.remove(uuid); }
	public static void disable(Player player) { disable(player.getUniqueId()); }

	// Disable all player from creating a chest preview
	public static void disable() { activePlayers.clear(); }

	// Check is player is enable
	public static boolean isEnable(UUID uuid) { return activePlayers.contains(uuid); };
	public static boolean isEnable(Player player) { return isEnable(player.getUniqueId()); };

	// Enable or disable if ChestPreview creation
	public static void enable(boolean enable, Player player)
	{
		if(player == null)  return;

		if(enable)
			enable(player);
		else
			disable(player);

		SimplePrefixedMessage.toPlayer(player, new Prefix(messages.chatPrefix), enable ? messages.mActive : messages.mInactive);
	}


	public static int count()
	{
		return config.chests.size();
	}

	// Create a chest preview
	public static void create(Chest chest, Player player)
	{
		if(chest == null)
			return;

		// Event
		ChestPreviewCreateEvent event = new ChestPreviewCreateEvent(player, chest);
		instance.getServer().getPluginManager().callEvent(event);
		if(event.isCancelled())
			return;

		// Create
		create(chest);

		// Send message
		SimplePrefixedMessage.toPlayer(player, new Prefix(messages.chatPrefix), messages.mCreated + " " + LocationUtil.toReadableString(chest.getLocation()));
	}

	// Create a chest preview
	protected static void create(Chest chest)
	{
		config.chests.add(chest.getLocation());
		config.save();
	}

	// Rename a chest preview
	public static void rename(Chest chest, String name, Player player)
	{
		if(chest == null || name.isEmpty()) return;

		// Event
		ChestPreviewRenameEvent event = new ChestPreviewRenameEvent(player, chest);
		instance.getServer().getPluginManager().callEvent(event);
		if(event.isCancelled())
			return;

		// Create
		rename(chest, name);
	}

	// Create a chest preview
	protected static void rename(Chest chest, String name)
	{
		ChestUtil.setName(chest, name);
	}

	// Remove a chest preview
	public static void remove(Chest chest, Player player)
	{
		// Event
		ChestPreviewRemoveEvent event = new ChestPreviewRemoveEvent(player, chest);
		instance.getServer().getPluginManager().callEvent(event);
		if(event.isCancelled())
			return;

		// Remove
		remove(chest);

		// Send message
		SimplePrefixedMessage.toPlayer(player, new Prefix(messages.chatPrefix), messages.mDestroyed + " " + LocationUtil.toReadableString(chest.getLocation()));
	}

	// Remove a chest preview
	protected static void remove(Chest chest)
	{
		config.chests.remove(chest.getLocation());
		config.save();
	}

	// Test if the Chest is a chest preview
	public static boolean isChestPreview(Chest chest)
	{
		if(chest==null)
			return false;

		if(config.worlds.contains(chest.getWorld().getName()))
			return true;
		if(config.chests.contains(chest.getLocation()))
			return true;

		return false;
	}

	// Test if the Chest is a chest preview
	public static boolean isChestPreview(Chest chest, Player player)
	{
		if(isChestPreview(chest))
			return true;

		String world = chest.getWorld().getName();

		if(player.getGameMode() == GameMode.CREATIVE && config.creativeWorlds.contains(world))
			return true;
		if(player.getGameMode()==GameMode.SURVIVAL && config.survivalWorlds.contains(world))
			return true;
		if(player.getGameMode()==GameMode.ADVENTURE && config.adventureWorlds.contains(world))
			return true;
		if(player.getGameMode()==GameMode.SPECTATOR && config.spectatorWorlds.contains(world))
			return true;

		return false;
	}

	// Test if a chest preview exist arround this location
	public static boolean isChestPreviewArround(Location location)
	{
		// Test si il existe un ChestPreview autour de la location
		Chest nearbyChest = ChestUtil.getChestNearby(location);
		// Test si il s'agit d'un chest preview
		return nearbyChest != null ? isChestPreview(nearbyChest) : false;
	}
}
