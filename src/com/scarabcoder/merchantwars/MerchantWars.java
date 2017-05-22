package com.scarabcoder.merchantwars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.enums.TeamSpreadType;
import com.scarabcoder.gameapi.game.Arena;
import com.scarabcoder.gameapi.game.ArenaSettings;
import com.scarabcoder.gameapi.game.Game;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.game.GameSettings;
import com.scarabcoder.gameapi.game.Team;
import com.scarabcoder.gameapi.manager.TeamManager;
import com.scarabcoder.gameapi.util.ScoreboardUtil;
import com.scarabcoder.merchantwars.shop.Respawns;

import net.md_5.bungee.api.ChatColor;

public class MerchantWars extends JavaPlugin {
	
	private static Plugin plugin;
	
	public static boolean pregame = false;
	
	@Override
	public void onEnable(){
		
		plugin = this;
		
		//Game initiation\\
		Arena arena = new Arena("MerchantWars");
		
		Game game = new Game("MerchantWars", arena, GameStatus.WAITING, this);
		
		//Setting & Manager Variables\\
		GameSettings gameSettings = game.getGameSettings();
		ArenaSettings arenaSettings = arena.getArenaSettings();
		TeamManager teamManager = game.getTeamManager();
		
		//Teams\\
		Team red = new Team(DyeColor.RED, "Red");
		Team blue = new Team(DyeColor.BLUE, "Blue");
		Team green = new Team(DyeColor.GREEN, "Green");
		Team yellow = new Team(DyeColor.YELLOW, "Yellow");
		teamManager.registerTeams(red, blue, green, yellow);
		
		//Set GameSettings\\
		gameSettings.shouldUseTeams(true);
		gameSettings.setTeamSpreadType(TeamSpreadType.EVEN);
		gameSettings.setMinimumTeamSize(2);
		gameSettings.setAutomaticTeamSizeCompensationEnabled(true);
		gameSettings.setMOTD(true);
		gameSettings.shouldSetListPlayerCount(true);
		gameSettings.setAutomaticCountdown(true);
		gameSettings.setMinimumTeamSize(2);
		gameSettings.setMinimumPlayers(2 * 4);
		gameSettings.setMaximumPlayers(4 * 4);
		gameSettings.setMaximumTeamSize(4);
		gameSettings.setAutomaticCountdown(true);
		gameSettings.setCountdownTime(20);
		
		//Set Arena Settings\\
		arenaSettings.setAllowChestAccess(false);
		arenaSettings.setCanDestroy(false);
		arenaSettings.setCanBuild(false);
		arenaSettings.setAllowFoodLevelChange(false);
		arenaSettings.setAllowItemDrop(false);
		
		
		
		//Game loop runnable\\
		game.setLoopRunnable(new Runnable(){

			@Override
			public void run() {
				switch(game.getGameStatus()){
				case INGAME:
					for(GamePlayer player : game.getPlayers()){
						if(player.isOnline()){
							List<String> board = new ArrayList<String>();
							
							for(Team team : game.getTeamManager().getTeams()){
								if(team.getPlayers().size() > 0){
									board.add(team.getChatColor() + team.getName());
									for(GamePlayer p : team.getPlayers()){
										board.add("   " + p.getPlayer().getName() + ": " + Respawns.getRespawns(p));
									}
								}else{
									board.add(team.getChatColor() + team.getName() + "   " + ChatColor.RESET.toString() + ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "X");
								}
							}
							
							Scoreboard s = ScoreboardUtil.createScoreboard("=== " + ChatColor.AQUA + "Merchant Wars" + ChatColor.WHITE + " ===", board);
								player.getOnlinePlayer().setScoreboard(s);
						}
					}
					break;
				case RESTARTING:
					break;
				case WAITING:
					break;
				default:
					break;
				
				}
			}
			
		});
		
	}
	
	public static Plugin getPlugin(){
		return plugin;
	}
	
}
