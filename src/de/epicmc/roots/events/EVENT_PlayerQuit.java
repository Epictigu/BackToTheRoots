package de.epicmc.roots.events;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.epicmc.roots.Main;
import de.epicmc.roots.utils.FlagType;

public class EVENT_PlayerQuit implements Listener{

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		if(Main.version < FlagType.DISABLE_COOLDOWN.minVersion)
			return;
		e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
	}
	
}
