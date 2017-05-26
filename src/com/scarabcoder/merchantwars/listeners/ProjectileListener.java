package com.scarabcoder.merchantwars.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.manager.PlayerManager;
import com.scarabcoder.merchantwars.EntityManager;

import net.md_5.bungee.api.ChatColor;

public class ProjectileListener implements Listener {
	
	@EventHandler
	public void onEnderpearl(ProjectileLaunchEvent e){
		if(e.getEntityType().equals(EntityType.ENDER_PEARL)){
			if(e.getEntity().getShooter() != null){
				if(e.getEntity().getShooter() instanceof Player){
					GamePlayer p = PlayerManager.getGamePlayer((Player)e.getEntity().getShooter());
					if(EntityManager.isCarryingNPC(p)){
						e.setCancelled(true);
						p.getOnlinePlayer().sendMessage(ChatColor.RED + "You can't throw enderpearls while carrying a merchant.");
					}
				}
			}
		}
	}
	
}
