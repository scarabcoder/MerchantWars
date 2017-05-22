package com.scarabcoder.merchantwars;

import org.bukkit.entity.Villager;

import com.scarabcoder.gameapi.game.Team;

public class NPC {
	
	private Villager villager;
	private Team team;
	private String name;
	
	public NPC(Villager villager, Team team, String name){
		this.villager = villager;
		this.name = name;
		this.team = team;
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
