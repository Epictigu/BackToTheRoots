package de.epicmc.roots.events;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
					} else if (type == Material.COMPASS) {
						p.openInventory(InventoryManager.worldInv);
					} else if (type == Material.DAYLIGHT_DETECTOR) {
						InventoryManager.initWorldInventory();
						InventoryManager.updateWorldInventories();
						p.openInventory(InventoryManager.worldInv);
					} else if (type == Material.DIRT || type == Material.NETHERRACK || type == Material.OBSIDIAN) {
						if (e.getCurrentItem().getItemMeta() != null) {
							if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
								String name = e.getCurrentItem().getItemMeta().getDisplayName();
								name = name.replaceAll("§7", "");

								World w = Bukkit.getWorld(name);
								if (w != null) {
									p.openInventory(InventoryManager.worldFlagInv.get(w));
								}
							}
						}
					} else if (type.toString().contains("TERRACOTTA") || type.toString().contains("CLAY")) {
						p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);

						String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
						FlagType flagType = null;
						for (FlagType fT : FlagType.values()) {
							if (fT.itemName.equalsIgnoreCase(name)) {
								flagType = fT;
							}
						}

						if (FlagManager.checkFlag(flagType, p)) {
							FlagManager.flagState.put(flagType, false);
						} else {
							FlagManager.flagState.put(flagType, true);
						}
						ConfigManager.updateConfig();
						changeFlag(flagType.disableType, e.getSlot(), name, FlagManager.checkFlag(flagType, p));

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
			} else if(e.getInventory().getHolder() == InventoryManager.customWorldInventoryHolder) {
				if(e.getCurrentItem() != null) {
					e.setCancelled(true);
					Material type = e.getCurrentItem().getType();
					Player p = (Player) e.getWhoClicked();
					
					if(type == Material.BARRIER) {
						p.openInventory(InventoryManager.worldInv);
					} else if(type.toString().contains("TERRACOTTA") || type.toString().contains("CLAY")) {
						p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);
						
						String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
						FlagType flagType = null;
						for (FlagType fT : FlagType.values()) {
							if (fT.itemName.equalsIgnoreCase(name)) {
								flagType = fT;
							}
						}
						
						String world = e.getView().getTitle().replaceAll("§6BTTR-World: §e", "");
						World w = Bukkit.getWorld(world);
						if(w == null) {
							return;
						}
						Map<FlagType, Boolean> state = FlagManager.flagStateWorld.get(w);
						
						if(!state.containsKey(flagType)) {
							state.put(flagType, true);
						} else if(state.get(flagType)) {
							state.put(flagType, false);
						} else {
							state.remove(flagType);
						}
						
						ConfigManager.saveWorldConfig(w);
						changeWorldFlag(flagType, e.getInventory(), e.getSlot(), name, state);
						
						if (flagType == FlagType.DISABLE_COOLDOWN)
							FlagManager.updateNoCooldown();
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

			inv.setItem(slot, new ItemBuilder(Enum.valueOf(Material.class, "STAINED_CLAY")).setData(color)
					.addDisplayName(name).buildItem());
		}
	}
	
	private void changeWorldFlag(FlagType type, Inventory inv, int slot, String name, Map<FlagType, Boolean> state) {

		if (Main.version >= 13) {
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

			inv.setItem(slot, new ItemBuilder(Enum.valueOf(Material.class, "STAINED_CLAY")).setData(color)
					.addDisplayName(name).buildItem());
		}
	}

}
