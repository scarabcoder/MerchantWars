package com.scarabcoder.merchantwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.scarabcoder.gameapi.enums.GamePlayerType;
import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.event.PlayerLeaveGameEvent;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.merchantwars.MerchantWars;
import com.scarabcoder.merchantwars.shop.Respawns;

import net.md_5.bungee.api.ChatColor;

public class PlayerLeaveListener implements Listener {
	
	@EventHandler
	public void onPlayerLeaveGame(PlayerLeaveGameEvent e){
		if(e.getGame().getGameStatus().equals(GameStatus.INGAME)){
			if(e.getPlayer().getTeam() != null){
				e.getGame().sendMessage(e.getPlayer().getTeam().getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.RESET + " has been eliminated!");
				int s = 0;
				for(GamePlayer p : e.getPlayer().getTeam().getPlayers()){
					if(e.getGame().getGamePlayerType(p).equals(GamePlayerType.PLAYER)){
						s++;
					}
				}
				
				if(s == 0){
					e.getGame().sendMessage(ChatColor.BOLD.toString() + ChatColor.RED + "Team " + e.getPlayer().getTeam().getName() + " has been wiped out!");
				}
				Respawns.setRespawns(e.getPlayer(), 0);
			}
			MerchantWars.checkForGameEnd(e.getGame());
		}else{
			
		}
	}
	
}
