package de.epicmc.roots.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class EVENT_PlayerSwapHandItems implements Listener{
	
	@EventHandler
	public void onHeld(PlayerSwapHandItemsEvent e){
		if(FlagManager.flagState.get(FlagType.DISABLE_OFF_HAND)){
			e.setCancelled(true);
		}
	}
	
}
