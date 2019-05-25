package de.epicmc.roots.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import de.epicmc.roots.Main;

public class EVENT_EntityRegainHealth implements Listener{
	
	@EventHandler
	public void onRegain(EntityRegainHealthEvent e){
		if(Main.DISABLE_NEW_REGEN){
			if(e.getEntity() instanceof Player){
				if(e.getRegainReason() == RegainReason.SATIATED){
					e.setAmount(e.getAmount() / 2D);
				}
			}
		}
	}
	
}
