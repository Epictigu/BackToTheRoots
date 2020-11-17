package de.epicmc.roots.events.flags.recipes;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class FLAG_Recipe_Shulker_Box implements Listener{
	
	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		if(!e.getViewers().isEmpty()) {
			if(FlagManager.checkFlag(FlagType.DISABLE_RECIPE_END_CRYSTAL, e.getViewers().get(0).getWorld())) {
				if(e.getRecipe() == null) {
					return;
				}
				
				Material type = e.getRecipe().getResult().getType();
				if(type.toString().contains("SHULKER_BOX")) {
					e.getInventory().setResult(new ItemStack(Material.AIR));
				}
			}
		}
	}
	
}
