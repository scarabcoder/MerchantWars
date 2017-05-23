package com.scarabcoder.merchantwars.shop;

import java.util.HashMap;

import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.game.Team;

public class ShopItems {
	
	private static HashMap<Team, HashMap<String, Boolean>> unlocked = new HashMap<Team, HashMap<String, Boolean>>();
	private static HashMap<GamePlayer, HashMap<String, Boolean>> playerUnlocked = new HashMap<GamePlayer, HashMap<String, Boolean>>();
	
	public static void setUnlocked(Team team, String item, boolean unlocked){
		HashMap<String, Boolean> unlock = ShopItems.unlocked.get(team);
		if(unlock == null){
			ShopItems.unlocked.put(team, new HashMap<String, Boolean>());
			unlock = ShopItems.unlocked.get(team);
		}
		unlock.put(item, unlocked);
		ShopItems.unlocked.put(team, unlock);
	}
	
	public static void setUnlocked(GamePlayer p, String item, boolean unlocked){
		HashMap<String, Boolean> unlock = ShopItems.playerUnlocked.get(p);
		if(unlock == null){
			ShopItems.playerUnlocked.put(p, new HashMap<String, Boolean>());
			unlock = ShopItems.playerUnlocked.get(p);
		}
		unlock.put(item, unlocked);
		ShopItems.playerUnlocked.put(p, unlock);
	}
	
	public static boolean isUnlocked(GamePlayer p, String item){
		HashMap<String, Boolean> isUnlocked = ShopItems.playerUnlocked.get(p);
		if(isUnlocked == null){
			ShopItems.playerUnlocked.put(p, new HashMap<String, Boolean>());
			isUnlocked = ShopItems.playerUnlocked.get(p);
		}
		return isUnlocked.get(item);
	}
	
	public static boolean isUnlocked(Team team, String item){
		HashMap<String, Boolean> isUnlocked = ShopItems.unlocked.get(team);
		if(isUnlocked == null){
			ShopItems.unlocked.put(team, new HashMap<String, Boolean>());
			isUnlocked = ShopItems.unlocked.get(team);
		}
		return isUnlocked.get(item);
	}
	
}
