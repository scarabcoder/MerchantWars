package com.scarabcoder.merchantwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.scarabcoder.gameapi.event.GameEndEvent;
import com.scarabcoder.merchantwars.EntityManager;
import com.scarabcoder.merchantwars.NPC;

public class GameEndListener implements Listener {
	
	@EventHandler
	public void onGameEnd(GameEndEvent e){
		for(NPC n : EntityManager.getNPCs()){
			n.kill();
		}
	}

}
