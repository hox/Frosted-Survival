package com.FrostedIsles.Listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Rank;
import com.FrostedIsles.Comp.Util;
import com.massivecraft.factions.entity.MPlayer;

public class Chat implements Listener {

	private static ConfigurationManager config;
	
	
	public Chat() {
		config = new ConfigurationManager();
		config.setup(new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml"));
	}


	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		MPlayer mplayer = MPlayer.get(p.getUniqueId().toString());
		String faction = mplayer.getFactionName();
		String Guild = "";
		if(mplayer.hasFaction()) Guild = "&7[&b"+faction+"&7] ";
		config.reloadData();
		
		Rank rank = Rank.Default;
		config.reloadData();
		String rankStr = config.getData().getString(p.getUniqueId().toString() + ".rank");
		try {
			rank = Rank.valueOf(rankStr);
		} catch (Exception ex) {}

		switch (rank) {
		case Builder:
			e.setFormat(Util.trColor(Guild + Builder() + p.getName() + " &c>> &f" + e.getMessage()));
			break;
		case Moderator:
			e.setFormat(Util.trColor(Guild + Moderator() + p.getName() + " &c>> &f" + e.getMessage()));
			break;
		case Admin:
			e.setFormat(Util.trColor(Guild + Admin() + p.getName() + " &c>> &f" + e.getMessage()));
			break;
		case Manager:
			e.setFormat(Util.trColor(Guild + Manager() + p.getName() + " &c>> &f" + e.getMessage()));
			break;
		case Owner:
			e.setFormat(Util.trColor(Guild + Owner() + p.getName() + " &c>> &f" + e.getMessage()));
			break;
		default:
			e.setFormat(Util.trColor(Guild + Default() + p.getName() + " &c>> &f")
					+ ChatColor.stripColor(e.getMessage()));
		}
	}

	public static String Owner() {
		String String = ChatColor.translateAlternateColorCodes('&', "&7[&cOwner&7]&r &c");
		return String;
	}

	public static String CoOwner() {
		String String = ChatColor.translateAlternateColorCodes('&', "&7[&cCo&f-&cOwner&7]&r &c");
		return String;
	}

	public static String Manager() {
		String String = ChatColor.translateAlternateColorCodes('&', "&7[&bManager&7]&r &c");
		return String;
	}

	public static String Admin() {
		String String = ChatColor.translateAlternateColorCodes('&', "&7[&cAdmin&7]&r &c");
		return String;
	}

	public static String Moderator() {
		String String = ChatColor.translateAlternateColorCodes('&', "&7[&dModerator&7]&r &c");
		return String;
	}

	public static String Builder() {
		String String = ChatColor.translateAlternateColorCodes('&', "&7[&6Builder&7]&r &c");
		return String;
	}

	public static String Default() {
		String String = ChatColor.translateAlternateColorCodes('&', "&7[&aMember&7]&r ");
		return String;
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();

		for (Player pls : Bukkit.getOnlinePlayers()) {
			Rank rank = Rank.Default;
			config.reloadData();
			String rankStr = config.getData().getString(pls.getUniqueId().toString() + ".rank");
			try {
				rank = Rank.valueOf(rankStr);
			} catch (Exception ex) {
			}
			if (rank.getRank() >= Rank.Moderator()) {
				pls.sendMessage(
						Util.trColor("&7[Command] " + p.getName() + " >> ") + ChatColor.stripColor(e.getMessage()));
			}
		}

	}
}
