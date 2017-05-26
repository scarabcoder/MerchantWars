package com.scarabcoder.merchantwars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.scarabcoder.gameapi.enums.GamePlayerType;
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
import com.scarabcoder.merchantwars.listeners.BlockPlaceListener;
import com.scarabcoder.merchantwars.listeners.DamageListener;
import com.scarabcoder.merchantwars.listeners.GUIListener;
import com.scarabcoder.merchantwars.listeners.GameEndListener;
import com.scarabcoder.merchantwars.listeners.GameStartListener;
import com.scarabcoder.merchantwars.listeners.MovementListener;
import com.scarabcoder.merchantwars.listeners.PlayerInteractListener;
import com.scarabcoder.merchantwars.listeners.PlayerJoinListener;
import com.scarabcoder.merchantwars.listeners.PlayerLeaveListener;
import com.scarabcoder.merchantwars.listeners.ProjectileListener;
import com.scarabcoder.merchantwars.listeners.SignListeners;
import com.scarabcoder.merchantwars.shop.Coins;
import com.scarabcoder.merchantwars.shop.Respawns;
import com.scarabcoder.merchantwars.shop.ShopItems;

public class MerchantWars extends JavaPlugin {
	
	private static Plugin plugin;
	
	private static boolean ending = false;
	
	public static boolean pregame = false;
	
