package de.epicmc.roots.events.flags;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class FLAG_Skeleton_Trap implements Listener{
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		if(e.getSpawnReason() == SpawnReason.LIGHTNING){
			e.setCancelled(true);
		}
	}
	
}
