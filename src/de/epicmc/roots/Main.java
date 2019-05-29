package de.epicmc.roots;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
import de.epicmc.roots.manager.ConfigManager;
import de.epicmc.roots.manager.InventoryManager;
import de.epicmc.roots.events.EVENT_PlayerJoin;
import de.epicmc.roots.events.EVENT_PlayerQuit;

public class Main extends JavaPlugin{
	
	public static Plugin instance;
	
	public void onEnable(){
		System.out.println(" ");
		System.out.println("[BTTR] Loading plugin ...");
		instance = this;
		
		InventoryManager.initializeInventories();
		ConfigManager.initializeConfig();
		
		registerEvents();
		Bukkit.getPluginCommand("bttr").setExecutor(new COMMAND_BTTR());
		
		System.out.println("[BTTR] Plugin successfully loaded.");
		System.out.println(" ");
	}
	
	
	
	private void registerEvents() {
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
	}
	
}