	@Override
	public void onEnable(){
		
		plugin = this;
		
		//Game initiation\\
		Arena arena = new Arena("MerchantWars");
		arena.setLobbySpawn(new Location(arena.getWorld(), 97, 66, -17));
		arena.setSpectatorSpawn(new Location(arena.getWorld(), 97, 66, -17));
		
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
		gameSettings.setMinimumTeamSize(1);
		gameSettings.setMinimumPlayers(4);
		gameSettings.setMaximumPlayers(4 * 4);
		gameSettings.setMaximumTeamSize(4);
		gameSettings.shouldDisableVanillaJoinLeaveMessages(true);
		gameSettings.setAutomaticCountdown(true);
		gameSettings.setCountdownTime(15);
		gameSettings.setSpectatorMode(GameMode.SPECTATOR);
		gameSettings.setDisableVanillaDeathMessages(true);
		gameSettings.shouldLeavePlayerOnDisconnect(true);
		gameSettings.setResetWorlds(false);
		
		
		//Set Arena Settings\\
		arenaSettings.setAllowChestAccess(false);
		arenaSettings.setCanDestroy(false);
		//arenaSettings.setCanBuild(false);
		arenaSettings.setAllowFoodLevelChange(false);
		arenaSettings.setAllowItemDrop(false);
		arenaSettings.setKeepInventory(true);
		arenaSettings.setAllowWeatherChange(false);
		arenaSettings.setAllowMobSpawn(false);
		arenaSettings.setAllowInventoryChange(false);
		arenaSettings.setKeepInventory(true);
		arenaSettings.setAllowTNTExplosion(false);
		
		
		//Areas\\
		Area red1 = new Area(new Location(arena.getWorld(), 67, 66, 25), new Location(arena.getWorld(), 55, 75, 13), "Redhome1");
		Area red2 = new Area(new Location(arena.getWorld(), 127, 66, -59), new Location(arena.getWorld(), 139, 75, -47), "Redhome2");
		
		Area blue1 = new Area(new Location(arena.getWorld(), 103, 66, -47), new Location(arena.getWorld(), 91, 75, -59), "Bluehome1");
		Area blue2 = new Area(new Location(arena.getWorld(), 91, 66, 13), new Location(arena.getWorld(), 103, 75, 25), "Bluehome2");
		
		Area green1 = new Area(new Location(arena.getWorld(), 127, 66, -11), new Location(arena.getWorld(), 139, 75, -23), "Greenhome1");
		Area green2 = new Area(new Location(arena.getWorld(), 67, 66, -23), new Location(arena.getWorld(), 55, 75, -11), "Greenhome2");
		
		Area yellow1 = new Area(new Location(arena.getWorld(), 55, 66, -59), new Location(arena.getWorld(), 67, 75, -47), "Yellowhome1");
		Area yellow2 = new Area(new Location(arena.getWorld(), 127, 66, 13), new Location(arena.getWorld(), 139, 75, 25), "Yellowhome2");
		
		game.registerAreas(red1, red2, blue1, blue2, green1, green2, yellow1, yellow2);
		red.addTeamSpawn(red1.getCenter(false));
		blue.addTeamSpawn(blue1.getCenter(false));
		green.addTeamSpawn(green1.getCenter(false));
		yellow.addTeamSpawn(yellow1.getCenter(false));
		
		//Shop upgrades\\
		for(Team t : game.getTeamManager().getTeams()){
			ShopItems.setLevel(t, "defence", 0);
			
			ShopItems.setLevel(t, "villagerdefence", 0);
			
			ShopItems.setLevel(t, "lighter", 0);
			
			ShopItems.setLevel(t, "trading", 0);	
		}
		
		//Costs\\
		ShopItems.registerItem("stoneSword", 12);
		ShopItems.registerItem("ironSword", 18);
		ShopItems.registerItem("diamondSword", 25);
		ShopItems.registerItem("diamondSword1", 30);
		ShopItems.registerItem("diamondSword2", 35);
		ShopItems.registerItem("chainArmor", 14);
		ShopItems.registerItem("ironArmor", 18);
		ShopItems.registerItem("diamondArmor", 24);
		ShopItems.registerItem("diamondArmor1", 28);
		ShopItems.registerItem("thorns", 24);
		ShopItems.registerItem("tnt", 12);
		ShopItems.registerItem("enderpearl", 18);
		ShopItems.registerItem("poison", 16);
		ShopItems.registerItem("jump", 12);
		ShopItems.registerItem("goldapple", 8);
		ShopItems.registerItem("respawn", 10);
		ShopItems.registerItem("upgradeTeamArmor", 6);
		ShopItems.registerItem("upgradeVillagerArmor", 5);
		ShopItems.registerItem("upgradeLighter", 10);
		ShopItems.registerItem("upgradeTrading", 12);
		ShopItems.registerItem("buyAllLives", 15);
		
		
		//Game loop runnable\\
		game.setLoopRunnable(new Runnable(){

			@Override
			public void run() {
				switch(game.getGameStatus()){
				case INGAME:
					for(GamePlayer player : game.getPlayers()){
						if(player.isOnline()){
							List<String> board = new ArrayList<String>();
							board.add("");
							if(game.getGamePlayerType(player).equals(GamePlayerType.PLAYER)){
								board.add("Coins: " + Coins.getBalance(player));
								board.add("Respawns: " + Respawns.getRespawns(player));
								board.add("");
							}
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
		Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
		Bukkit.getPluginManager().registerEvents(new ProjectileListener(), this);
		Bukkit.getPluginManager().registerEvents(new GameEndListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
		
		GameManager.registerGame(game);
		
	}
	
	public static void checkForGameEnd(Game g){
		if(!ending){
			List<Team> teams = g.getTeamManager().getTeams();
			Collections.sort(teams, 
					(t1, t2) -> Integer.compare(t2.getPlayersByMode(GamePlayerType.PLAYER).size(), t1.getPlayersByMode(GamePlayerType.PLAYER).size()));
			for(Team t : teams){
				System.out.println("Players: " + t.getPlayers().size() + " Players: " + t.getPlayersByMode(GamePlayerType.PLAYER).size());
			}
			if(teams.get(0).getPlayers().size() != 0){
				if(teams.get(1).getPlayersByMode(GamePlayerType.PLAYER).size() == 0){
					Team w = teams.get(1);
					g.sendMessage(ChatColor.BOLD.toString() + w.getChatColor() + w.getName() + ChatColor.RESET + ChatColor.BOLD + " wins the game!" + ChatColor.RESET + "", false, true);
					g.sendMessage("", false);
					g.sendMessage(ChatColor.AQUA + "Top Kills", false, true);
					int x = 0;
					for(GamePlayer p : g.getPlayersSortedByKills()){
						if(x == 3) break;
						g.sendMessage(p.getTeam().getChatColor() + p.getPlayer().getName() + ChatColor.RESET + ": " + g.getKills(p), false, true);
						x++;
					}
					g.sendMessage(ChatColor.GRAY + "Game restarting in 10 seconds", false, true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(MerchantWars.getPlugin(), new Runnable(){

						@Override
						public void run() {
							MerchantWars.ending = false;
							g.endGame();
						}
						
					}, 10 * 20);
				}
			}else{
				g.endGame();
			}
		}
		
	}
	
	@Override
	public void onDisable(){
		for(NPC npc : EntityManager.getNPCs()){
			npc.kill();
		}
	}
	
	public static void giveDefaultInventory(GamePlayer p){
		ItemStack h = new ItemStack(Material.LEATHER_HELMET);
		ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack b = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta m = (LeatherArmorMeta) l.getItemMeta();
		m.setColor(p.getTeam().getColor());
		h.setItemMeta(m);
		c.setItemMeta(m);
		if(ShopItems.getLevel(p.getTeam(), "defence") > 0) 
			m.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, ShopItems.getLevel(p.getTeam(), "defence"), true);
		l.setItemMeta(m);
		b.setItemMeta(m);
		
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		
		PlayerInventory i = p.getOnlinePlayer().getInventory();
		i.setHelmet(h);
		i.setChestplate(c);
		i.setLeggings(l);
		i.setBoots(b);
		
		i.setItem(0, sword);
		
	}
	
	public static Plugin getPlugin(){
		return plugin;
	}
	
}
