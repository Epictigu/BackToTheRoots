package de.epicmc.roots.events;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import de.epicmc.roots.Main;
import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class EVENT_PlayerChangedWorld implements Listener{
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		final Player p = e.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				if(FlagManager.checkFlag(FlagType.DISABLE_COOLDOWN, p)){
					p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32D);
				} else {
					p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
				}
			}
		}, 2);
	}
	
}
