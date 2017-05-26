package com.scarabcoder.merchantwars.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		if(e.getBlock().getType().equals(Material.TNT)){
			e.getBlock().setType(Material.AIR);
			TNTPrimed tnt = (TNTPrimed) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
			tnt.setFuseTicks(60);
		}
	}

}
