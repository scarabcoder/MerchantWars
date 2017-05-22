package com.scarabcoder.merchantwars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;
import com.scarabcoder.merchantwars.EntityManager;
import com.scarabcoder.merchantwars.NPC;
import com.scarabcoder.merchantwars.util.ScarabUtil;

public class DamageListener {
	
	@EventHandler
	public void entityDamageByEntity(EntityDamageByEntityEvent e){
		if(ScarabUtil.isPlayerDamager(e)){
			if(e.getEntity() instanceof Villager){
				Villager villager = (Villager) e.getEntity();
				if(EntityManager.isNPC(villager)){
					NPC npc = EntityManager.getNPC(villager);
					GamePlayer damager = ScarabUtil.getDamager(e);
					if(damager.getTeam().equals(npc.getTeam())){
						e.setCancelled(true);
					}
				}
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			GamePlayer p = PlayerManager.getGamePlayer((Player) e.getEntity());
			if(p.isInGame()){
				if(p.getOnlinePlayer().getPassengers().get(0) != null){
					if(p.getOnlinePlayer().getPassengers().get(0) instanceof Villager){
						Villager v = (Villager) p.getOnlinePlayer().getPassengers().get(0);
						if(EntityManager.isNPC(v)){
							p.getOnlinePlayer().removePassenger(p.getOnlinePlayer().getPassengers().get(0));
							p.getOnlinePlayer().removePotionEffect(PotionEffectType.SLOW);
						}
					}
				}
			}
		}
	}
	
}
