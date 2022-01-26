package de.epicmc.roots.utils;

import org.bukkit.event.Listener;

import de.epicmc.roots.events.flags.FLAG_Chorus_Fruit;
import de.epicmc.roots.events.flags.FLAG_Collide;
import de.epicmc.roots.events.flags.FLAG_Endermite_On_Pearl;
import de.epicmc.roots.events.flags.FLAG_New_Damage;
import de.epicmc.roots.events.flags.FLAG_New_Regen;
import de.epicmc.roots.events.flags.FLAG_Off_Hand;
import de.epicmc.roots.events.flags.FLAG_Path_Make;
import de.epicmc.roots.events.flags.FLAG_Skeleton_Trap;
import de.epicmc.roots.events.flags.recipes.FLAG_Recipe_End_Crystal;
import de.epicmc.roots.events.flags.recipes.FLAG_Recipe_Shield;
import de.epicmc.roots.events.flags.recipes.FLAG_Recipe_Shulker_Box;

public enum FlagType {
	
	DISABLE_COOLDOWN(9, null, DisableType.GENERAL, "DISABLE.COOLDOWN", true, "Disable Cooldown"),
	DISABLE_NEW_DAMAGE(9, new FLAG_New_Damage(), DisableType.GENERAL, "DISABLE.NEW_DAMAGE", true, "Disable New Damage"),
	DISABLE_NEW_REGEN(9, new FLAG_New_Regen(), DisableType.GENERAL, "DISABLE.NEW_REGEN", false, "Disable New Regen"),
	DISABLE_PATH_MAKE(9, new FLAG_Path_Make(), DisableType.GENERAL, "DISABLE.PATH_MAKE", false, "Disable Path Make"),
	DISABLE_OFF_HAND(9, new FLAG_Off_Hand(), DisableType.GENERAL, "DISABLE.OFF_HAND", false, "Disable Off Hand"),
	DISABLE_SKELETON_TRAP(9, new FLAG_Skeleton_Trap(), DisableType.GENERAL, "DISABLE.SKELETON_TRAP", false, "Disable Skeleton Trap"),
	DISABLE_PLAYER_COLLIDE(9, new FLAG_Collide(), DisableType.GENERAL, "DISABLE.PLAYER_COLLIDE", false, "Disable Player Collide"),
	DISABLE_CHORUS_FRUIT(9, new FLAG_Chorus_Fruit(), DisableType.GENERAL, "DISABLE.CHORUS_FRUIT", false, "Disable ChorusFruit"),
	DISABLE_ENDERMITE_ON_PEARL(9, new FLAG_Endermite_On_Pearl(), DisableType.GENERAL, "DISABLE.ENDERMITE_ON_PEARL", false, "Disable Endermite on Enderpearl"),
	
	DISABLE_RECIPE_SHIELD(9, new FLAG_Recipe_Shield(), DisableType.RECIPE, "DISABLE_RECIPE.SHIELD", false, "Disable: Recipe Shield"),
	DISABLE_RECIPE_END_CRYSTAL(9, new FLAG_Recipe_End_Crystal(), DisableType.RECIPE, "DISABLE_RECIPE.END_CRYSTAL", false, "Disable Recipe: End Crystal"),
	DISABLE_RECIPE_SHULKER_BOX(11, new FLAG_Recipe_Shulker_Box(), DisableType.RECIPE, "DISABLE_RECIPE.SHULKER_BOX", false, "Disable Recipe: Shulker Box");
	
	public int minVersion;
	public DisableType disableType;
	public String configPath;
	public boolean defaultValue;
	public String itemName;
	public Listener eventListener;
	
	private FlagType(int minVersion, Listener eventListener, DisableType disableType, String configPath, boolean defaultValue, String itemName) {
		this.minVersion = minVersion;
		this.disableType = disableType;
		this.configPath = configPath;
		this.defaultValue = defaultValue;
		this.itemName = itemName;
		this.eventListener = eventListener;
	}
	
}
