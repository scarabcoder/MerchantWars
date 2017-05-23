package com.scarabcoder.merchantwars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.scarabcoder.gameapi.enums.GameStatus;
import com.scarabcoder.gameapi.enums.TeamSpreadType;
import com.scarabcoder.gameapi.game.Area;
import com.scarabcoder.gameapi.game.Arena;
import com.scarabcoder.gameapi.game.ArenaSettings;
import com.scarabcoder.gameapi.game.Game;
import com.scarabcoder.gameapi.game.GamePlayer;
import com.scarabcoder.gameapi.game.GameSettings;
import com.scarabcoder.gameapi.game.Team;
import com.scarabcoder.gameapi.manager.GameManager;
import com.scarabcoder.gameapi.manager.TeamManager;
import com.scarabcoder.gameapi.util.ScoreboardUtil;
import com.scarabcoder.merchantwars.listeners.AreaListeners;
import com.scarabcoder.merchantwars.listeners.DamageListener;
import com.scarabcoder.merchantwars.listeners.GUIListener;
import com.scarabcoder.merchantwars.listeners.GameStartListener;
import com.scarabcoder.merchantwars.listeners.MovementListener;
import com.scarabcoder.merchantwars.listeners.PlayerInteractListener;
import com.scarabcoder.merchantwars.listeners.PlayerJoinListener;
import com.scarabcoder.merchantwars.listeners.SignListeners;
import com.scarabcoder.merchantwars.shop.Coins;
import com.scarabcoder.merchantwars.shop.Respawns;
import com.scarabcoder.merchantwars.shop.ShopItems;

public class MerchantWars extends JavaPlugin {
	
	private static Plugin plugin;
	
	public static boolean pregame = false;
	
