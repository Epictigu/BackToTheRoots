package de.epicmc.roots.events;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EVENT_PlayerQuit implements Listener{

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
	}
	
}
