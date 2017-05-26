package com.scarabcoder.merchantwars.listeners;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.scarabcoder.gameapi.event.gui.InventoryButtonClickEvent;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.gui.InventoryGUI;
import com.scarabcoder.merchantwars.shop.Coins;
import com.scarabcoder.merchantwars.shop.Respawns;
import com.scarabcoder.merchantwars.shop.ShopItems;

import net.md_5.bungee.api.ChatColor;



public class GUIListener implements Listener {
	
	@EventHandler
	public void buttonClick(InventoryButtonClickEvent e){
		System.out.println(e.getButtonID());
		
		int b = Coins.getBalance(e.getPlayer());
		ItemStack balance = new ItemStack(Material.EMERALD);
		ItemMeta m = balance.getItemMeta();
		m.setDisplayName(ChatColor.GREEN.toString() + b + " Coins");
		balance.setItemMeta(m);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		m.setDisplayName(ChatColor.GREEN + "<- Back");
		back.setItemMeta(m);
		switch(e.getButtonID()){
		case "weapons":
			GUIListener.openWeaponGUI(e.getPlayer());
			break;
		case "armor":
			GUIListener.openArmorGUI(e.getPlayer());
			break;
		case "special":
			GUIListener.openSpecialGUI(e.getPlayer());
			break;
		case "team":
			GUIListener.openTeamGUI(e.getPlayer());
			break;
		case "back":
			GUIListener.openShopGUI(e.getPlayer());
				break;
		}
		
		switch(e.getButtonID()){
		case "stoneSword":
			if(b >= ShopItems.getCost("stoneSword")){
				e.getPlayer().getOnlinePlayer().getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("stoneSword"));
			}
			break;
		case "ironSword":
			if(b >= ShopItems.getCost("ironSword")){
				e.getPlayer().getOnlinePlayer().getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("ironSword"));
			}
			break;
		case "diamondSword":
			if(b >= ShopItems.getCost("diamondSword")){
				e.getPlayer().getOnlinePlayer().getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("diamondSword"));
			}
			break;
		case "diamondSword1":
			if(b >= ShopItems.getCost("diamondSword1")){
				ItemStack ds1 = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta im = ds1.getItemMeta();
				im.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
				ds1.setItemMeta(im);
				e.getPlayer().getOnlinePlayer().getInventory().setItem(0, ds1);
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("diamondSword1"));
			}
			break;
		case "diamondSword2":
			if(b >= ShopItems.getCost("diamondSword2")){
				ItemStack ds2 = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta im = ds2.getItemMeta();
				im.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
				ds2.setItemMeta(im);
				e.getPlayer().getOnlinePlayer().getInventory().setItem(0, ds2);
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("diamondSword2"));
			}
			break;
		case "chainArmor":
			if(b >= ShopItems.getCost("chainArmor")){
				m = e.getPlayer().getOnlinePlayer().getInventory().getBoots().getItemMeta();
				ItemStack l = new ItemStack(Material.CHAINMAIL_LEGGINGS);
				ItemStack ab = new ItemStack(Material.CHAINMAIL_BOOTS);
				l.setItemMeta(m);
				ab.setItemMeta(m);
				e.getPlayer().getOnlinePlayer().getInventory().setLeggings(l);
				e.getPlayer().getOnlinePlayer().getInventory().setBoots(ab);
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("chainArmor"));
			}
			break;
		case "ironArmor":
			if(b >= ShopItems.getCost("ironArmor")){
				m = e.getPlayer().getOnlinePlayer().getInventory().getBoots().getItemMeta();
				ItemStack l = new ItemStack(Material.IRON_LEGGINGS);
				ItemStack ab = new ItemStack(Material.IRON_LEGGINGS);
				l.setItemMeta(m);
				ab.setItemMeta(m);
				e.getPlayer().getOnlinePlayer().getInventory().setLeggings(l);
				e.getPlayer().getOnlinePlayer().getInventory().setBoots(ab);
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("ironArmor"));
			}
			break;
		case "diamondArmor":
			if(b >= ShopItems.getCost("diamondArmor")){
				m = e.getPlayer().getOnlinePlayer().getInventory().getBoots().getItemMeta();
				ItemStack l = new ItemStack(Material.DIAMOND_LEGGINGS);
				ItemStack ab = new ItemStack(Material.DIAMOND_BOOTS);
				l.setItemMeta(m);
				ab.setItemMeta(m);
				e.getPlayer().getOnlinePlayer().getInventory().setLeggings(l);
				e.getPlayer().getOnlinePlayer().getInventory().setBoots(ab);
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("diamondArmor"));
			}
			break;
		case "thorns":
			if(b >= ShopItems.getCost("thorns")){
				ItemStack dl = new ItemStack(Material.IRON_LEGGINGS);
				ItemStack db = new ItemStack(Material.IRON_BOOTS);
				ItemMeta im = dl.getItemMeta();
				im.addEnchant(Enchantment.THORNS, 2, false);
				if(e.getPlayer().getOnlinePlayer().getInventory().getBoots().containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) 
					im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 
						e.getPlayer().getOnlinePlayer().getInventory().getBoots().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL), true);
				dl.setItemMeta(im);
				db.setItemMeta(im);
				e.getPlayer().getOnlinePlayer().getInventory().setLeggings(dl);
				e.getPlayer().getOnlinePlayer().getInventory().setBoots(db);
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("thorns"));
			}
			break;
		case "tnt":
			if(b >= ShopItems.getCost("tnt")){
				e.getPlayer().getOnlinePlayer().getInventory().addItem(new ItemStack(Material.TNT));
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("tnt"));
			}
			break;
		case "enderpearl":
			if(b >= ShopItems.getCost("enderpearl")){
				e.getPlayer().getOnlinePlayer().getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("enderpearl"));
			}
			break;
		case "jump":
			if(b >= ShopItems.getCost("jump")){
				ItemStack s = e.getItemStack();
				PotionMeta pm = (PotionMeta) s.getItemMeta();
				pm.setDisplayName(null);
				s.setItemMeta(pm);
				e.getPlayer().getOnlinePlayer().getInventory().addItem(s);
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("jump"));
			}
			break;
		case "poison":
			if(b >= ShopItems.getCost("poison")){
				ItemStack s = e.getItemStack();
				PotionMeta pm = (PotionMeta) s.getItemMeta();
				pm.setDisplayName(null);
				e.getPlayer().getOnlinePlayer().getInventory().addItem(s);
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("poison"));
			}
			break;
		case "goldapple":
			if(b >= ShopItems.getCost("goldapple")){
				e.getPlayer().getOnlinePlayer().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("goldapple"));
			}
			break;
		case "respawn":
			if(b >= ShopItems.getCost("respawn")){
				Respawns.setRespawns(e.getPlayer(), Respawns.getRespawns(e.getPlayer()) + 1);
				e.getPlayer().getOnlinePlayer().sendMessage(ChatColor.GREEN + "+1 Respawn");
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("respawn"));
			}
			break;
		case "buyAllLives":
			if(b >= ShopItems.getCost("buyAllLives")){
				for(GamePlayer p : e.getPlayer().getTeam().getPlayers()){
					Respawns.setRespawns(p, Respawns.getRespawns(p) + 1);
					p.getOnlinePlayer().sendMessage(p.getTeam().getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.RESET + " purchased respawns for your team.");
					p.getOnlinePlayer().sendMessage(ChatColor.GREEN + "+1 Respawn");
				}
				Coins.removeBalance(e.getPlayer(), ShopItems.getCost("buyAllLives"));
			}
			break;
		case "upgradeTeamArmor":
			int l = ShopItems.getLevel(e.getPlayer().getTeam(), "defence");
			if(b >= (l + 1) * 6 + ShopItems.getCost("upgradeTeamArmor") && l != 3){
				Coins.removeBalance(e.getPlayer(), (l + 1) * 6 + ShopItems.getCost("upgradeTeamArmor"));
				for(GamePlayer p : e.getPlayer().getTeam().getPlayers()){
					if(p.isOnline()){
						ShopItems.setLevel(e.getPlayer().getTeam(), "defence", ShopItems.getLevel(e.getPlayer().getTeam(), "defence") + 1);
						l++;
						ItemStack al = p.getOnlinePlayer().getInventory().getLeggings();
						ItemStack ab = p.getOnlinePlayer().getInventory().getBoots();
						m = al.getItemMeta();
						m.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, l, true);
						if(al != null){
							al.setItemMeta(m);
							p.getOnlinePlayer().getInventory().setLeggings(al);
						}
						if(ab != null){
							ab.setItemMeta(m);
							p.getOnlinePlayer().getInventory().setBoots(ab);
						}
					}
				}
				e.getPlayer().getTeam().sendMessage(e.getPlayer().getTeam().getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.RESET + " purchased Better Player Defence " + (l+1));
			}
			break;
		case "upgradeVillagerArmor":
			l = ShopItems.getLevel(e.getPlayer().getTeam(), "villagerdefence");
			if(b >= (l + 1) * 6 + ShopItems.getCost("upgradeVillagerArmor") && l != 3){
				ShopItems.setLevel(e.getPlayer().getTeam(), "villagerdefence", l + 1);
				Coins.removeBalance(e.getPlayer(), (l + 1) * 6 + ShopItems.getCost("upgradeVillagerArmor"));
				e.getPlayer().getTeam().sendMessage(e.getPlayer().getTeam().getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.RESET + " purchased Better Villager Defence " + (l+1));
			}
			break;
		case "upgradeLighter":
			l = ShopItems.getLevel(e.getPlayer().getTeam(), "lighter");
			if(b >= (l + 1) * 6 + ShopItems.getCost("upgradeLighter") && l != 3){
				ShopItems.setLevel(e.getPlayer().getTeam(), "lighter", l+1);
				Coins.removeBalance(e.getPlayer(), (l + 1) * 6 + ShopItems.getCost("upgradeLighter"));
				e.getPlayer().getTeam().sendMessage(e.getPlayer().getTeam().getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.RESET + " purchased Lighter Merchant " + (l+1));
			}
			break;
		case "upgradeTrading":
			l = ShopItems.getLevel(e.getPlayer().getTeam(), "trading");
			if(b >= (l + 1) * 6 + ShopItems.getCost("upgradeLighter") && l != 3){
				ShopItems.setLevel(e.getPlayer().getTeam(), "trading", l+1);
				Coins.removeBalance(e.getPlayer(), (l + 1) * 6 + ShopItems.getCost("upgradeTrading"));
				e.getPlayer().getTeam().sendMessage(e.getPlayer().getTeam().getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.RESET + " purchased Better Trading " + (l+1));
			}
			break;
		}
		if(e.getButtonID() != "back"){
			switch(e.getInventoryGUI().getInventory().getName()){
			case "Shop - Weapons":
				GUIListener.openWeaponGUI(e.getPlayer());
				break;
			case "Shop - Armor":
				GUIListener.openArmorGUI(e.getPlayer());
				break;
			case "Shop - Special":
				GUIListener.openSpecialGUI(e.getPlayer());
				break;
			case "Shop - Team Upgrades":
				GUIListener.openTeamGUI(e.getPlayer());
				break;
			}
		}
	}
	
	public static void openTeamGUI(GamePlayer p){
		int b = Coins.getBalance(p);
		ItemStack balance = new ItemStack(Material.EMERALD);
		ItemMeta m = balance.getItemMeta();
		m.setDisplayName(ChatColor.GREEN.toString() + b + " Coins");
		balance.setItemMeta(m);
		ItemStack back = new ItemStack(Material.BARRIER);
		m.setDisplayName(ChatColor.GREEN + "<- Back");
		back.setItemMeta(m);
		InventoryGUI gui = new InventoryGUI("Shop - Team Upgrades");
		
		gui.setButton("balance", 13, balance);
		gui.setButton("back", 0, back);
		
		int c = ShopItems.getCost("upgradeTeamArmor");
		ItemStack protection = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta lm = (LeatherArmorMeta) protection.getItemMeta();
		lm.setColor(p.getTeam().getColor());
		int lvl = ShopItems.getLevel(p.getTeam(), "defence");
		lm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, (lvl != 3 ? 1 : 0) + lvl, true);
		lm.setDisplayName((lvl != 3 ? (b >= ((lvl + 1) * 6) + c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ((lvl + 1) * 6 + c) + " coins" : ChatColor.RED + "Maxed out"));
		lm.setLore(Arrays.asList(ChatColor.AQUA.toString() + ChatColor.BOLD + "Better Player Defence", ChatColor.AQUA + "Increases protection on your team's armor.", ChatColor.AQUA + "Current: " + (lvl)));
		protection.setItemMeta(lm);
		gui.setButton("upgradeTeamArmor", 29, protection);
		
		c = ShopItems.getCost("upgradeVillagerArmor");
		ItemStack vProtection = new ItemStack(Material.IRON_BARDING);
		lvl = ShopItems.getLevel(p.getTeam(), "villagerdefence");
		m.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, (lvl != 3 ? 1 : 0) + lvl, true);
		m.setDisplayName((lvl != 3 ? (b >= (lvl + 1) * 5 + c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ((lvl + 1) * 5 + c) + " coins" : ChatColor.RED + "Maxed out"));
		m.setLore(Arrays.asList(ChatColor.AQUA.toString() + ChatColor.BOLD + "Better Merchant Defence", ChatColor.AQUA + "Decreases the damage your merchant takes.", ChatColor.AQUA + "Current: " + lvl));
		vProtection.setItemMeta(m);
		m = balance.getItemMeta();
		gui.setButton("upgradeVillagerArmor", 30, vProtection);
		
		c = ShopItems.getCost("upgradeLighter");
		ItemStack lighter = new ItemStack(Material.ELYTRA);
		lvl = ShopItems.getLevel(p.getTeam(), "lighter");
		m.setDisplayName((lvl != 3 ? (b >= (lvl + 1) * 5 + c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ((lvl + 1) * 5 + c) + " coins" : ChatColor.RED + "Maxed out"));
		m.setLore(Arrays.asList(ChatColor.AQUA + ChatColor.BOLD.toString() + "Lighter Merchant", ChatColor.AQUA + "Makes your merchant lighter, increases speed ", ChatColor.AQUA + "while carrying.", ChatColor.AQUA + "Current: " + lvl));
		lighter.setItemMeta(m);
		m = balance.getItemMeta();
		gui.setButton("upgradeLighter", 31, lighter);
		
		c = ShopItems.getCost("upgradeTrading");
		ItemStack trade = new ItemStack(Material.EMERALD_ORE);
		lvl = ShopItems.getLevel(p.getTeam(), "trading");
		m.setDisplayName((lvl != 3 ? (b >= (lvl + 1) * 6 + c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ((lvl + 1) * 6 + c) + " coins" : ChatColor.RED + "Maxed out"));
		m.setLore(Arrays.asList(ChatColor.AQUA.toString() + ChatColor.BOLD + "Better Trading", ChatColor.AQUA + "Your team will get 5 more coins when your", ChatColor.AQUA + "merchant is escorted successfully.", ChatColor.AQUA + "Current: " + lvl));
		trade.setItemMeta(m);
		gui.setButton("upgradeTrading", 32, trade);
		
		c = ShopItems.getCost("buyAllLives");
		ItemStack allLives = new ItemStack(Material.TOTEM);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		m.setLore(Arrays.asList(ChatColor.AQUA + "Buy respawns for all teammates."));
		allLives.setItemMeta(m);
		gui.setButton("buyAllLives", 33, allLives);
		
		gui.open(p);
	}
	
	public static void openSpecialGUI(GamePlayer p){

		int b = Coins.getBalance(p);
		ItemStack balance = new ItemStack(Material.EMERALD);
		ItemMeta m = balance.getItemMeta();
		m.setDisplayName(ChatColor.GREEN.toString() + b + " Coins");
		balance.setItemMeta(m);
		ItemStack back = new ItemStack(Material.BARRIER);
		m.setDisplayName(ChatColor.GREEN + "<- Back");
		back.setItemMeta(m);
		
		InventoryGUI gui = new InventoryGUI("Shop - Special");
		
		gui.setButton("balance", 13, balance);
		gui.setButton("back", 0, back);
		
		int c = ShopItems.getCost("tnt");
		ItemStack tnt = new ItemStack(Material.TNT);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		tnt.setItemMeta(m);
		gui.setButton("tnt", 29, tnt);
		
		c = ShopItems.getCost("enderpearl");
		ItemStack epearl = new ItemStack(Material.ENDER_PEARL);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		epearl.setItemMeta(m);
		gui.setButton("enderpearl", 30, epearl);
		
		c = ShopItems.getCost("poison");
		ItemStack poison = new ItemStack(Material.SPLASH_POTION);
		PotionMeta pm = (PotionMeta) poison.getItemMeta();
		pm.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		pm.setBasePotionData(new PotionData(PotionType.POISON, false, true));
		poison.setItemMeta(pm);
		gui.setButton("poison", 31, poison);
		
		c = ShopItems.getCost("jump");
		ItemStack jumpBoost = new ItemStack(Material.POTION);
		pm = (PotionMeta) jumpBoost.getItemMeta();
		pm.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		pm.setBasePotionData(new PotionData(PotionType.JUMP, false, true));
		jumpBoost.setItemMeta(pm);
		gui.setButton("jump", 32, jumpBoost);
		
		c = ShopItems.getCost("goldapple");
		ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		gapple.setItemMeta(m);
		gui.setButton("goldapple", 33, gapple);
		
		c = ShopItems.getCost("respawn");
		ItemStack respawn = new ItemStack(Material.TOTEM);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		m.setLore(Arrays.asList(ChatColor.AQUA + "Get an additional respawn."));
		respawn.setItemMeta(m);
		gui.setButton("respawn", 40, respawn);
		
		gui.open(p);
	}
	
	public static void openArmorGUI(GamePlayer p){

		int b = Coins.getBalance(p);
		ItemStack balance = new ItemStack(Material.EMERALD);
		ItemMeta m = balance.getItemMeta();
		m.setDisplayName(ChatColor.GREEN.toString() + b + " Coins");
		balance.setItemMeta(m);
		ItemStack back = new ItemStack(Material.BARRIER);
		m.setDisplayName(ChatColor.GREEN + "<- Back");
		back.setItemMeta(m);
		
		InventoryGUI gui = new InventoryGUI("Shop - Armor");
		
		gui.setButton("balance", 13, balance);
		gui.setButton("back", 0, back);
		
		
		int c = ShopItems.getCost("chainArmor");
		ItemStack chain = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		chain.setItemMeta(m);
		gui.setButton("chainArmor", 29, chain);
		
		c = ShopItems.getCost("ironArmor");
		ItemStack iron = new ItemStack(Material.IRON_CHESTPLATE);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		iron.setItemMeta(m);
		gui.setButton("ironArmor", 30, iron);
		
		c = ShopItems.getCost("diamondArmor");
		ItemStack diamond = new ItemStack(Material.DIAMOND_CHESTPLATE);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		diamond.setItemMeta(m);
		gui.setButton("diamondArmor", 31, diamond);
		
		
		c = ShopItems.getCost("thorns");
		ItemStack thorns = new ItemStack(Material.IRON_CHESTPLATE);
		m.setDisplayName((b >= c ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + c + " coins");
		m.addEnchant(Enchantment.THORNS, 2, false);
		thorns.setItemMeta(m);
		gui.setButton("thorns", 32, thorns);
		
		
		gui.open(p);
	}
	
	public static void openWeaponGUI(GamePlayer p){
		
		int b = Coins.getBalance(p);
		ItemStack balance = new ItemStack(Material.EMERALD);
		ItemMeta m = balance.getItemMeta();
		m.setDisplayName(ChatColor.GREEN.toString() + b + " Coins");
		balance.setItemMeta(m);
		ItemStack back = new ItemStack(Material.BARRIER);
		m.setDisplayName(ChatColor.GREEN + "<- Back");
		back.setItemMeta(m);
		
		InventoryGUI gui = new InventoryGUI("Shop - Weapons");
		
		ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
		m.setDisplayName((b >= ShopItems.getCost("stoneSword") ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ShopItems.getCost("stoneSword") + " coins");
		stoneSword.setItemMeta(m);
		
		ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
		m.setDisplayName((b >= ShopItems.getCost("ironSword") ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ShopItems.getCost("ironSword") + " coins");
		ironSword.setItemMeta(m);
		
		ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
		m.setDisplayName((b >= ShopItems.getCost("diamondSword") ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ShopItems.getCost("diamondSword") + " coins");
		diamondSword.setItemMeta(m);
		
		ItemStack diamondSword1 = new ItemStack(Material.DIAMOND_SWORD);
		m.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
		m.setDisplayName((b >= ShopItems.getCost("diamondSword1") ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ShopItems.getCost("diamondSword1") + " coins");
		diamondSword1.setItemMeta(m);
		m = balance.getItemMeta();
		
		ItemStack diamondSword2 = new ItemStack(Material.DIAMOND_SWORD);
		m.addEnchant(Enchantment.DAMAGE_ALL, 2, false);
		m.setDisplayName((b >= ShopItems.getCost("diamondSword2") ? ChatColor.GREEN : ChatColor.RED) + "Buy for " + ShopItems.getCost("diamondSword2") + " coins");
		diamondSword2.setItemMeta(m);
		
		gui.setButton("stoneSword", 29, stoneSword);
		gui.setButton("ironSword", 30, ironSword);
		gui.setButton("diamondSword", 31, diamondSword);
		gui.setButton("diamondSword1", 32, diamondSword1);
		gui.setButton("diamondSword2", 33, diamondSword2);
		gui.setButton("balance", 13, balance);
		gui.setButton("back", 0, back);
		
		gui.open(p);
	}
	
	public static void openShopGUI(GamePlayer p){
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
