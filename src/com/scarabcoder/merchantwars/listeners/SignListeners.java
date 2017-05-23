package com.scarabcoder.merchantwars.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.scarabcoder.gameapi.enums.GamePlayerType;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.gui.InventoryGUI;
import com.scarabcoder.gameapi.manager.PlayerManager;
import com.scarabcoder.merchantwars.shop.Coins;

import net.md_5.bungee.api.ChatColor;

public class SignListeners implements Listener {
	
	@EventHandler
	public void onSignPlace(SignChangeEvent e){
		if(e.getPlayer().hasPermission("merchantwars.admin")){
			System.out.println(e.getLine(0));
			if(e.getLine(0).equals("[Shop]")){
				e.setLine(0, "[" + ChatColor.GOLD + "Shop" + ChatColor.RESET + "]");
			}
		}
	}
	
	public boolean isSign(Block block){
		return block.getType().equals(Material.SIGN) || block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN);
	}
	
	@EventHandler
	public void onSignInteract(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(this.isSign(e.getClickedBlock())){
				GamePlayer p = PlayerManager.getGamePlayer(e.getPlayer());
				Sign s = (Sign) e.getClickedBlock().getState();
				if(p.getGame().getGamePlayerType(p).equals(GamePlayerType.PLAYER) && s.getLine(0).contains("Shop")){
					InventoryGUI g = new InventoryGUI("Shop");

					ItemStack balance = new ItemStack(Material.EMERALD);
					ItemMeta m = balance.getItemMeta();
					m.setDisplayName(ChatColor.GREEN.toString() + Coins.getBalance(p) + " Coins");
					balance.setItemMeta(m);
					
					ItemStack weaponMenu = new ItemStack(Material.DIAMOND_SWORD);
					m.setDisplayName(ChatColor.AQUA + "Weapons");
					weaponMenu.setItemMeta(m);
					g.setButton("weapons", 30, weaponMenu);
					
					ItemStack toolMenu = new ItemStack(Material.TNT);
					m.setDisplayName(ChatColor.AQUA + "Special");
					toolMenu.setItemMeta(m);
					g.setButton("special", 32, toolMenu);
					
					ItemStack armorMenu = new ItemStack(Material.DIAMOND_CHESTPLATE);
					m.setDisplayName(ChatColor.AQUA + "Armor");
					armorMenu.setItemMeta(m);
					g.setButton("armor", 31, armorMenu);
					
					ItemStack teamMenu = new ItemStack(Material.LEATHER_CHESTPLATE);
					LeatherArmorMeta lm = (LeatherArmorMeta) teamMenu.getItemMeta();
					lm.setDisplayName(ChatColor.AQUA + "Team Upgrades");
					lm.setColor(p.getTeam().getColor());
					teamMenu.setItemMeta(lm);
					g.setButton("team", 40, teamMenu);
					
					g.setButton("balance", 13, balance);
					g.open(p);
				}
			}
		}
			
	}
	
}
