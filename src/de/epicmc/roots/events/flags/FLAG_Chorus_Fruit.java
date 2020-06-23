package de.epicmc.roots.events.flags;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FLAG_Chorus_Fruit implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getPlayer().getInventory().getItemInOffHand() != null){
				ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
				if(item.getType() == Material.CHORUS_FRUIT){
					e.setCancelled(true);
				} else {
					ItemStack item2 = e.getPlayer().getInventory().getItemInMainHand();
					if(item2 != null){
						if(item2.getType() == Material.CHORUS_FRUIT){
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
}
