package com.FrostedIsles.Comp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Util {
	
	public static ConfigurationManager config;
	
	public static final String prefix = trColor("&7[&bFrosted&3Isles&7]&r ");
	public static final String pd = trColor("&cError: &7Permission Denied!");
	public static final String pnf = trColor("&cError: &7Player not found!");
	public static final String wi = trColor("&cError: &7You are not permitted to do that in this world!");
	
	public static final int max = Integer.MAX_VALUE;
	
	public Util(Main main) {
		config = new ConfigurationManager();
		config.setup(new File(main.getDataFolder(), "config.yml"));
	}
	
	public static Rank getRank(Player pls) {
		Rank rank;
		String rankStr = config.data.getString(pls.getUniqueId().toString() + ".rank");
		rank = Enum.valueOf(Rank.class, rankStr);
		return rank;
	}

	public static Rank getRankByUUID(String uuid) {
		Rank rank;
		String rankStr = config.data.getString(uuid + ".rank");
		rank = Enum.valueOf(Rank.class, rankStr);
		return rank;
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
	  
	public static String buildMessage(String[] args)
	  {
	    String message = "";
	    for (int i = 1; i < args.length; i++) {
	      if (!message.equals("")) {
	        message = message + " ";
	      }
	      message = message + args[i];
	    }
	    return message;
	  }
	}


