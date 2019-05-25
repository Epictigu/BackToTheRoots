package de.epicmc.roots.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import de.epicmc.roots.Main;

public class EVENT_PlayerSwapHandItems implements Listener{
	
	@EventHandler
	public void onHeld(PlayerSwapHandItemsEvent e){
		if(Main.DISABLE_OFF_HAND){
			e.setCancelled(true);
		}
	}
	
}
