package com.scarabcoder.merchantwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.scarabcoder.gameapi.event.PlayerJoinGameEvent;
import com.scarabcoder.gameapi.game.Team;
import com.scarabcoder.merchantwars.MerchantWars;
import com.scarabcoder.merchantwars.shop.Coins;
import com.scarabcoder.merchantwars.shop.Respawns;
import com.scarabcoder.merchantwars.shop.ShopItems;

import net.md_5.bungee.api.ChatColor;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoinGame(final PlayerJoinGameEvent e){
		Team team = e.getGame().addToTeam(e.getPlayer());
		if(team != null){
			e.getGame().sendMessage(team.getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.GREEN + " joined the " + team.getName() + " team!");
			Coins.setBalance(e.getPlayer(), 15);
			Respawns.setRespawns(e.getPlayer(), 3);
			
			ShopItems.setUnlocked(e.getPlayer(), "chain", false);
			ShopItems.setUnlocked(e.getPlayer(), "iron", false);
			ShopItems.setUnlocked(e.getPlayer(), "iron", false);
			ShopItems.setUnlocked(e.getPlayer(), "diamond", false);
			
		}else{
			e.getPlayer().getOnlinePlayer().sendMessage(ChatColor.RED + "The game is full, sending you back to hub...");
			Bukkit.getScheduler().scheduleSyncDelayedTask(MerchantWars.getPlugin(), new Runnable(){

				@Override
				public void run() {
					e.getGame().removePlayer(e.getPlayer());
					
				}
				
			}, 20);
		}
	}
	
}
