package de.epicmc.roots.utils;

public enum FlagType {
	
	DISABLE_COOLDOWN(DisableType.GENERAL, "DISABLE.COOLDOWN", true, "Disable Cooldown"),
	DISABLE_NEW_DAMAGE(DisableType.GENERAL, "DISABLE.NEW_DAMAGE", true, "Disable New Damage"),
	DISABLE_NEW_REGEN(DisableType.GENERAL, "DISABLE.NEW_REGEN", true, "Disable New Regen"),
	DISABLE_PATH_MAKE(DisableType.GENERAL, "DISABLE.PATH_MAKE", false, "Disable Path Make"),
	DISABLE_OFF_HAND(DisableType.GENERAL, "DISABLE.OFF_HAND", false, "Disable Off Hand"),
	DISABLE_SKELETON_TRAP(DisableType.GENERAL, "DISABLE.SKELETON_TRAP", false, "Disable Skeleton Trap"),
	DISABLE_PLAYER_COLLIDE(DisableType.GENERAL, "DISABLE.PLAYER_COLLIDE", false, "Disable Player Collide"),
	DISABLE_CHORUS_FRUIT(DisableType.GENERAL, "DISABLE.CHORUS_FRUIT", false, "Disable ChorusFruit"),
	DISABLE_ENDERMITE_ON_PEARL(DisableType.GENERAL, "DISABLE.ENDERMITE_ON_PEARL", false, "Disable Endermite on Enderpearl"),
	
	DISABLE_RECIPE_SHIELD(DisableType.RECIPE, "DISABLE_RECIPE.SHIELD", false, "Disable: Recipe Shield"),
	DISABLE_RECIPE_END_CRYSTAL(DisableType.RECIPE, "DISABLE_RECIPE.END_CRYSTAL", false, "Disable Recipe: End Crystal"),
	DISABLE_RECIPE_SHULKER_BOX(DisableType.RECIPE, "DISABLE_RECIPE.SHULKER_BOX", false, "Disable Recipe: Shulker Box");
	
	public DisableType disableType;
	public String configPath;
	public boolean defaultValue;
	public String itemName;
	
	private FlagType(DisableType disableType, String configPath, boolean defaultValue, String itemName) {
		this.disableType = disableType;
		this.configPath = configPath;
		this.defaultValue = defaultValue;
		this.itemName = itemName;
	}
	
}
