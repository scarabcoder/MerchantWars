package com.scarabcoder.merchantwars.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.event.GameStartEvent;
import com.scarabcoder.gameapi.game.Game;
import com.scarabcoder.merchantwars.MerchantWars;

import net.md_5.bungee.api.ChatColor;

public class GameStartListener implements Listener {
	
	@EventHandler
	public void onGameStart(GameStartEvent e){
		Game game = e.getGame();
		List<String> msgs = Arrays.asList(
				ChatColor.RED + "======== " + ChatColor.RESET.toString() + ChatColor.AQUA + ChatColor.BOLD.toString() + "Merchant Wars" + ChatColor.RESET + ChatColor.RED + " ========",
				ChatColor.GREEN + "Carry your trading merchant back and forth across the map to earn coins. Upgrade your gear and buy more respawns at merchant stalls on both sides of the map. Stop enemy teams from getting their merchant across!",
				ChatColor.GRAY + "Game starts in 5 seconds"
				);
		for(String msg : msgs){
			game.sendMessage(msg, false);
		}
		game.getArena().getArenaSettings().allowChat(false);
		MerchantWars.pregame = true;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MerchantWars.getPlugin(), new Runnable(){

			@Override
			public void run() {
				MerchantWars.pregame = false;
				if(game.getGameStatus().equals(GameStatus.INGAME)){
					game.getArena().getArenaSettings().allowChat(true);
					game.sendMessage("Chat is now enabled.");
				}
			}
			
		}, 5 * 20);
	}

}