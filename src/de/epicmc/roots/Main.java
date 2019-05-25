package de.epicmc.roots;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import de.epicmc.roots.commands.COMMAND_BTTR;
import de.epicmc.roots.events.EVENT_CreatureSpawn;
import de.epicmc.roots.events.EVENT_EntityDamageByEntity;
import de.epicmc.roots.events.EVENT_EntityRegainHealth;
import de.epicmc.roots.events.EVENT_FoodBarChange;
import de.epicmc.roots.events.EVENT_InventoryClick;
import de.epicmc.roots.events.EVENT_PlayerChangedWorld;
import de.epicmc.roots.events.EVENT_PlayerInteract;
import de.epicmc.roots.events.EVENT_PlayerSwapHandItems;
import de.epicmc.roots.events.EVENT_PrepareItemCraft;
import de.epicmc.roots.events.EVENT_PlayerJoin;
import de.epicmc.roots.events.EVENT_PlayerQuit;
import de.epicmc.roots.utils.DisableType;
import de.epicmc.roots.utils.ItemBuilder;

public class Main extends JavaPlugin{
	
	public static Plugin instance;
	
	public static boolean DISABLE_COOLDOWN = false;
	public static boolean DISABLE_NEW_DAMAGE = false;
	public static boolean DISABLE_PATH_MAKE = false;
	public static boolean DISABLE_OFF_HAND = false;
	public static boolean DISABLE_SKELETON_TRAP = false;
	public static boolean DISABLE_PLAYER_COLLIDE = false;
	public static boolean DISABLE_CHORUS_FRUIT = false;
	public static boolean DISABLE_ENDERMITE_ON_PEARL = false;
	public static boolean DISABLE_NEW_REGEN = false;
	
	public static boolean DISABLE_RECIPE_SHIELD = false;
	public static boolean DISABLE_RECIPE_END_CRYSTAL = false;
	public static boolean DISABLE_RECIPE_SHULKER_BOX = false;
	
	public static Inventory flagInv;
	public static Inventory flagInvMain;
	public static Inventory flagInvRecipe;
	
	public static InventoryHolder BTTRHolder;
	
	private int generalSlot;
	private int recipeSlot;
	
	public static boolean use11;
	
	public void onEnable(){
		String version = this.getDescription().getVersion();
		
		BTTRHolder = new InventoryHolder() {
			@Override
			public Inventory getInventory() {return null;}
		};
		
		
		
		flagInv = Bukkit.createInventory(BTTRHolder, 27, "§6BackToTheRoots");
		flagInvMain = Bukkit.createInventory(BTTRHolder, 45, "§6BackToTheRoots");
		flagInvRecipe = Bukkit.createInventory(BTTRHolder, 36, "§6BackToTheRoots");
		
		ItemStack background = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).addDisplayName("§0 ").buildItem();
		
		for(int i = 0; i < 45; i++){
			if(i < 27){
				flagInv.setItem(i, background);
			}
			if(i < 36){
				flagInvRecipe.setItem(i, background);
			}
			
			flagInvMain.setItem(i, background);
		}
		
		flagInv.setItem(11, new ItemBuilder(Material.BOOK).addDisplayName("§7Disable general things.").buildItem());
		flagInv.setItem(15, new ItemBuilder(Material.CRAFTING_TABLE).addDisplayName("§7Disable crafting-recipes.").buildItem());
		
		ItemStack cancel = new ItemBuilder(Material.BARRIER).addDisplayName("§7Back").buildItem();
		
		flagInvMain.setItem(40, cancel);
		flagInvRecipe.setItem(31, cancel);
		
		generalSlot = 9;
		recipeSlot = 9;
		
		use11 = (getServer().getVersion().contains("1.11") || getServer().getVersion().contains("1.12") || getServer().getVersion().contains("1.13") || getServer().getVersion().contains("1.14"));
		
		System.out.println(" ");
		System.out.println("[BTTR] Loading plugin ...");
		
		instance = this;
		
		File old = new File("plugins/BackToTheRoods");
		if(old.exists()){
			old.renameTo(new File("plugins/BackToTheRoots"));
		}
		
