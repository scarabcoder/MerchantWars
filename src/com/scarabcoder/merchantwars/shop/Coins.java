package com.scarabcoder.merchantwars.shop;

import java.util.HashMap;

import com.scarabcoder.gameapi.game.GamePlayer;

public class Coins {
	
	private static HashMap<GamePlayer, Integer> balance = new HashMap<GamePlayer, Integer>();
	
	public static void setBalance(GamePlayer player, int bal){
		balance.put(player, bal);
	}
	
	public static void addBalance(GamePlayer player, int bal){
		balance.put(player, balance.get(player) + bal);
	}
	
	
	public static void removeBalance(GamePlayer player, int bal){
		addBalance(player, -1 * bal);
		
	}
	
	public static int getBalance(GamePlayer player){
		return balance.get(player);
	}
	
}
