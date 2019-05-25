package de.epicmc.roots.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import de.epicmc.roots.Main;
import de.epicmc.roots.utils.DisableType;
import de.epicmc.roots.utils.FlagType;
import de.epicmc.roots.utils.ItemBuilder;

public class EVENT_InventoryClick implements Listener{
	
	@EventHandler
	public void onClick(final InventoryClickEvent e){
		if(e.getInventory() != null){
			if(e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.PLAYER)){
				if(Main.DISABLE_OFF_HAND){
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
						@Override
						public void run() {
							if(e.getWhoClicked().getInventory().getItemInOffHand() != null){
								final ItemStack item = e.getWhoClicked().getInventory().getItemInOffHand();
								e.getWhoClicked().getInventory().setItemInOffHand(null);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
									@Override
									public void run() {
										e.getWhoClicked().getInventory().addItem(item);	
									}
								},3);
							}
						}
					}, 5);
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
						@Override
						public void run() {
							if(e.getWhoClicked().getInventory().getItemInOffHand() != null){
								final ItemStack item = e.getWhoClicked().getInventory().getItemInOffHand();
								e.getWhoClicked().getInventory().setItemInOffHand(null);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
									@Override
									public void run() {
										e.getWhoClicked().getInventory().addItem(item);	
									}
								},10);
							}
						}
					}, 20);
				}
			} else {
				if(e.getInventory().getHolder() == Main.BTTRHolder){
					if(e.getInventory().getHolder() == Main.BTTRHolder){
						if(e.getCurrentItem() != null){
							e.setCancelled(true);
							Material type = e.getCurrentItem().getType();
							Player p = (Player)e.getWhoClicked();
							
							if(type == Material.BOOK){
								p.openInventory(Main.flagInvMain);
							} else if(type == Material.CRAFTING_TABLE){
								p.openInventory(Main.flagInvRecipe);
							} else if(type == Material.BARRIER){
								p.openInventory(Main.flagInv);
							} else if(type == Material.GREEN_TERRACOTTA || type == Material.RED_TERRACOTTA){
								p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);
								
								String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
								FlagType flagtype = FlagType.getByName(name);
								
								if(flagtype == FlagType.DISABLE_COOLDOWN){
									if(Main.DISABLE_COOLDOWN){
										Main.DISABLE_COOLDOWN = false;
										
										for(Player player : Bukkit.getOnlinePlayers()){
											player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4D);
										}
										
										changeInConfig("DISABLE.COOLDOWN", false);
									} else {
										Main.DISABLE_COOLDOWN = true;
										
										for(Player player : Bukkit.getOnlinePlayers()){
											player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32D);
										}
										
										changeInConfig("DISABLE.COOLDOWN", true);
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_COOLDOWN);
								} else if(flagtype == FlagType.DISABLE_NEW_DAMAGE){
									if(Main.DISABLE_NEW_DAMAGE){
										Main.DISABLE_NEW_DAMAGE = false;
										
										changeInConfig("DISABLE.NEW_DAMAGE", false);
									} else {
										Main.DISABLE_NEW_DAMAGE = true;
										
										changeInConfig("DISABLE.NEW_DAMAGE", true);
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_NEW_DAMAGE);
								} else if(flagtype == FlagType.DISABLE_NEW_REGEN){
									if(Main.DISABLE_NEW_REGEN){
										Main.DISABLE_NEW_REGEN = false;
										
										changeInConfig("DISABLE.NEW_REGEN", false);
									} else {
										Main.DISABLE_NEW_REGEN = true;
										
										changeInConfig("DISABLE.NEW_REGEN", true);
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_NEW_REGEN);
								} else if(flagtype == FlagType.DISABLE_PATH_MAKE){
									if(Main.DISABLE_PATH_MAKE){
										Main.DISABLE_PATH_MAKE = false;
										
										changeInConfig("DISABLE.PATH_MAKE", false);
									} else {
										Main.DISABLE_PATH_MAKE = true;
										
										changeInConfig("DISABLE.PATH_MAKE", true);
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_PATH_MAKE);
								} else if(flagtype == FlagType.DISABLE_OFF_HAND){
									if(Main.DISABLE_OFF_HAND){
										Main.DISABLE_OFF_HAND = false;
										
										changeInConfig("DISABLE.OFF_HAND", false);
									} else {
										Main.DISABLE_OFF_HAND = true;
										
										changeInConfig("DISABLE.OFF_HAND", true);
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_OFF_HAND);
								} else if(flagtype == FlagType.DISABLE_SKELETON_TRAP){
									if(Main.DISABLE_SKELETON_TRAP){
										Main.DISABLE_SKELETON_TRAP = false;
										
										changeInConfig("DISABLE.SKELETON_TRAP", false);
									} else {
										Main.DISABLE_SKELETON_TRAP = true;
										
										changeInConfig("DISABLE.SKELETON_TRAP", true);
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_SKELETON_TRAP);
								} else if(flagtype == FlagType.DISABLE_PLAYER_COLLIDE){
									if(Main.DISABLE_PLAYER_COLLIDE){
										Main.DISABLE_PLAYER_COLLIDE = false;
										
										changeInConfig("DISABLE.PLAYER_COLLIDE", false);
										
										for(Player pl : Bukkit.getOnlinePlayers()){
											Scoreboard sb = pl.getScoreboard();
											
											if(sb.getEntryTeam(pl.getName()) != null){
												sb.getEntryTeam(pl.getName()).setOption(Option.COLLISION_RULE, OptionStatus.ALWAYS);
											}
										}
									} else {
										Main.DISABLE_PLAYER_COLLIDE = true;
										
										changeInConfig("DISABLE.PLAYER_COLLIDE", true);
										
										for(Player pl : Bukkit.getOnlinePlayers()){
											Scoreboard sb = pl.getScoreboard();
											
											if(sb.getEntryTeam(pl.getName()) != null){
												sb.getEntryTeam(pl.getName()).setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
											} else {
												Team t = null;
												
												if(sb.getTeam("acollide") != null){
													t = sb.getTeam("acollide");
												} else {
													t = sb.registerNewTeam("acollide");
												}
												
												t.addEntry(pl.getName());
												t.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
											}
										}
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_PLAYER_COLLIDE);
								} else if(flagtype == FlagType.DISABLE_CHORUS_FRUIT){
									if(Main.DISABLE_CHORUS_FRUIT){
										Main.DISABLE_CHORUS_FRUIT = false;
										
										changeInConfig("DISABLE.CHORUS_FRUIT", false);
									} else {
										Main.DISABLE_CHORUS_FRUIT = true;
										
										changeInConfig("DISABLE.CHORUS_FRUIT", true);
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_CHORUS_FRUIT);
								} else if(flagtype == FlagType.DISABLE_ENDERMITE_ON_PEARL){
									if(Main.DISABLE_ENDERMITE_ON_PEARL){
										Main.DISABLE_ENDERMITE_ON_PEARL = false;
										
										changeInConfig("DISABLE.ENDERMITE_ON_PEARL", false);
									} else {
										Main.DISABLE_ENDERMITE_ON_PEARL = true;
										
										changeInConfig("DISABLE.ENDERMITE_ON_PEARL", true);
									}
									
									changeFlag(DisableType.GENERAL, e.getSlot(), name, Main.DISABLE_ENDERMITE_ON_PEARL);
								} else if(flagtype == FlagType.DISABLE_RECIPE_SHIELD){
									if(Main.DISABLE_RECIPE_SHIELD){
										Main.DISABLE_RECIPE_SHIELD = false;
										changeInConfig("DISABLE_RECIPE.SHIELD", false);
									} else {
										Main.DISABLE_RECIPE_SHIELD = true;
										changeInConfig("DISABLE_RECIPE.SHIELD", true);
									}
									
									changeFlag(DisableType.RECIPE, e.getSlot(), name, Main.DISABLE_RECIPE_SHIELD);
								} else if(flagtype == FlagType.DISABLE_RECIPE_END_CRYSTAL){
									if(Main.DISABLE_RECIPE_END_CRYSTAL){
										Main.DISABLE_RECIPE_END_CRYSTAL = false;
										changeInConfig("DISABLE_RECIPE.END_CRYSTAL", false);
									} else {
										Main.DISABLE_RECIPE_END_CRYSTAL = true;
										changeInConfig("DISABLE_RECIPE.END_CRYSTAL", true);
									}
									
									changeFlag(DisableType.RECIPE, e.getSlot(), name, Main.DISABLE_RECIPE_END_CRYSTAL);
								} else if(flagtype == FlagType.DISABLE_RECIPE_SHULKER_BOX){
									if(Main.use11){
										if(Main.DISABLE_RECIPE_SHULKER_BOX){
											Main.DISABLE_RECIPE_SHULKER_BOX = false;
											changeInConfig("DISABLE_RECIPE.SHULKER_BOX", false);
										} else {
											Main.DISABLE_RECIPE_SHULKER_BOX = true;
											changeInConfig("DISABLE_RECIPE.SHULKER_BOX", true);
										}
										
										changeFlag(DisableType.RECIPE, e.getSlot(), name, Main.DISABLE_RECIPE_SHULKER_BOX);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void changeFlag(DisableType type, int slot, String name, boolean flag){
		Inventory inv = null;
		
		if(type == DisableType.GENERAL){
			inv = Main.flagInvMain;
		} else if(type == DisableType.RECIPE){
			inv = Main.flagInvRecipe;
		}
		
		Material color;
		
		if(flag){
			name = "§a" + name;
			color = Material.GREEN_TERRACOTTA;
		} else {
			name = "§c" + name;
			color = Material.RED_TERRACOTTA;
		}
		
		inv.setItem(slot, new ItemBuilder(color).addDisplayName(name).buildItem());
	}
	
	private void changeInConfig(String path, boolean value){
		File f = new File("plugins/BackToTheRoots/config.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.set(path, value);
		
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
