package com.scarabcoder.merchantwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import com.scarabcoder.gameapi.enums.GamePlayerType;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;
import com.scarabcoder.merchantwars.EntityManager;
import com.scarabcoder.merchantwars.MerchantWars;
import com.scarabcoder.merchantwars.NPC;
import com.scarabcoder.merchantwars.shop.Coins;
import com.scarabcoder.merchantwars.util.ScarabUtil;

import net.md_5.bungee.api.ChatColor;

public class DamageListener implements Listener {
	
	@EventHandler
	public void entityDamageByEntity(EntityDamageByEntityEvent e){
		if(ScarabUtil.isPlayerDamager(e)){
			GamePlayer damager = ScarabUtil.getDamager(e);
			if(e.getEntity() instanceof Villager){
				Villager villager = (Villager) e.getEntity();
				if(EntityManager.isNPC(villager)){
					NPC npc = EntityManager.getNPC(villager);
					if(damager.getGame().getGamePlayerType(damager).equals(GamePlayerType.PLAYER)){
						if(damager.getTeam().equals(npc.getTeam())){
							e.setCancelled(true);
						}else{
							if(npc.getEntity().getHealth() - e.getFinalDamage() <= 0){
								e.setCancelled(true);
								npc.getEntity().teleport(npc.getLastArea().getCenter(false));
								damager.getGame().sendMessage(damager.getTeam().getChatColor() + damager.getPlayer().getName() + ChatColor.RESET + " returned " + npc.getTeam().getName().toLowerCase() + "'s merchant.");
								damager.getOnlinePlayer().sendMessage(ChatColor.GREEN + "+8 Coins (Returned Merchant)");
								Coins.addBalance(damager, 8);
								npc.getEntity().setHealth(20.0);
							}
						}
					}else{
						e.setCancelled(true);
					}
				}
			}else if(e.getEntity() instanceof Player){
				GamePlayer damaged = PlayerManager.getGamePlayer((Player)e.getEntity());
				if(!e.isCancelled()){
					if(damaged.getOnlinePlayer().getHealth() - e.getFinalDamage() > 0){
						damager.getOnlinePlayer().sendMessage(damaged.getTeam().getChatColor() + damaged.getOnlinePlayer().getName() + " now has " + (damaged.getOnlinePlayer().getHealth() - e.getFinalDamage()) + " HP");
					}else{
						damager.getGame().sendMessage(damager.getTeam().getChatColor() + damager.getPlayer().getName() + ChatColor.RESET + " killed " + damaged.getTeam().getChatColor() + damaged.getPlayer().getName());
						Bukkit.getScheduler().scheduleSyncDelayedTask(MerchantWars.getPlugin(), new Runnable(){

							@Override
							public void run() {
								damaged.getOnlinePlayer().spigot().respawn();
							}
							
						}, 30);
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
