package com.scarabcoder.merchantwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import com.scarabcoder.gameapi.enums.GamePlayerType;
import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.event.PlayerJoinGameEvent;
import com.scarabcoder.gameapi.game.Team;
import com.scarabcoder.merchantwars.MerchantWars;
import com.scarabcoder.merchantwars.shop.Coins;
import com.scarabcoder.merchantwars.shop.Respawns;

import net.md_5.bungee.api.ChatColor;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoinGame(final PlayerJoinGameEvent e){
		e.getPlayer().getOnlinePlayer().removePotionEffect(PotionEffectType.SLOW);
		e.getPlayer().getOnlinePlayer().removePotionEffect(PotionEffectType.REGENERATION);
		if(!e.getGame().getGameStatus().equals(GameStatus.INGAME)){
			Team team = e.getGame().addToTeam(e.getPlayer());
			if(team != null){
				e.getGame().sendMessage(team.getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.GREEN + " joined the game " + 
			ChatColor.DARK_GREEN + "(" + e.getGame().getPlayers().size() + "/" + e.getGame().getGameSettings().getMaximumPlayers() + ")");
				Coins.setBalance(e.getPlayer(), 15);
				Respawns.setRespawns(e.getPlayer(), 3);
				e.getPlayer().getOnlinePlayer().getInventory().clear();
				
			}else{
				e.getPlayer().getOnlinePlayer().sendMessage(ChatColor.RED + "The game is full, sending you back to hub...");
				Bukkit.getScheduler().scheduleSyncDelayedTask(MerchantWars.getPlugin(), new Runnable(){
	
					@Override
					public void run() {
						e.getGame().removePlayer(e.getPlayer());
						
					}
					
				}, 20);
			}
		}else{
			e.setGamePlayerType(GamePlayerType.SPECTATOR);
			e.getPlayer().getOnlinePlayer().teleport(e.getGame().getArena().getSpectatorSpawn());
			e.getGame().sendMessage(ChatColor.GREEN + e.getPlayer().getPlayer().getName() + " joined the game (spectating).");
		}
	}
	
}
