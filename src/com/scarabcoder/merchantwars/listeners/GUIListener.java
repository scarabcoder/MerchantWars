package com.scarabcoder.merchantwars.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.scarabcoder.gameapi.event.gui.InventoryButtonClickEvent;
import com.scarabcoder.gameapi.gui.InventoryGUI;
import com.scarabcoder.merchantwars.shop.Coins;

import net.md_5.bungee.api.ChatColor;



public class GUIListener implements Listener {
	
	@EventHandler
	public void buttonClick(InventoryButtonClickEvent e){
		System.out.println(e.getButtonID());
		if(e.getButtonID().equals("weapons")){
			InventoryGUI gui = new InventoryGUI("Shop - Weapons");
			
			int b = Coins.getBalance(e.getPlayer());
			
			ItemStack balance = new ItemStack(Material.EMERALD);
			ItemMeta m = balance.getItemMeta();
			m.setDisplayName(ChatColor.GREEN.toString() + b + " Coins");
			balance.setItemMeta(m);
			
			ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
			m.setDisplayName((b >= 6 ? ChatColor.GREEN : ChatColor.RED) + "Buy for 6 coins");
			stoneSword.setItemMeta(m);
			
			ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
			m.setDisplayName((b >= 10 ? ChatColor.GREEN : ChatColor.RED) + "Buy for 10 coins");
			ironSword.setItemMeta(m);
			
			ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
			m.setDisplayName((b >= 15 ? ChatColor.GREEN : ChatColor.RED) + "Buy for 15 coins");
			diamondSword.setItemMeta(m);
			
			gui.setButton("stoneSword", 29, stoneSword);
			gui.setButton("ironSword", 30, ironSword);
			gui.setButton("diamondSword", 31, diamondSword);
			gui.setButton("balance", 13, balance);
			
			gui.open(e.getPlayer());
		}else if(e.getButtonID().equals("armor")){
			
		}
	}
	
}
