package de.epicmc.roots.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import de.epicmc.roots.Main;

public class EVENT_PrepareItemCraft implements Listener{
	
	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		if(e.getRecipe() == null) {
			return;
		}
		Material type = e.getRecipe().getResult().getType();
		
		if(type == Material.SHIELD) {
			if(Main.DISABLE_RECIPE_SHIELD) {
				e.getInventory().setResult(new ItemStack(Material.AIR));
			}
		} else if(type == Material.END_CRYSTAL) {
			if(Main.DISABLE_RECIPE_END_CRYSTAL) {
				e.getInventory().setResult(new ItemStack(Material.AIR));
			}
		} else if(type.toString().contains("SHULKER_BOX")) {
			if(Main.DISABLE_RECIPE_SHULKER_BOX) {
				e.getInventory().setResult(new ItemStack(Material.AIR));
			}
		}
	}
	
}