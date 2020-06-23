package de.epicmc.roots.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import de.epicmc.roots.Main;
import de.epicmc.roots.utils.FlagType;

public class FlagManager {
	
	public static Map<FlagType, Boolean> flagState = new HashMap<FlagType, Boolean>();
	
	public static void updateNoCooldown() {
		if(flagState.get(FlagType.DISABLE_COOLDOWN)) {
			for(Player player : Bukkit.getOnlinePlayers()){
				player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
			}
		} else {
			for(Player player : Bukkit.getOnlinePlayers()){
				player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32D);
			}
		}
	}
	
	public static void setupEvents() {
		for(FlagType ft : flagState.keySet()) {
			if(flagState.get(ft)) {
				if(ft.eventListener != null)
					Bukkit.getPluginManager().registerEvents(ft.eventListener, Main.instance);
			}
		}
	}
	
}
