package de.epicmc.roots.events.flags;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class FLAG_Endermite_On_Pearl implements Listener{
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		if(e.getEntityType() == EntityType.ENDERMITE){
			if(e.getSpawnReason() == SpawnReason.ENDER_PEARL){
				e.setCancelled(true);
			}
		}
	}
	
}
