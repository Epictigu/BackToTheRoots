package de.epicmc.roots.events;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import de.epicmc.roots.Main;

public class EVENT_PlayerJoin implements Listener{
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e){
		if(Main.DISABLE_COOLDOWN){
			e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32D);
		} else {
			e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
		}
		
		if(Main.DISABLE_OFF_HAND){
			ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
			if(item != null){
				e.getPlayer().getInventory().setItemInOffHand(null);
				e.getPlayer().getInventory().addItem(item);
			}
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				if(Main.DISABLE_PLAYER_COLLIDE){
					Scoreboard sb = e.getPlayer().getScoreboard();
					
					if(sb.getEntryTeam(e.getPlayer().getName()) != null){
						sb.getEntryTeam(e.getPlayer().getName()).setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
					} else {
						Team t = null;
						
						if(sb.getTeam("acollide") != null){
							t = sb.getTeam("acollide");
						} else {
							t = sb.registerNewTeam("acollide");
						}
						
						t.addEntry(e.getPlayer().getName());
						t.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
					}
				}
			}
		}, 40);
	}
	
}
