package de.epicmc.roots.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import de.epicmc.roots.Main;

public class EVENT_CreatureSpawn implements Listener{
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		if(Main.DISABLE_SKELETON_TRAP){
			if(e.getSpawnReason() == SpawnReason.LIGHTNING){
				e.setCancelled(true);
			}
		}
		if(Main.DISABLE_ENDERMITE_ON_PEARL){
			if(e.getEntityType() == EntityType.ENDERMITE){
				if(e.getSpawnReason() == SpawnReason.DEFAULT){
					e.setCancelled(true);
				}
			}
		}
	}
	
}
