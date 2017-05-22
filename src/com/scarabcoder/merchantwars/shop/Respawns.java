package com.scarabcoder.merchantwars.shop;

import java.util.HashMap;

import com.scarabcoder.gameapi.game.GamePlayer;

public class Respawns {
	
	private static HashMap<GamePlayer, Integer> respawns = new HashMap<GamePlayer, Integer>();
	
	public static void setRespawns(GamePlayer p, int amount){
		respawns.put(p, amount);
	}
	
	public static int getRespawns(GamePlayer p){
		return respawns.get(p);
	}
	
}
