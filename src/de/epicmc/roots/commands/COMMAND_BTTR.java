package de.epicmc.roots.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.epicmc.roots.Main;
import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.manager.InventoryManager;
import de.epicmc.roots.utils.FlagType;

public class COMMAND_BTTR implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[BTTR] Version: " + Main.instance.getDescription().getVersion());
			return true;
		}
		
		if(sender.hasPermission("bttr.command")){
			if(args.length > 1) {
				if(args[0].equalsIgnoreCase("checkflag")) {
					try {
						FlagType fT = FlagType.valueOf(args[1].toUpperCase());
						sender.sendMessage("§8[§6BTTR§8] §7Flag §e" + fT.toString() + " §7is at this position: §e" + FlagManager.checkFlag(fT, (Player)sender));
						return true;
					} catch (IllegalArgumentException e) {}
				}
			}
			
			((Player)sender).openInventory(InventoryManager.flagInv);
		} else {
			sender.sendMessage("§8[§6BTTR§8] §7You need the permission §e\"bttr.command\" §7for this.");
		}
		
		return true;
	}
	
}
