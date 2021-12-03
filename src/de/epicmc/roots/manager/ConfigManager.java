package de.epicmc.roots.manager;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import de.epicmc.roots.Main;
import de.epicmc.roots.utils.FlagType;

public class ConfigManager {
	
	public static void initializeConfig() {
		Main.instance.getLogger().info("[BTTR] Loading config ...");
		String version = Main.instance.getDescription().getVersion();
		
		//Creation of main folder if needed
		File mainFolder = new File("plugins/BackToTheRoots");
		if(!(mainFolder.exists())){
			mainFolder.mkdir();
		}
		
		File worldFolder = new File("plugins/BackToTheRoots/worlds");
		if(!worldFolder.exists()) {
			worldFolder.mkdir();
		}
		
		//Creating or updating of the main config
		File configFile = new File("plugins/BackToTheRoots/config.yml");
		if(!(configFile.exists())){
			Main.instance.getLogger().info("[BTTR] No config found.");
			Main.instance.getLogger().info("[BTTR] Creating new one ...");
			try {
				configFile.createNewFile();
				YamlConfiguration cfg = new YamlConfiguration();
				
				cfg.set("CONFIG_VERSION", version);
				for(FlagType cfgV : FlagType.values()) {
					cfg.set(cfgV.configPath, cfgV.defaultValue);
				}
				
				cfg.save(configFile);
			} catch (IOException e) { e.printStackTrace(); }
		} else {
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
			
			//Checking if config version is different than plugin version
			if(!(cfg.getString("CONFIG_VERSION").equalsIgnoreCase(version))){
				Main.instance.getLogger().info("[BTTR] Updating config ...");
				cfg.set("CONFIG_VERSION", version);
				
				//Set default value onto every missing flag
				for(FlagType cfgV : FlagType.values()) {
					if(cfg.get(cfgV.configPath) == null) {
						cfg.set(cfgV.configPath, cfgV.defaultValue);
					}
				}
				
				try {
					cfg.save(configFile);
				} catch (IOException e) { e.printStackTrace(); }
				
				Main.instance.getLogger().info("[BTTR] Update complete!");
			}
		}
		
		//Load values from config
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
		for(FlagType cfgV : FlagType.values()) {
			if(cfgV.minVersion <= Main.version) {
				FlagManager.flagState.put(cfgV, cfg.getBoolean(cfgV.configPath));
				InventoryManager.addDisable(cfgV);
			}
		}
	}
	
	public static void saveWorldConfig(World w) {
		File f = new File("plugins/BackToTheRoots/worlds/" + w.getName() + ".yml");
		Map<FlagType, Boolean> state = FlagManager.flagStateWorld.get(w);
		if(f.exists())
			f.delete();
		if(state == null) {
			return;
		} else if(state.isEmpty()) {
			return;
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		YamlConfiguration cfg = new YamlConfiguration();
		for(FlagType fT : state.keySet()) {
			cfg.set(fT.configPath, state.get(fT));
		}
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateConfig() {
		File configFile = new File("plugins/BackToTheRoots/config.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
		
		//Save new values to config
		for(FlagType fT : FlagType.values()) {
			cfg.set(fT.configPath, FlagManager.flagState.get(fT));
		}
		
		try {
			cfg.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
