package com.scarabcoder.merchantwars;

import java.util.HashMap;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Villager;

public class EntityManager {
	
	private static HashMap<String, NPC> npcs = new HashMap<String, NPC>();
	
	public static boolean isNPC(Villager villager){
		return getNPC(villager) != null;
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
	
	public static NPC getNPC(String name){
		return npcs.get(name);
	}
	
	public static void destoreNPC(NPC npc){
		npcs.remove(npc.getName());
	}
	
	
	
	
}
