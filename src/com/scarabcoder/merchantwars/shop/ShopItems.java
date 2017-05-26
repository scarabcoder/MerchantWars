package com.scarabcoder.merchantwars.shop;

import java.util.HashMap;

import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.game.Team;

public class ShopItems {
	
	private static HashMap<Team, HashMap<String, Integer>> unlocked = new HashMap<Team, HashMap<String, Integer>>();
	private static HashMap<GamePlayer, HashMap<String, Integer>> playerUnlocked = new HashMap<GamePlayer, HashMap<String, Integer>>();
	private static HashMap<String, Integer> costs = new HashMap<String, Integer>();
	
	public static void registerItem(String item, int cost){
		costs.put(item, cost);
	}
	
	public static int getCost(String item){
		return costs.get(item);
	}
	
	public static void setLevel(Team team, String item, int level){
		HashMap<String, Integer> unlock = ShopItems.unlocked.get(team);
		if(unlock == null){
			ShopItems.unlocked.put(team, new HashMap<String, Integer>());
			unlock = ShopItems.unlocked.get(team);
		}
		unlock.put(item, level);
		ShopItems.unlocked.put(team, unlock);
	}
	
	public static void setLevel(GamePlayer p, String item, int level){
		HashMap<String, Integer> unlock = ShopItems.playerUnlocked.get(p);
		if(unlock == null){
			ShopItems.playerUnlocked.put(p, new HashMap<String, Integer>());
			unlock = ShopItems.playerUnlocked.get(p);
		}
		unlock.put(item, level);
		ShopItems.playerUnlocked.put(p, unlock);
	}
	
	public static Integer getLevel(GamePlayer p, String item){
		HashMap<String, Integer> isUnlocked = ShopItems.playerUnlocked.get(p);
		if(isUnlocked == null){
			ShopItems.playerUnlocked.put(p, new HashMap<String, Integer>());
			isUnlocked = ShopItems.playerUnlocked.get(p);
		}
		return isUnlocked.get(item);
	}
	
	public static Integer getLevel(Team team, String item){
		HashMap<String, Integer> isUnlocked = ShopItems.unlocked.get(team);
		if(isUnlocked == null){
			ShopItems.unlocked.put(team, new HashMap<String, Integer>());
			isUnlocked = ShopItems.unlocked.get(team);
		}
		return isUnlocked.get(item);
	}
	
}
