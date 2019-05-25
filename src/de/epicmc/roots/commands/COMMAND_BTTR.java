package de.epicmc.roots.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.epicmc.roots.Main;

public class COMMAND_BTTR implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[BTTR] Version: " + Main.instance.getDescription().getVersion());
			return true;
		}
		
		if(sender.hasPermission("bttr.command")){
			((Player)sender).openInventory(Main.flagInv);
		} else {
			sender.sendMessage("§8[§6BTTR§8] §7You need the permission §e\"bttr.command\" §7for this.");
		}
		
		return true;
	}
	
}
