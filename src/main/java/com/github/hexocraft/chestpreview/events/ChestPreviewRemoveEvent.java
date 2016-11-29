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

package com.github.hexocraft.chestpreview.events;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class ChestPreviewRemoveEvent extends Event implements Cancellable
{
	private static final HandlerList handlers    = new HandlerList();
	private              boolean     isCancelled = false;

	private final Player player;
	private final Chest  chest;

	public ChestPreviewRemoveEvent(Player player, Chest chest)
	{
		this.player = player;
		this.chest = chest;
	}

	public Player getPlayer() { return player; }
	public Chest getChest() { return chest; }
	public Location getLocation() { return chest.getLocation(); }

	@Override
	public HandlerList getHandlers() { return handlers; }

	public static HandlerList getHandlerList() { return handlers; }

	@Override
	public boolean isCancelled() { return this.isCancelled; }

	@Override
	public void setCancelled(boolean cancelled) { this.isCancelled = cancelled; }
}
