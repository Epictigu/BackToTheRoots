package de.epicmc.roots.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.epicmc.roots.Main;
import de.epicmc.roots.utils.FlagType;

public class ConfigManager {
	
	public static void initializeConfig() {
		System.out.println("[BTTR] Loading config ...");
		String version = Main.instance.getDescription().getVersion();
		
		//Creation of main folder if needed
		File mainFolder = new File("plugins/BackToTheRoots");
		if(!(mainFolder.exists())){
			mainFolder.mkdir();
		}
		
		//Creation or updating of config
		File configFile = new File("plugins/BackToTheRoots/config.yml");
		if(!(configFile.exists())){
			System.out.println("[BTTR] No config found.");
			System.out.println("[BTTR] Creating new one ...");
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
				System.out.println("[BTTR] Updating config ...");
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
				
				System.out.println("[BTTR] Update complete!");
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
