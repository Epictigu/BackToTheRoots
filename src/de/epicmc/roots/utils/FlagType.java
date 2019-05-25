package de.epicmc.roots.utils;

import org.bukkit.ChatColor;

public enum FlagType {
	
	DISABLE_COOLDOWN,
	DISABLE_NEW_DAMAGE,
	DISABLE_PATH_MAKE, DISABLE_OFF_HAND,
	DISABLE_SKELETON_TRAP,
	DISABLE_PLAYER_COLLIDE,
	DISABLE_CHORUS_FRUIT,
	DISABLE_ENDERMITE_ON_PEARL,
	DISABLE_NEW_REGEN,
	
	DISABLE_RECIPE_SHIELD,
	DISABLE_RECIPE_END_CRYSTAL,
	DISABLE_RECIPE_SHULKER_BOX;
	
	
	public static FlagType getByName(String name){
		String sname = ChatColor.stripColor(name);
		
		if(sname.equalsIgnoreCase("Disable Cooldown")){
			return FlagType.DISABLE_COOLDOWN;
		} else if(sname.equalsIgnoreCase("Disable New Damage")){
			return FlagType.DISABLE_NEW_DAMAGE;
		} else if(sname.equalsIgnoreCase("Disable New Regen")){
			return FlagType.DISABLE_NEW_REGEN;
		} else if(sname.equalsIgnoreCase("Disable Path Make")){
			return FlagType.DISABLE_PATH_MAKE;
		} else if(sname.equalsIgnoreCase("Disable Off Hand")){
			return FlagType.DISABLE_OFF_HAND;
		} else if(sname.equalsIgnoreCase("Disable Skeleton Trap")){
			return FlagType.DISABLE_SKELETON_TRAP;
		} else if(sname.equalsIgnoreCase("Disable Player Collide")){
			return FlagType.DISABLE_PLAYER_COLLIDE;
		} else if(sname.equalsIgnoreCase("Disable ChorusFruit")){
			return FlagType.DISABLE_CHORUS_FRUIT;
		} else if(sname.equalsIgnoreCase("Disable Endermite on Enderpearl")){
			return FlagType.DISABLE_ENDERMITE_ON_PEARL;
		} else if(sname.equalsIgnoreCase("Disable Recipe: Shield")){
			return FlagType.DISABLE_RECIPE_SHIELD;
		} else if(sname.equalsIgnoreCase("Disable Recipe: End Crystal")){
			return FlagType.DISABLE_RECIPE_END_CRYSTAL;
		} else if(sname.equalsIgnoreCase("Disable Recipe: ShulkerBox")){
			return FlagType.DISABLE_RECIPE_SHULKER_BOX;
		}
		
		return null;
	}
	
}