	@Override
	public void onEnable(){
		
		plugin = this;
		
		//Game initiation\\
		Arena arena = new Arena("MerchantWars");
		arena.setLobbySpawn(new Location(arena.getWorld(), -1258.5, 42, -335.5));
		arena.setSpectatorSpawn(new Location(arena.getWorld(), -1255, 24, -335));
		
		final Game game = new Game("MerchantWars", arena, GameStatus.WAITING, this);
		game.setMessagePrefix("[" + ChatColor.AQUA + "Merchant Wars" + ChatColor.RESET + "]");
		
		//Setting & Manager Variables\\
		GameSettings gameSettings = game.getGameSettings();
		ArenaSettings arenaSettings = arena.getArenaSettings();
		TeamManager teamManager = game.getTeamManager();
		
		//Teams\\
		Team red = new Team(Color.RED, ChatColor.RED, "Red");
		Team blue = new Team(Color.BLUE, ChatColor.BLUE, "Blue");
		Team green = new Team(Color.GREEN, ChatColor.GREEN, "Green");
		Team yellow = new Team(Color.YELLOW, ChatColor.YELLOW, "Yellow");
		
		red.setAllowTeamDamage(false);
		blue.setAllowTeamDamage(false);
		green.setAllowTeamDamage(false);
		yellow.setAllowTeamDamage(false);
		
		teamManager.registerTeams(red, blue, green, yellow);
		
		//Set GameSettings\\
		gameSettings.shouldUseTeams(true);
		gameSettings.setTeamSpreadType(TeamSpreadType.EVEN);
		gameSettings.setMinimumTeamSize(2);
		gameSettings.setAutomaticTeamSizeCompensationEnabled(true);
		gameSettings.setMOTD(true);
		gameSettings.setUsesBungee(true);
		gameSettings.setLobbyServer("hub");
		gameSettings.shouldSetListPlayerCount(true);
		gameSettings.setAutomaticCountdown(true);
		gameSettings.setMinimumTeamSize(2);
		gameSettings.setMinimumPlayers(1);
		gameSettings.setMaximumPlayers(4 * 4);
		gameSettings.setMaximumTeamSize(4);
		gameSettings.shouldDisableVanillaJoinLeaveMessages(true);
		gameSettings.setAutomaticCountdown(true);
		gameSettings.setCountdownTime(5);
		gameSettings.setSpectatorMode(GameMode.SPECTATOR);
		gameSettings.setDisplayVanillaDeathMessages(false);
		
		
		//Set Arena Settings\\
		arenaSettings.setAllowChestAccess(false);
		arenaSettings.setCanDestroy(false);
		arenaSettings.setCanBuild(false);
		arenaSettings.setAllowFoodLevelChange(false);
		arenaSettings.setAllowItemDrop(false);
		arenaSettings.setKeepInventory(true);
		arenaSettings.setAllowWeatherChange(false);
		arenaSettings.setAllowMobSpawn(false);
		arenaSettings.setAllowInventoryChange(false);
		arenaSettings.setKeepInventory(true);
		
		
		
		//Areas\\
		Area red1 = new Area(new Location(arena.getWorld(), -1263, 4, -354), new Location(arena.getWorld(), -1269, 6, -358), "Redhome1");
		Area red2 = new Area(new Location(arena.getWorld(), -1244, 4, -358), new Location(arena.getWorld(), -1239, 6, -354), "Redhome2");
		
		Area blue1 = new Area(new Location(arena.getWorld(), -1263, 4, -334), new Location(arena.getWorld(), -1269, 6, -338), "Bluehome1");
		Area blue2 = new Area(new Location(arena.getWorld(), -1244, 4, -338), new Location(arena.getWorld(), -1238, 6, -334), "Bluehome2");
		
		Area green1 = new Area(new Location(arena.getWorld(), -1244, 4, -318), new Location(arena.getWorld(), -1238, 6, -314), "Greenhome1");
		Area green2 = new Area(new Location(arena.getWorld(), -1263, 4, -314), new Location(arena.getWorld(), -1269, 6, -318), "Greenhome2");
		
		Area yellow1 = new Area(new Location(arena.getWorld(), -1244, 4, -301), new Location(arena.getWorld(), -1238, 6, -297), "Yellowhome1");
		Area yellow2 = new Area(new Location(arena.getWorld(), -1263, 4, -297), new Location(arena.getWorld(), -1269, 6, -301), "Yellowhome2");
		
		game.registerAreas(red1, red2, blue1, blue2, green1, green2, yellow1, yellow2);
		red.addTeamSpawn(red1.getCenter(false));
		blue.addTeamSpawn(blue1.getCenter(false));
		green.addTeamSpawn(green1.getCenter(false));
		yellow.addTeamSpawn(yellow1.getCenter(false));
		
		//Shop upgrades\\
		for(Team t : game.getTeamManager().getTeams()){
			ShopItems.setUnlocked(t, "defence1", false);
			ShopItems.setUnlocked(t, "defence2", false);
			ShopItems.setUnlocked(t, "defence3", false);
			
			ShopItems.setUnlocked(t, "lighter1", false);
			ShopItems.setUnlocked(t, "lighter2", false);
			ShopItems.setUnlocked(t, "lighter3", false);
			
			ShopItems.setUnlocked(t, "trading1", false);
			ShopItems.setUnlocked(t, "trading2", false);
			ShopItems.setUnlocked(t, "trading3", false);
		}
		
		//Game loop runnable\\
		game.setLoopRunnable(new Runnable(){

			@Override
			public void run() {
				switch(game.getGameStatus()){
				case INGAME:
					for(GamePlayer player : game.getPlayers()){
						if(player.isOnline()){
							List<String> board = new ArrayList<String>();
							board.add("Coins: " + Coins.getBalance(player));
							board.add("Respawns: " + Respawns.getRespawns(player));
							board.add("");	
							for(Team team : game.getTeamManager().getTeams()){
								if(team.getPlayers().size() > 0){
									board.add(team.getChatColor() + team.getName());
									for(GamePlayer p : team.getPlayers()){
										if(Respawns.getRespawns(p) > 0){
											board.add("   " + p.getPlayer().getName() + ": " + Respawns.getRespawns(p));
										}else{
											board.add("   " + p.getPlayer().getName() + ": " + ChatColor.DARK_RED + "✗");
										}
									}
								}else{
									board.add(team.getChatColor() + team.getName() + "   " + ChatColor.RESET.toString() + ChatColor.BOLD + ChatColor.ITALIC + ChatColor.BOLD + ChatColor.DARK_RED + "✗");
								}
							}
							
							Scoreboard s = ScoreboardUtil.createScoreboard(ChatColor.AQUA + "Merchant Wars", board);
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
		
		//Listeners
		Bukkit.getPluginManager().registerEvents(new AreaListeners(), this);
		Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new GameStartListener(), this);
		Bukkit.getPluginManager().registerEvents(new MovementListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
		Bukkit.getPluginManager().registerEvents(new SignListeners(), this);
		
		GameManager.registerGame(game);
		
	}
	
	@Override
	public void onDisable(){
		for(NPC npc : EntityManager.getNPCs()){
			npc.kill();
		}
	}
	
	public static Plugin getPlugin(){
		return plugin;
	}
	
}
