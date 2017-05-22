package com.scarabcoder.merchantwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.scarabcoder.gameapi.event.PlayerJoinGameEvent;
import com.scarabcoder.gameapi.game.Team;
import com.scarabcoder.merchantwars.shop.Coins;
import com.scarabcoder.merchantwars.shop.Respawns;

import net.md_5.bungee.api.ChatColor;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoinGame(PlayerJoinGameEvent e){
		Team team = e.getGame().addToTeam(e.getPlayer());
		if(team != null){
			e.getGame().sendMessage(team.getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.GREEN + " joined the " + team.getName() + " team!");
			Coins.setBalance(e.getPlayer(), 500);
			Respawns.setRespawns(e.getPlayer(), 3);
		}else{
			e.getPlayer().getOnlinePlayer().sendMessage(ChatColor.RED + "The game is full, sending you back to hub...");
			e.getGame().removePlayer(e.getPlayer());
		}
	}
	
}
