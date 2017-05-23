package com.scarabcoder.merchantwars.listeners;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;
import com.scarabcoder.merchantwars.EntityManager;
import com.scarabcoder.merchantwars.NPC;

public class PlayerInteractListener implements Listener {
	
	
	@EventHandler
	public void onPlayerInteractNPC(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Villager){
			Villager v = (Villager) e.getRightClicked();
			if(EntityManager.isNPC(v)){
				NPC npc = EntityManager.getNPC(v);
				GamePlayer p = PlayerManager.getGamePlayer(e.getPlayer());
				if(p.isInGame()){
					if(p.getTeam() != null){
						if(p.getTeam().getName().equals(npc.getTeam().getName())){
							e.setCancelled(true);
							p.getOnlinePlayer().addPassenger(npc.getEntity());
							p.getOnlinePlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
						}
					}
				}
			}
		}
	}
	
	
}
