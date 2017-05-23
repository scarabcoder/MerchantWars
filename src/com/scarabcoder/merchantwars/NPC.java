package com.scarabcoder.merchantwars;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import com.scarabcoder.gameapi.game.Area;
import com.scarabcoder.gameapi.game.Team;

public class NPC {
	
	private Villager villager;
	private Team team;
	private String name;
	private Area area;
	private Area lastArea;
	
	public NPC(Location loc, Team team, String name, Area area){
		Villager v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		v.setCustomNameVisible(true);
		v.setCustomName(name);
		this.villager = v;
		this.name = name;
		this.team = team;
		this.setArea(area);
	}
	
	public Area getLastArea(){
		return lastArea;
	}
	
	public Area getArea(){
		return area;
	}
	
	public void setArea(Area area){
		this.area = area;
		if(area != null){
			this.lastArea = area;
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public Villager getEntity(){
		return this.villager;
	}
	
	public Team getTeam(){
		return this.team;
	}
	
	
	public void kill(){
		EntityManager.destoreNPC(this);
		this.villager.setHealth(0);
	}
	
}
