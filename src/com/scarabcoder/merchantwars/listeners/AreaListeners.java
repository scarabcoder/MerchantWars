package com.scarabcoder.merchantwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.scarabcoder.gameapi.event.PlayerEnterAreaEvent;
import com.scarabcoder.gameapi.event.PlayerLeaveAreaEvent;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.merchantwars.EntityManager;
import com.scarabcoder.merchantwars.NPC;
import com.scarabcoder.merchantwars.shop.Coins;

import net.md_5.bungee.api.ChatColor;

public class AreaListeners implements Listener {
	
	@EventHandler
	public void onPlayerEnterArea(PlayerEnterAreaEvent e){
		if(e.getArea().getName().contains("home")){
			if(e.getPlayer().getOnlinePlayer().getPassengers() != null){
				if(e.getPlayer().getOnlinePlayer().getPassengers().size() != 0){
					if(EntityManager.isNPC(e.getPlayer().getOnlinePlayer().getPassengers().get(0))){
						NPC npc = EntityManager.getNPC(e.getPlayer().getOnlinePlayer().getPassengers().get(0));
						if(!npc.getLastArea().getName().equals(e.getArea().getName())){
							e.getPlayer().getOnlinePlayer().removePassenger(e.getPlayer().getOnlinePlayer().getPassengers().get(0));
							npc.setArea(e.getArea());
							npc.getEntity().teleport(e.getArea().getCenter(false));
							for(GamePlayer p : npc.getTeam().getPlayers()){
								Coins.addBalance(p, 10);
								if(p.isOnline()){
									p.getOnlinePlayer().sendMessage(ChatColor.GREEN + "+10 Coins (Merchant Escorted)");
									if(p.equals(e.getPlayer())){
										Coins.addBalance(p, 5);
										p.getOnlinePlayer().sendMessage(ChatColor.GREEN + "+5 Coins (Escorting Merchant)");
									}
									p.getOnlinePlayer().setBedSpawnLocation(EntityManager.getNPC(p.getTeam().getChatColor() + "Merchant").getLastArea().getCenter());
								}
								
							}
							e.getPlayer().getOnlinePlayer().removePotionEffect(PotionEffectType.SLOW);
							e.getPlayer().getGame().sendMessage(e.getPlayer().getTeam().getChatColor() + e.getPlayer().getPlayer().getName() + ChatColor.RESET + " escorted their merchant across the map.");
						}
					}
				}
			}
			NPC n = EntityManager.getNPC(e.getPlayer().getTeam().getChatColor() + "Merchant");
			if(n.getArea() != null){
				if(n.getArea().equals(e.getArea())){
					e.getPlayer().getOnlinePlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerLeaveArea(PlayerLeaveAreaEvent e){
		NPC n = EntityManager.getNPC(e.getPlayer().getTeam().getChatColor() + "Merchant");
		System.out.println(n == null);
		if(n.getArea() != null){
			if(n.getArea().equals(e.getArea())){
				e.getPlayer().getOnlinePlayer().removePotionEffect(PotionEffectType.REGENERATION);
			}
		}
	}
	
}
