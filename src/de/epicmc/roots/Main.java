package de.epicmc.roots;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.epicmc.roots.commands.COMMAND_BTTR;
import de.epicmc.roots.events.EVENT_InventoryClick;
import de.epicmc.roots.events.EVENT_PlayerChangedWorld;
import de.epicmc.roots.manager.CollisionManager;
import de.epicmc.roots.manager.ConfigManager;
import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.manager.InventoryManager;
import de.epicmc.roots.events.EVENT_PlayerJoin;
import de.epicmc.roots.events.EVENT_PlayerQuit;

public class Main extends JavaPlugin{
	
	public static Plugin instance;
	public static int version;
	
	public void onEnable(){
		//Checking for current spigot version, so that multiple versions can be supported
		String packageName = getServer().getClass().getPackage().getName();
		version = Integer.parseInt(packageName.substring(packageName.lastIndexOf(".") + 1).split("_")[1]);
		
		System.out.println(" ");
		System.out.println("[BTTR] Loading plugin ...");
		instance = this;
		
		//Init of both Inventory and Config
		InventoryManager.initializeInventories();
		ConfigManager.initializeConfig();
		FlagManager.setupEvents();
		CollisionManager.initializeCollisionScheduler();
		
		registerEvents();
		Bukkit.getPluginCommand("bttr").setExecutor(new COMMAND_BTTR());
		
		System.out.println("[BTTR] Plugin successfully loaded.");
		System.out.println(" ");
	}
	
	
	
	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new EVENT_InventoryClick(), this);
		pm.registerEvents(new EVENT_PlayerChangedWorld(), this);
		
		EVENT_PlayerJoin join = new EVENT_PlayerJoin();
		
		//Run onJoin for everyone that is on the server, in case "/reload" was used
		for(Player p : Bukkit.getOnlinePlayers()){
			join.onJoin(new PlayerJoinEvent(p, ""));
		}
		
		pm.registerEvents(join, this);
		pm.registerEvents(new EVENT_PlayerQuit(), this);
	}
	
}
