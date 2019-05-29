package de.epicmc.roots.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.epicmc.roots.utils.DisableType;
import de.epicmc.roots.utils.FlagType;
import de.epicmc.roots.utils.ItemBuilder;

public class InventoryManager {
	
	public static InventoryHolder customInventoryHolder;
	
	private static int generalSlot = 9;
	private static int recipeSlot = 9;
	
	public static Inventory flagInv;
	public static Inventory flagInvMain;
	public static Inventory flagInvRecipe;
	
	public static void initializeInventories() {
		customInventoryHolder = new InventoryHolder() { @Override public Inventory getInventory() {return null;} };
		
		flagInv = Bukkit.createInventory(customInventoryHolder, 27, "�6BackToTheRoots");
		flagInvMain = Bukkit.createInventory(customInventoryHolder, 45, "�6BackToTheRoots");
		flagInvRecipe = Bukkit.createInventory(customInventoryHolder, 36, "�6BackToTheRoots");
		
		ItemStack background = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).addDisplayName("�0 ").buildItem();
		
		for(int i = 0; i < 45; i++){
			if(i < 27){
				flagInv.setItem(i, background);
			}
			if(i < 36){
				flagInvRecipe.setItem(i, background);
			}
			
			flagInvMain.setItem(i, background);
		}
		
		flagInv.setItem(11, new ItemBuilder(Material.BOOK).addDisplayName("�7Disable general things.").buildItem());
		flagInv.setItem(15, new ItemBuilder(Material.CRAFTING_TABLE).addDisplayName("�7Disable crafting-recipes.").buildItem());
		
		ItemStack cancel = new ItemBuilder(Material.BARRIER).addDisplayName("�7Back").buildItem();
		
		flagInvMain.setItem(40, cancel);
		flagInvRecipe.setItem(31, cancel);
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
		Material color;
		
		if(FlagManager.flagState.get(type)){
			name = "�a" + name;
			color = Material.GREEN_TERRACOTTA;
		} else {
			name = "�c" + name;
			color = Material.RED_TERRACOTTA;
		}
		
		inv.setItem(slot, new ItemBuilder(color).addDisplayName(name).buildItem());
	}
	
}