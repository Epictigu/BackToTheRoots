package de.epicmc.roots.events;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class EVENT_FoodBarChange implements Listener{
	
	@EventHandler
	public void onChange(FoodLevelChangeEvent e){
		if(FlagManager.flagState.get(FlagType.DISABLE_NEW_REGEN)){
			if(((Player)e.getEntity()).getFoodLevel() > 16){
				if(((Player)e.getEntity()).getFoodLevel() > e.getFoodLevel()){
					if(new Random().nextInt(3) > 0){
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
}
