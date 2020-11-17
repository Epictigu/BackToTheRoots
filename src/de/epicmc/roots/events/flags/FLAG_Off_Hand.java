package de.epicmc.roots.events.flags;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import de.epicmc.roots.Main;
import de.epicmc.roots.manager.FlagManager;
import de.epicmc.roots.utils.FlagType;

public class FLAG_Off_Hand implements Listener {

	@EventHandler
	public void onHeld(PlayerSwapHandItemsEvent e) {
		if(FlagManager.checkFlag(FlagType.DISABLE_OFF_HAND, e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(FlagManager.checkFlag(FlagType.DISABLE_OFF_HAND, e.getPlayer())) {
			ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
			if (item != null) {
				e.getPlayer().getInventory().setItemInOffHand(null);
				e.getPlayer().getInventory().addItem(item);
			}
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		if(FlagManager.checkFlag(FlagType.DISABLE_OFF_HAND, e.getPlayer())) {
			final Player p = e.getPlayer();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
				@Override
				public void run() {
					ItemStack item = p.getInventory().getItemInOffHand();
					if (item != null) {
						p.getInventory().setItemInOffHand(null);
						p.getInventory().addItem(item);
					}
				}
			}, 2);
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if(FlagManager.checkFlag(FlagType.DISABLE_OFF_HAND, e.getWhoClicked().getWorld())) {
			if(e.getInventory() != null){
				if(e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.PLAYER)){
					final HumanEntity p = e.getWhoClicked();
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
						@Override
						public void run() {
							if(p.getInventory().getItemInOffHand() != null){
								final ItemStack item = p.getInventory().getItemInOffHand();
								p.getInventory().setItemInOffHand(null);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
									@Override
									public void run() {
										p.getInventory().addItem(item);	
									}
								},3);
							}
						}
					}, 5);
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
						@Override
						public void run() {
							if(p.getInventory().getItemInOffHand() != null){
								final ItemStack item = p.getInventory().getItemInOffHand();
								p.getInventory().setItemInOffHand(null);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
									@Override
									public void run() {
										p.getInventory().addItem(item);	
									}
								},10);
							}
						}
					}, 20);
				}
			}
		}
		
	}

}