		File o1 = new File("plugins/BackToTheRoots");
		if(!(o1.exists())){
			o1.mkdir();
		}
		
		File f1 = new File("plugins/BackToTheRoots/config.yml");
		if(!(f1.exists())){
			System.out.println("[BTTR] No config found.");
			System.out.println("[BTTR] Creating new one ...");
			try {
				f1.createNewFile();
				
				YamlConfiguration cfg = new YamlConfiguration();
				
				cfg.set("CONFIG_VERSION", version);
				
				cfg.set("DISABLE.COOLDOWN", true);
				cfg.set("DISABLE.NEW_DAMAGE", true);
				cfg.set("DISABLE.NEW_REGEN", true);
				cfg.set("DISABLE.PATH_MAKE", false);
				cfg.set("DISABLE.OFF_HAND", false);
				cfg.set("DISABLE.SKELETON_TRAP", false);
				cfg.set("DISABLE.PLAYER_COLLIDE", false);
				cfg.set("DISABLE.CHORUS_FRUIT", false);
				cfg.set("DISABLE.ENDERMITE_ON_PEARL", false);
				
				cfg.set("DISABLE_RECIPE.SHIELD", false);
				cfg.set("DISABLE_RECIPE.END_CRYSTAL", false);
				cfg.set("DISABLE_RECIPE.SHULKER_BOX", false);
				
				cfg.save(f1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("[BTTR] Loading config ...");
		
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f1);
		
		if(!(cfg.getString("CONFIG_VERSION").equalsIgnoreCase(version))){
			System.out.println("[BTTR] Updating config ...");
			
			cfg.set("CONFIG_VERSION", version);
			
			if(cfg.get("DISABLE.NEW_DAMAGE") == null){
				cfg.set("DISABLE.NEW_DAMAGE", true);
			}
			
			if(cfg.get("DISABLE.PATH_MAKE") == null){
				cfg.set("DISABLE.PATH_MAKE", false);
			}
			
			if(cfg.get("DISABLE.OFF_HAND") == null){
				cfg.set("DISABLE.OFF_HAND", false);
			}
			
			if(cfg.get("DISABLE_RECIPE.END_CRYSTAL") == null){
				cfg.set("DISABLE_RECIPE.END_CRYSTAL", false);
			}
			
			if(cfg.get("DISABLE.SKELETON_TRAP") == null){
				cfg.set("DISABLE.SKELETON_TRAP", false);
			}
			
			if(cfg.get("DISABLE.PLAYER_COLLIDE") == null){
				cfg.set("DISABLE.PLAYER_COLLIDE", false);
			}
			
			if(cfg.get("DIABLE.CHORUS_FRUIT") == null){
				cfg.set("DISABLE.CHORUS_FRUIT", false);
			}
			
			if(cfg.get("DISABLE.ENDERMITE_ON_PEARL") == null){
				cfg.set("DISABLE.ENDERMITE_ON_PEARL", false);
			}
			
			if(cfg.get("DISABLE.NEW_REGEN") == null){
				cfg.set("DISABLE.NEW_REGEN", true);
			}
			
			if(cfg.get("DISABLE_RECIPE.SHULKER_BOX") == null){
				cfg.set("DISABLE_RECIPE.SHULKER_BOX", false);
			}
			
			try {
				cfg.save(f1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("[BTTR] Update complete!");
		}
		
		DISABLE_COOLDOWN = cfg.getBoolean("DISABLE.COOLDOWN");
		DISABLE_NEW_DAMAGE = cfg.getBoolean("DISABLE.NEW_DAMAGE");
		DISABLE_PATH_MAKE = cfg.getBoolean("DISABLE.PATH_MAKE");
		DISABLE_OFF_HAND = cfg.getBoolean("DISABLE.OFF_HAND");
		DISABLE_SKELETON_TRAP = cfg.getBoolean("DISABLE.SKELETON_TRAP");
		DISABLE_PLAYER_COLLIDE = cfg.getBoolean("DISABLE.PLAYER_COLLIDE");
		DISABLE_CHORUS_FRUIT = cfg.getBoolean("DISABLE.CHORUS_FRUIT");
		DISABLE_ENDERMITE_ON_PEARL = cfg.getBoolean("DISABLE.ENDERMITE_ON_PEARL");
		DISABLE_NEW_REGEN = cfg.getBoolean("DISABLE.NEW_REGEN");
		
		DISABLE_RECIPE_SHIELD = cfg.getBoolean("DISABLE_RECIPE.SHIELD");
		DISABLE_RECIPE_END_CRYSTAL = cfg.getBoolean("DISABLE_RECIPE.END_CRYSTAL");
		DISABLE_RECIPE_SHULKER_BOX = cfg.getBoolean("DISABLE_RECIPE.SHULKER_BOX");
		
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new EVENT_CreatureSpawn(), this);
		pm.registerEvents(new EVENT_EntityDamageByEntity(), this);
		pm.registerEvents(new EVENT_EntityRegainHealth(), this);
		pm.registerEvents(new EVENT_FoodBarChange(), this);
		pm.registerEvents(new EVENT_InventoryClick(), this);
		pm.registerEvents(new EVENT_PlayerChangedWorld(), this);
		pm.registerEvents(new EVENT_PlayerInteract(), this);
		
		EVENT_PlayerJoin join = new EVENT_PlayerJoin();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			join.onJoin(new PlayerJoinEvent(p, ""));
		}
		
		pm.registerEvents(join, this);
		pm.registerEvents(new EVENT_PlayerQuit(), this);
		pm.registerEvents(new EVENT_PlayerSwapHandItems(), this);
		pm.registerEvents(new EVENT_PrepareItemCraft(), this);
		
		Bukkit.getPluginCommand("bttr").setExecutor(new COMMAND_BTTR());
		
		addDisable(DisableType.GENERAL, "Cooldown", DISABLE_COOLDOWN);
		addDisable(DisableType.GENERAL, "New Damage", DISABLE_NEW_DAMAGE);
		addDisable(DisableType.GENERAL, "New Regen", DISABLE_NEW_REGEN);
		addDisable(DisableType.GENERAL, "Path Make", DISABLE_PATH_MAKE);
		addDisable(DisableType.GENERAL, "Off Hand", DISABLE_OFF_HAND);
		addDisable(DisableType.GENERAL, "Skeleton Trap", DISABLE_SKELETON_TRAP);
		addDisable(DisableType.GENERAL, "Player Collide", DISABLE_PLAYER_COLLIDE);
		addDisable(DisableType.GENERAL, "ChorusFruit", DISABLE_CHORUS_FRUIT);
		addDisable(DisableType.GENERAL, "EnderMite on EnderPearl", DISABLE_ENDERMITE_ON_PEARL);
		
		addDisable(DisableType.RECIPE, "Recipe: Shield", DISABLE_RECIPE_SHIELD);
		addDisable(DisableType.RECIPE, "Recipe: End Crystal", DISABLE_RECIPE_END_CRYSTAL);
		
		if(use11){
			addDisable(DisableType.RECIPE, "Recipe: ShulkerBox", DISABLE_RECIPE_SHULKER_BOX);
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				if(DISABLE_PLAYER_COLLIDE){
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
		
		System.out.println("[BTTR] Plugin successfully loaded.");
		System.out.println(" ");
	}
	
	private void addDisable(DisableType type, String DisableName, boolean DisableFlag){
		Inventory inv = null;
		int slot = 0;
		
		if(type == DisableType.GENERAL){
			inv = flagInvMain;
			generalSlot++;
			
			if(generalSlot > 16 && generalSlot < 19){
				generalSlot = 19;
			}
			
			slot = generalSlot;
		} else if(type == DisableType.RECIPE){
			inv = flagInvRecipe;
			recipeSlot++;
			
			if(recipeSlot > 16 && recipeSlot < 19){
				recipeSlot = 19;
			}
			
			slot = recipeSlot;
		}
		
		String name = "Disable " + DisableName;
		Material color;
		
		if(DisableFlag){
			name = "§a" + name;
			color = Material.GREEN_TERRACOTTA;
		} else {
			name = "§c" + name;
			color = Material.RED_TERRACOTTA;
		}
		
		inv.setItem(slot, new ItemBuilder(color).addDisplayName(name).buildItem());
	}
	
}
