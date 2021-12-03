package de.epicmc.roots.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import de.epicmc.roots.Main;
import de.epicmc.roots.utils.FlagType;

public class CollisionManager {
	
	public static void initializeCollisionScheduler() {
		if(Main.version < FlagType.DISABLE_PLAYER_COLLIDE.minVersion)
			return;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				if(FlagManager.flagState.get(FlagType.DISABLE_PLAYER_COLLIDE) == true){
					for(Player p : Bukkit.getOnlinePlayers()){
						if(p.getScoreboard() != null){
							Scoreboard sb = p.getScoreboard();
							if(sb.getEntryTeam(p.getName()) != null){
								sb.getEntryTeam(p.getName()).setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
							}
						}
					}
				}
			}
		}, 60, 60);
	}
	
	public static void updateCollisionRule() {
		if(Main.version < FlagType.DISABLE_PLAYER_COLLIDE.minVersion)
			return;
		if(FlagManager.flagState.get(FlagType.DISABLE_PLAYER_COLLIDE)){
			for(Player pl : Bukkit.getOnlinePlayers()){
				Scoreboard sb = pl.getScoreboard();
				
				if(sb.getEntryTeam(pl.getName()) != null){
					sb.getEntryTeam(pl.getName()).setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
				}
			}
		} else {
			for(Player pl : Bukkit.getOnlinePlayers()){
				Scoreboard sb = pl.getScoreboard();
				
				if(sb.getEntryTeam(pl.getName()) != null){
					sb.getEntryTeam(pl.getName()).setOption(Option.COLLISION_RULE, OptionStatus.ALWAYS);
				} else {
					Team t = null;
					
					if(sb.getTeam("acollide") != null){
						t = sb.getTeam("acollide");
					} else {
						t = sb.registerNewTeam("acollide");
					}
					
					t.addEntry(pl.getName());
					t.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
				}
			}
		}
	}
	
}
