package de.epicmc.roots.events;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import de.epicmc.roots.Main;

public class EVENT_PlayerChangedWorld implements Listener{
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		final Player p = e.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				if(Main.DISABLE_COOLDOWN){
					p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32D);
				} else {
					p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
				}
				
				if(Main.DISABLE_OFF_HAND){
					ItemStack item = p.getInventory().getItemInOffHand();
					if(item != null){
						p.getInventory().setItemInOffHand(null);
						p.getInventory().addItem(item);
					}
				}
			}
		}, 2);
	}
	
}