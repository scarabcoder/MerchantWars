package com.scarabcoder.merchantwars.listeners;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;
import com.scarabcoder.merchantwars.EntityManager;
import com.scarabcoder.merchantwars.NPC;
import com.scarabcoder.merchantwars.shop.ShopItems;

import net.md_5.bungee.api.ChatColor;

public class PlayerInteractListener implements Listener {
	
	
	@EventHandler
	public void onPlayerInteractNPC(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Villager){
			Villager v = (Villager) e.getRightClicked();
			e.setCancelled(true);
			if(EntityManager.isNPC(v)){
				NPC npc = EntityManager.getNPC(v);
				GamePlayer p = PlayerManager.getGamePlayer(e.getPlayer());
				if(p.isInGame()){
					if(p.getTeam() != null){
						if(p.getTeam().getName().equals(npc.getTeam().getName())){
							if(!EntityManager.isCarryingNPC(p)){
								p.getOnlinePlayer().addPassenger(npc.getEntity());
								int l = ShopItems.getLevel(p.getTeam(), "lighter");
								if(l != 3)
									p.getOnlinePlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2 - l));
								p.getTeam().sendMessage(p.getTeam().getChatColor() + p.getPlayer().getName() + ChatColor.RESET + " picked up the merchant.");
							}
						}
					}
				}
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEntityEvent e){
		GamePlayer p = PlayerManager.getGamePlayer(e.getPlayer());
		if(EntityManager.isCarryingNPC(p)){
			
			p.getOnlinePlayer().removePassenger(p.getOnlinePlayer().getPassengers().get(0));
		}
	}
	
	
}
