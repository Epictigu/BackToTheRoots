package de.epicmc.roots.events.flags;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.epicmc.roots.Main;
import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class FLAG_Enderpearl_Cooldown implements Listener{

	@EventHandler
	public void onEnderpearl(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(e.getItem() == null)
				return;
			if(e.getItem().getType() == Material.ENDER_PEARL) {
				if(FlagManager.checkFlag(FlagType.DISABLE_ENDERPEARL_COOLDOWN, e.getPlayer())) {
					Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
						
						@Override
						public void run() {
							e.getPlayer().setCooldown(Material.ENDER_PEARL, 0);
						}
					});
				}
			}
		}
	}
	
}
