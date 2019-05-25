package de.epicmc.roots.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.epicmc.roots.Main;

public class EVENT_PlayerInteract implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(Main.DISABLE_PATH_MAKE){
				if(e.getClickedBlock().getType() == Material.GRASS){
					Material mat = e.getPlayer().getInventory().getItemInMainHand().getType();
					
					if(mat == Material.DIAMOND_SHOVEL
							|| mat == Material.IRON_SHOVEL
							|| mat == Material.STONE_SHOVEL
							|| mat == Material.WOODEN_SHOVEL
							|| mat == Material.GOLDEN_SHOVEL){
						e.setCancelled(true);
					}
				}
			}
		}
		
		if(Main.DISABLE_CHORUS_FRUIT){
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
	
}
