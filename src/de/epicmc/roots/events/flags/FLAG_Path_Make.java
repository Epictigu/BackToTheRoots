package de.epicmc.roots.events.flags;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class FLAG_Path_Make implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(FlagManager.checkFlag(FlagType.DISABLE_PATH_MAKE, e.getPlayer())) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType() == Material.GRASS){
					Material mat = e.getPlayer().getInventory().getItemInMainHand().getType();
					
					if(mat.toString().contains("SHOVEL") || mat.toString().contains("SPADE")){
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
}
