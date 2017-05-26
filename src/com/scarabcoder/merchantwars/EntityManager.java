package com.scarabcoder.merchantwars;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;

import com.scarabcoder.gameapi.game.GamePlayer;

public class EntityManager {
	
	private static HashMap<String, NPC> npcs = new HashMap<String, NPC>();
	
	public static boolean isNPC(Entity villager){
		if(villager instanceof Villager){
			Villager v = (Villager) villager;
			return getNPC(v) != null;
			
		}else{
			return false;
		}
	}
	
	public static void storeNPCs(NPC...npcs){
		for(NPC npc : npcs){
			storeNPC(npc);
		}
	}
	
	public static boolean isCarryingNPC(GamePlayer player){
		if(player.isOnline() && player.getOnlinePlayer().getPassengers() != null){
			if(player.getOnlinePlayer().getPassengers().size() > 0){
				if(player.getOnlinePlayer().getPassengers().get(0) != null){
					return isNPC(player.getOnlinePlayer().getPassengers().get(0));
				}
			}
		}
		return false;
	}
	
	public static NPC getCarryingNPC(GamePlayer player){
		if(isCarryingNPC(player)){
			return getNPC((Villager)player.getOnlinePlayer().getPassengers().get(0));
		}
		return null;
	}
	
	
	
	public static void storeNPC(NPC npc){
		npcs.put(npc.getName(), npc);
		npc.getEntity().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0d);
		npc.getEntity().setCollidable(false);
	}
	
	public static NPC getNPC(Villager villager){
		for(String key : npcs.keySet()){
			if(npcs.get(key).getEntity().getUniqueId().equals(villager.getUniqueId())){
				return npcs.get(key);
			}
		}
		return null;
	}
	
	public static NPC getNPC(Entity e){
		if(e instanceof Villager){
			return getNPC((Villager)e);
		}
		return null;
	}
	
	
	public static NPC getNPC(String name){
		return npcs.get(name);
	}
	
	public static void destoreNPC(NPC npc){
		npcs.remove(npc.getName());
	}

	public static ArrayList<NPC> getNPCs() {
		ArrayList<NPC> npcList = new ArrayList<NPC>();
		
		for(String n : npcs.keySet()){
			npcList.add(npcs.get(n));
		}
		
		return npcList;
	}
	
	
	
	
}
