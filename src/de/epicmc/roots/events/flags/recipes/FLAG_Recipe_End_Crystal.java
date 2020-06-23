package de.epicmc.roots.events.flags.recipes;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class FLAG_Recipe_End_Crystal implements Listener{
	
	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		if(e.getRecipe() == null) {
			return;
		}
		
		Material type = e.getRecipe().getResult().getType();
		if(type == Material.END_CRYSTAL) {
			e.getInventory().setResult(new ItemStack(Material.AIR));
		}
	}
	
}
