package de.epicmc.roots.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemBuilder {
	
	private Material material;
	private int amount = 1;
	private byte data = 0;
	
	private boolean displayname_used = false;
	private String displayname = "";
	
	private List<String> lore = new ArrayList<String>();
	
	private Map<Enchantment, Integer> enchantment = new HashMap<Enchantment, Integer>();
	
	private Color color;
	
	public ItemBuilder(Material material){
		this.material = material;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public ItemBuilder setAmount(int amount){
		this.amount = amount;
		return this;
	}
	
	public ItemBuilder setData(int data){
		this.data = (byte)data;
		return this;
	}
	
	public ItemBuilder addDisplayName(String displayname){
		this.displayname_used = true;
		this.displayname = displayname;
		return this;
	}
	
	public ItemBuilder addLore(String... lore){
		for(String s : lore){
			this.lore.add(s);
		}
		
		return this;
	}
	
	public ItemBuilder resetLore(){
		this.lore.clear();
		return this;
	}
	
	public ItemBuilder addEnchantment(Enchantment enchantment, int enchantmentlevel){
		this.enchantment.put(enchantment, enchantmentlevel);
		
		return this;
	}
	
	public ItemBuilder changeColor(Color color){
		this.color = color;
		return this;
	}
	
	public ItemStack buildItem(){
		ItemStack is;
		if(data == 0) {
			is = new ItemStack(material, amount);
		} else {
			is = new ItemStack(material, amount, data);
		}
		is.addUnsafeEnchantments(enchantment);
		ItemMeta ism = is.getItemMeta();
		if(displayname_used){
			ism.setDisplayName(displayname);
		}
		ism.setLore(lore);
		is.setItemMeta(ism);
		if(color != null){
			LeatherArmorMeta lam = (LeatherArmorMeta)is.getItemMeta();
			lam.setColor(color);
			is.setItemMeta(lam);
		}
		
		return is;
	}
	
}
