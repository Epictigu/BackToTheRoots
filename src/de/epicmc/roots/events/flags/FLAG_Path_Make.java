package de.epicmc.roots.events.flags;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.epicmc.roots.Main;
import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class FLAG_Path_Make implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(FlagManager.checkFlag(FlagType.DISABLE_PATH_MAKE, e.getPlayer())) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				String grass_name = (Main.version >= 17) ? "GRASS_BLOCK" : "GRASS";
				if(e.getClickedBlock().getType() == Material.valueOf(grass_name)){
					Material mat = e.getPlayer().getInventory().getItemInMainHand().getType();
					
					if(mat.toString().toUpperCase().contains("SHOVEL") || mat.toString().toUpperCase().contains("SPADE")){
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
}
