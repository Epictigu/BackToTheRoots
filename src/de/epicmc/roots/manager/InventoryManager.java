package de.epicmc.roots.manager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.epicmc.roots.Main;
import de.epicmc.roots.utils.DisableType;
import de.epicmc.roots.utils.FlagType;
import de.epicmc.roots.utils.ItemBuilder;

public class InventoryManager {
	
	public static InventoryHolder customInventoryHolder;
	public static InventoryHolder customWorldInventoryHolder;
	
	private static int generalSlot = 9;
	private static int recipeSlot = 9;
	
	public static Inventory flagInv;
	public static Inventory flagInvMain;
	public static Inventory flagInvRecipe;
	
	public static Inventory worldInv;
	public static Map<World, Inventory> worldFlagInv = new HashMap<World, Inventory>();
	
	private static ItemStack background;
	private static ItemStack cancel;
	
	public static void initializeInventories() {
		customInventoryHolder = new InventoryHolder() { @Override public Inventory getInventory() {return null;} };
		customWorldInventoryHolder = new InventoryHolder() { @Override public Inventory getInventory() {return null;} };
		
		flagInv = Bukkit.createInventory(customInventoryHolder, 36, "§6BackToTheRoots");
		flagInvMain = Bukkit.createInventory(customInventoryHolder, 45, "§6BackToTheRoots");
		flagInvRecipe = Bukkit.createInventory(customInventoryHolder, 36, "§6BackToTheRoots");
		
		if(Main.version >= 13) {
			background = new ItemBuilder(Enum.valueOf(Material.class, "BLACK_STAINED_GLASS_PANE")).addDisplayName("§0 ").buildItem();
		} else {
			background = new ItemBuilder(Enum.valueOf(Material.class, "STAINED_GLASS_PANE")).setData(15).addDisplayName("§0 ").buildItem();
		}
		
		for(int i = 0; i < 45; i++){
			if(i < 36){
				flagInv.setItem(i, background);
				flagInvRecipe.setItem(i, background);
			}
			
			flagInvMain.setItem(i, background);
		}
		
		flagInv.setItem(11, new ItemBuilder(Material.BOOK).addDisplayName("§7Disable general things.").buildItem());
		if(Main.version >= 13) {
			flagInv.setItem(15, new ItemBuilder(Enum.valueOf(Material.class, "CRAFTING_TABLE")).addDisplayName("§7Disable crafting-recipes.").buildItem());
		} else {
			flagInv.setItem(15, new ItemBuilder(Enum.valueOf(Material.class, "WORKBENCH")).addDisplayName("§7Disable crafting-recipes.").buildItem());
		}
		flagInv.setItem(31, new ItemBuilder(Material.COMPASS).addDisplayName("§7World-Configuration Mode.").buildItem());
		
		cancel = new ItemBuilder(Material.BARRIER).addDisplayName("§7Back").buildItem();
		
		flagInvMain.setItem(40, cancel);
		flagInvRecipe.setItem(31, cancel);
		
		initWorldInventory();
	}
	
	public static void updateWorldInventories() {
		worldFlagInv.clear();
		for(World w : Bukkit.getWorlds()) {
			File f = new File("plugins/BackToTheRoots/worlds/" + w.getName() + ".yml");
			Map<FlagType, Boolean> flagState = new HashMap<FlagType, Boolean>();
			if(FlagManager.flagStateWorld.containsKey(w)) {
				flagState = FlagManager.flagStateWorld.get(w);
			} else {
				FlagManager.flagStateWorld.put(w, flagState);
			}
			if(f.exists()) {
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
				
				for(FlagType flag : FlagType.values()) {
					if(cfg.contains(flag.configPath)) {
						flagState.put(flag, cfg.getBoolean(flag.configPath));
					}
				}
			}
			
			
			Inventory inv = Bukkit.createInventory(customWorldInventoryHolder, 45, "§6BTTR-World: §e" + w.getName());
			for(int i = 0; i < 45; i++) {
				inv.setItem(i, background);
			}
			
			inv.setItem(40, cancel);
			inv.setItem(44, new ItemBuilder(Material.REDSTONE).addDisplayName("§e§lINFO").addLore("",
					"§7§lGray §7- Default Setting, not specific for this World.",
					"§a§lGreen §7- Enabled, enables this flag specifically for this world.",
					"§c§lRed §7- Disabled, disables this flag specifically for this world.").buildItem());
			
			int slot = 10;
			for(FlagType flag : FlagType.values()) {
				if(flag.disableType == DisableType.RECIPE || flag == FlagType.DISABLE_PLAYER_COLLIDE)
					continue;
				if(Main.version < flag.minVersion)
					continue;
				addWorldDisable(inv, slot, flag, flagState);
				slot++;
				if(slot > 16 && slot < 19) {
					slot = 19;
				}
			}
			
			worldFlagInv.put(w, inv);
		}
	}
	
