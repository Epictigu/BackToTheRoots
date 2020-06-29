package de.epicmc.roots.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import de.epicmc.roots.Main;
import de.epicmc.roots.manager.CollisionManager;
import de.epicmc.roots.manager.ConfigManager;
import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.manager.InventoryManager;
import de.epicmc.roots.utils.DisableType;
import de.epicmc.roots.utils.FlagType;
import de.epicmc.roots.utils.ItemBuilder;

public class EVENT_InventoryClick implements Listener {

	@EventHandler
	public void onClick(final InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getHolder() == InventoryManager.customInventoryHolder) {
				if (e.getInventory().getHolder() == InventoryManager.customInventoryHolder) {
					if (e.getCurrentItem() != null) {
						e.setCancelled(true);
						Material type = e.getCurrentItem().getType();
						Player p = (Player) e.getWhoClicked();
						
						if (type == Material.BOOK) {
							p.openInventory(InventoryManager.flagInvMain);
						} else if (type.toString().contains("CRAFTING") || type.toString().equalsIgnoreCase("WORKBENCH")) {
							p.openInventory(InventoryManager.flagInvRecipe);
						} else if (type == Material.BARRIER) {
							p.openInventory(InventoryManager.flagInv);
						} else if (type.toString().contains("TERRACOTTA") || type.toString().contains("CLAY")) {
							p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);

							String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
							FlagType flagType = null;
							for (FlagType fT : FlagType.values()) {
								if (fT.itemName.equalsIgnoreCase(name)) {
									flagType = fT;
								}
							}

							if (FlagManager.flagState.get(flagType)) {
								FlagManager.flagState.put(flagType, false);
								HandlerList.unregisterAll(flagType.eventListener);
							} else {
								FlagManager.flagState.put(flagType, true);
								if(flagType.eventListener != null)
									Bukkit.getPluginManager().registerEvents(flagType.eventListener, Main.instance);
							}
							ConfigManager.updateConfig();
							changeFlag(flagType.disableType, e.getSlot(), name, FlagManager.flagState.get(flagType));

							if (flagType == FlagType.DISABLE_COOLDOWN)
								FlagManager.updateNoCooldown();
							
							final FlagType fType = flagType;
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
								
								@Override
								public void run() {
									if (fType == FlagType.DISABLE_PLAYER_COLLIDE)
										CollisionManager.updateCollisionRule();
								}
							}, 2);
						}
					}
				}
			}
		}
	}

	private void changeFlag(DisableType type, int slot, String name, boolean flag) {
		Inventory inv = null;

		if (type == DisableType.GENERAL) {
			inv = InventoryManager.flagInvMain;
		} else if (type == DisableType.RECIPE) {
			inv = InventoryManager.flagInvRecipe;
		}

		if (Main.version >= 13) {
			Material color;
			if (flag) {
				name = "§a" + name;
				color = Enum.valueOf(Material.class, "GREEN_TERRACOTTA");
			} else {
				name = "§c" + name;
				color = Enum.valueOf(Material.class, "RED_TERRACOTTA");
			}

			inv.setItem(slot, new ItemBuilder(color).addDisplayName(name).buildItem());
		} else {
			int color;
			if (flag) {
				name = "§a" + name;
				color = 13;
			} else {
				name = "§c" + name;
				color = 14;
			}

			inv.setItem(slot, new ItemBuilder(Enum.valueOf(Material.class, "STAINED_CLAY")).setData(color).addDisplayName(name)
					.buildItem());
		}
	}

}
