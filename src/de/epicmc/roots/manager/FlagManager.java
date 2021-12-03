package de.epicmc.roots.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import de.epicmc.roots.Main;
import de.epicmc.roots.utils.FlagType;

public class FlagManager {
	
	public static Map<FlagType, Boolean> flagState = new HashMap<FlagType, Boolean>();
	public static Map<World, Map<FlagType, Boolean>> flagStateWorld = new HashMap<World, Map<FlagType, Boolean>>();
	
	public static void updateNoCooldown() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					if(checkFlag(FlagType.DISABLE_COOLDOWN, player)) {
						player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32D);
					} else {
						player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
					}
				}
			}
		});
	}
	
	public static void setupEvents() {
		for(FlagType ft : flagState.keySet()) {
			if(ft.eventListener != null)
				Bukkit.getPluginManager().registerEvents(ft.eventListener, Main.instance);
		}
	}
	
	public static boolean checkFlag(FlagType type, Player p) {
		if(type.minVersion > Main.version)
			return false;
		
		return checkFlag(type, p.getWorld());
	}
	
	public static boolean checkFlag(FlagType type, World w) {
		if(FlagManager.flagStateWorld.containsKey(w)) {
			Map<FlagType, Boolean> state = FlagManager.flagStateWorld.get(w);
			if(state.containsKey(type))
				return state.get(type);
		}
		
		return FlagManager.flagState.get(type);
	}
	
}
