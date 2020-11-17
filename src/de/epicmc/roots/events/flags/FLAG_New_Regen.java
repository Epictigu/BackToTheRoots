package de.epicmc.roots.events.flags;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class FLAG_New_Regen implements Listener{
	
	@EventHandler
	public void onRegain(EntityRegainHealthEvent e){
		if(FlagManager.checkFlag(FlagType.DISABLE_NEW_REGEN, e.getEntity().getWorld())) {
			if(e.getEntity() instanceof Player){
				if(e.getRegainReason() == RegainReason.SATIATED){
					e.setAmount(e.getAmount() / 2D);
				}
			}
		}
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e){
		if(FlagManager.checkFlag(FlagType.DISABLE_NEW_REGEN, e.getEntity().getWorld())) {
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
