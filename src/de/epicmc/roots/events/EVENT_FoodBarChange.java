package de.epicmc.roots.events;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.epicmc.roots.Main;

public class EVENT_FoodBarChange implements Listener{
	
	@EventHandler
	public void onChange(FoodLevelChangeEvent e){
		if(Main.DISABLE_NEW_REGEN){
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
