package com.FrostedIsles.Comp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;


public class Util {
	
	public static ConfigurationManager config;
	
	public static final String prefix = trColor("&7[&bFrosted&3Isles&7]&r ");
	public static final String pd = trColor("&cError: &7Permission Denied!");
	public static final String pnf = trColor("&cError: &7Player not found!");
	public static final String wi = trColor("&cError: &7You are not permitted to do that in this world!");
	
	public static final int max = Integer.MAX_VALUE;
	
	public static final Location SPAWN = new Location(Bukkit.getWorld("Survival"), -779, 137, 1002.5, 0, 0);
	public static final Location SHOP = new Location(Bukkit.getWorld("Survival"), 6513.5, 63.2, 2174.5, 90, 0);
	
	public Util(Main main) {
		config = new ConfigurationManager();
		config.setup(new File(main.getDataFolder(), "config.yml"));
	}
	
	public static Rank getRank(Player pls) {
		String rankStr = Main.getConfigFile("main").getString(pls.getUniqueId().toString() + ".rank");
		Log.info(rankStr);
		
		try {
			return Enum.valueOf(Rank.class, rankStr);
		} catch (Exception e) {
			Log.warn("Unable to determine rank for " + pls.getName() + ". Returning default rank.");
			return Rank.Default;
		}
	}

	public static void sendMsg(CommandSender p, String str) {
		p.sendMessage(trColor(prefix + str));
	}

	public static String trColor(String str) {
		str = ChatColor.translateAlternateColorCodes('&', str);
		return str;
	}

	public static void msgAll(String s) {
		Bukkit.broadcastMessage(trColor(prefix + s));
	}
	
	public static void sendMsgNoPre(CommandSender p, String str) {
		p.sendMessage(trColor(str));
	}

	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	public static String buildMessage(String[] args) {
		return buildMessage(args, " ");
	}

	public static String buildMessage(String[] args, String delimiter) {
		String message = "";
		for (int i = 1; i < args.length; i++) {
			if (!message.equals("")) {
				message = message + delimiter;
			}
			message = message + args[i];
		}
		return message;
	}
}

