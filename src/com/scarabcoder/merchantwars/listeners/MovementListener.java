package com.scarabcoder.merchantwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;
import com.scarabcoder.merchantwars.MerchantWars;

public class MovementListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		GamePlayer player = PlayerManager.getGamePlayer(e.getPlayer());
		if(player.isInGame() && MerchantWars.pregame){
			if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()){
				e.setCancelled(true);
			}
		}
	}
	
}