	public static void initWorldInventory() {
		List<World> worlds = Bukkit.getWorlds();
		int wS = worlds.size();
		int start = 0;
		int inc = 100;
		int nSize = 54;
		
		if(wS <=20) {
			start = 11;
			inc = 5;
			nSize = ((wS - 1) / 5 + 3) * 9;
			if(nSize < 54) nSize +=9;
		} else if(wS <= 28) {
			start = 10;
			inc = 7;
			nSize = ((wS - 1) / 7 + 3) * 9;
			if(nSize < 54) nSize +=9;
		}
		
		worldInv = Bukkit.createInventory(customInventoryHolder, nSize, "§6WorldSelection");
		
		for(int i = 0; i < nSize; i++) {
			worldInv.setItem(i, background);
		}
		
		int curPos = start;
		int row = 0;
		for(World w : worlds) {
			if(w.getEnvironment() == Environment.NORMAL)
				worldInv.setItem(curPos, new ItemBuilder(Material.DIRT).addDisplayName("§7" + w.getName()).buildItem());
			else if(w.getEnvironment() == Environment.NETHER)
				worldInv.setItem(curPos, new ItemBuilder(Material.NETHERRACK).addDisplayName("§7" + w.getName()).buildItem());
			else if(w.getEnvironment() == Environment.THE_END)
				worldInv.setItem(curPos, new ItemBuilder(Material.OBSIDIAN).addDisplayName("§7" + w.getName()).buildItem());
			
			curPos++;
			if(curPos > start + row * 9 + inc) {
				row++;
				curPos = start + row * 9;
			}
		}
		
		worldInv.setItem(worldInv.getSize() - 5, cancel);
		worldInv.setItem(worldInv.getSize() - 9, new ItemBuilder(Material.DAYLIGHT_DETECTOR).addDisplayName("§7Reload World Configurations").buildItem());
	}
	
	
	
	public static void addDisable(FlagType type){
		Inventory inv = null;
		int slot = 0;
		
		if(type.disableType == DisableType.GENERAL){
			inv = flagInvMain;
			generalSlot++;
			
			if(generalSlot > 16 && generalSlot < 19){
				generalSlot = 19;
			}
			
			slot = generalSlot;
		} else if(type.disableType == DisableType.RECIPE){
			inv = flagInvRecipe;
			recipeSlot++;
			
			if(recipeSlot > 16 && recipeSlot < 19){
				recipeSlot = 19;
			}
			
			slot = recipeSlot;
		}
		
		String name = type.itemName;
		
		if(Main.version >=13) {
			Material color;
			if(FlagManager.flagState.get(type)){
				name = "§a" + name;
				color = Enum.valueOf(Material.class, "GREEN_TERRACOTTA");
			} else {
				name = "§c" + name;
				color = Enum.valueOf(Material.class, "RED_TERRACOTTA");
			}
			
			inv.setItem(slot, new ItemBuilder(color).addDisplayName(name).buildItem());
		} else {
			int color;
			if(FlagManager.flagState.get(type)){
				name = "§a" + name;
				color = 13;
			} else {
				name = "§c" + name;
				color = 14;
			}
			
			inv.setItem(slot, new ItemBuilder(Enum.valueOf(Material.class, "STAINED_CLAY")).setData(color).addDisplayName(name).buildItem());
		}
	}
	
	public static void addWorldDisable(Inventory inv, int slot, FlagType type, Map<FlagType, Boolean> state){
		String name = type.itemName;
		
		if(Main.version >=13) {
			Material color;
			if(!state.containsKey(type)) {
				name = "§7" + name;
				color = Enum.valueOf(Material.class, "LIGHT_GRAY_TERRACOTTA");
			} else if(state.get(type)){
				name = "§a" + name;
				color = Enum.valueOf(Material.class, "GREEN_TERRACOTTA");
			} else {
				name = "§c" + name;
				color = Enum.valueOf(Material.class, "RED_TERRACOTTA");
			}
			
			inv.setItem(slot, new ItemBuilder(color).addDisplayName(name).buildItem());
		} else {
			int color;
			if(!state.containsKey(type)) {
				name = "§7" + name;
				color = 8;
			} else if(state.get(type)){
				name = "§a" + name;
				color = 13;
			} else {
				name = "§c" + name;
				color = 14;
			}
			
			inv.setItem(slot, new ItemBuilder(Enum.valueOf(Material.class, "STAINED_CLAY")).setData(color).addDisplayName(name).buildItem());
		}
	}
	
}
