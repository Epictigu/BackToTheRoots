package de.epicmc.roots.events.flags;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class FLAG_Skeleton_Trap implements Listener{
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		if(FlagManager.checkFlag(FlagType.DISABLE_SKELETON_TRAP, e.getEntity().getWorld())) {
			if(e.getSpawnReason() == SpawnReason.LIGHTNING){
				e.setCancelled(true);
			}
		}
	}
	
}
