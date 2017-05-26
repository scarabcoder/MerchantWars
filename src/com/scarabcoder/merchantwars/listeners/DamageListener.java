package com.scarabcoder.merchantwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffectType;

import com.scarabcoder.gameapi.enums.GamePlayerType;
import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;
import com.scarabcoder.gameapi.util.LocationUtil;
import com.scarabcoder.gameapi.util.ScarabUtil;
import com.scarabcoder.merchantwars.EntityManager;
import com.scarabcoder.merchantwars.MerchantWars;
import com.scarabcoder.merchantwars.NPC;
import com.scarabcoder.merchantwars.shop.Coins;
import com.scarabcoder.merchantwars.shop.Respawns;

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
					if(damager.getGame().getGamePlayerType(damager).equals(GamePlayerType.PLAYER) && !LocationUtil.isInArea(npc.getEntity().getLocation(), npc.getLastArea().getLocation1(), npc.getLastArea().getLocation2())){
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
				if(damager.getGame().getGameStatus().equals(GameStatus.INGAME)){
					final GamePlayer damaged = PlayerManager.getGamePlayer((Player)e.getEntity());
					if(!e.isCancelled()){
						if(damaged.getOnlinePlayer().getHealth() - e.getFinalDamage() > 0){
							damager.getOnlinePlayer().sendMessage(damaged.getTeam().getChatColor() + damaged.getOnlinePlayer().getName() + ChatColor.RESET + " now has " + Math.ceil((damaged.getOnlinePlayer().getHealth() - e.getFinalDamage())) + " HP");
						}else{
							damager.getGame().sendMessage(damager.getTeam().getChatColor() + damager.getPlayer().getName() + ChatColor.RESET + " killed " + damaged.getTeam().getChatColor() + damaged.getPlayer().getName());
							damager.getOnlinePlayer().sendMessage(ChatColor.GREEN + "+8 Coins (Killed " + damaged.getTeam().getChatColor() + damaged.getPlayer().getName() + ChatColor.GREEN + ")");
							Coins.addBalance(damager, 8);
						}
					}
				}else{
					e.setCancelled(true);
				}
			}
		}
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		final GamePlayer p = PlayerManager.getGamePlayer(e.getEntity());
		
		if(Respawns.getRespawns(p) != 0){
			p.getOnlinePlayer().sendMessage(ChatColor.RED + "-1 Respawn");
			Respawns.setRespawns(p, Respawns.getRespawns(p) - 1);
			p.getOnlinePlayer().getInventory().clear();
			MerchantWars.giveDefaultInventory(p);
		}
		
		if(Respawns.getRespawns(p) <= 0){
			p.getGame().sendMessage(p.getTeam().getChatColor() + p.getPlayer().getName() + ChatColor.RESET + " has been eliminated.");
			p.getGame().setPlayerMode(GamePlayerType.SPECTATOR, p);
			MerchantWars.checkForGameEnd(p.getGame());
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(MerchantWars.getPlugin(), new Runnable(){

			@Override
			public void run() {
				e.getEntity().spigot().respawn();
			}
			
		}, 5);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		
		final GamePlayer p = PlayerManager.getGamePlayer(e.getPlayer());
		if(Respawns.getRespawns(p) <= 0 && p.getGame().getGamePlayerType(p).equals(GamePlayerType.SPECTATOR)){
			e.setRespawnLocation(p.getGame().getArena().getSpectatorSpawn());
			Bukkit.getScheduler().scheduleSyncDelayedTask(MerchantWars.getPlugin(), new Runnable(){

				@Override
				public void run() {
					p.getOnlinePlayer().setGameMode(GameMode.SPECTATOR);
				}
				
			}, 5);
		}else{
			e.setRespawnLocation(p.getTeam().getTeamSpawns().get(0));
			p.getOnlinePlayer().getInventory().clear();
			MerchantWars.giveDefaultInventory(p);
			
		}
	}
	
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			GamePlayer p = PlayerManager.getGamePlayer((Player) e.getEntity());
			if(p.getGame().getGameStatus().equals(GameStatus.INGAME)){
				if(p.isInGame()){
					if(p.getOnlinePlayer().getPassengers() != null){
						if(p.getOnlinePlayer().getPassengers().size() != 0){
							if(p.getOnlinePlayer().getPassengers().get(0) instanceof Villager){
								Villager v = (Villager) p.getOnlinePlayer().getPassengers().get(0);
								if(EntityManager.isNPC(v)){
									p.getOnlinePlayer().removePassenger(v);
									p.getTeam().sendMessage(p.getTeam().getChatColor() + p.getPlayer().getName() + ChatColor.RESET + " dropped up the merchant.");
									p.getOnlinePlayer().removePotionEffect(PotionEffectType.SLOW);
								}
							}
						}
					}
				}
			}else{
				e.setCancelled(true);
			}
		}
	}
	
}
