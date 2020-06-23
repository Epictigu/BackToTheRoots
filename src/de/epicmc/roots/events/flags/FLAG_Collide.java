package de.epicmc.roots.events.flags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import de.epicmc.roots.Main;
import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class FLAG_Collide implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				if(FlagManager.flagState.get(FlagType.DISABLE_PLAYER_COLLIDE)){
					Scoreboard sb = p.getScoreboard();
					
					if(sb.getEntryTeam(p.getName()) != null){
						sb.getEntryTeam(p.getName()).setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
					} else {
						Team t = null;
						
						if(sb.getTeam("acollide") != null){
							t = sb.getTeam("acollide");
						} else {
							t = sb.registerNewTeam("acollide");
						}
						
						t.addEntry(p.getName());
						t.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
					}
				}
			}
		}, 40);
	}
	
}
