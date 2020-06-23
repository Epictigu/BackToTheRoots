package de.epicmc.roots.events.flags;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FLAG_New_Damage implements Listener{
	
	@EventHandler
	public void onDamage(final EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player p = (Player)e.getDamager();
			
			Material mat = p.getInventory().getItemInMainHand().getType();
			
			if(mat.toString().contains("AXE") && mat.toString().contains("WOOD")){
				e.setDamage(e.getDamage() / 2.3333D);
			} else if(mat == Material.STONE_AXE){
				e.setDamage(e.getDamage() / 2.25);
			} else if(mat.toString().contains("AXE") && mat.toString().contains("GOLD")){
				e.setDamage(e.getDamage() / 2.3333D);
			} else if(mat == Material.IRON_AXE){
				e.setDamage(e.getDamage() / 1.8);
			} else if(mat == Material.DIAMOND_AXE){
				e.setDamage(e.getDamage() / 1.5);
			}
		}
	}
	
}
