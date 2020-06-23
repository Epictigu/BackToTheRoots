package de.epicmc.roots.events;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class EVENT_PlayerJoin implements Listener{
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e){
		if(FlagManager.flagState.get(FlagType.DISABLE_COOLDOWN)){
			e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32D);
		} else {
			e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
		}
	}
	
}
