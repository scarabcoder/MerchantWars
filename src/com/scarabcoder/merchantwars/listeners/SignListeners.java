package com.scarabcoder.merchantwars.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.scarabcoder.gameapi.enums.GamePlayerType;
import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;

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
				if(p.getGame().getGamePlayerType(p).equals(GamePlayerType.PLAYER) && s.getLine(0).contains("Shop") && p.getGame().getGameStatus().equals(GameStatus.INGAME)){
					GUIListener.openShopGUI(p);
				}
			}
		}
			
	}
	
}
