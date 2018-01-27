package com.FrostedIsles.Comp;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.FrostedIsles.Commands.Admins;
import com.FrostedIsles.Commands.Basic;
import com.FrostedIsles.Commands.Management;
import com.FrostedIsles.Commands.Moderators;
import com.FrostedIsles.Listeners.Chat;
import com.FrostedIsles.Listeners.Join;
import com.FrostedIsles.Listeners.Leave;
import com.FrostedIsles.Listeners.PreJoin;

public class Main extends JavaPlugin {
	public static Main plugin;
	public static ConfigurationManager config;

	public static final String prefix = trColor("&7[&bFrosted&3Isles&7]&r ");
	public static final String pd = trColor("&cError: &7Permission Denied!");
	public static final String pnf = trColor("&cError: &7Player not found!");
	public static final String wi = trColor("&cError: &7You are not permitted to do that in this world!");

	public static final int max = Integer.MAX_VALUE;

	HashMap<Player, Player> invites = new HashMap<Player, Player>();

	@Override
	public void onEnable() {

		new Chat(this);
		new PreJoin(this);
		new Join(this);
		new Leave(this);

		plugin = this;
		registerCommands();

		config = new ConfigurationManager();
		config.setup(new File(this.getDataFolder(), "config.yml"));

		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() { // TODO: Auto-Broadcast
			@Override
			public void run() {
				for (int i = 0; i < Double.MAX_VALUE; i++) {
					try {
						Thread.sleep(180 * 1000);
						msgAll("&f&l • &3&lDont forget to vote with &8&l/&b&lvote&3&l!");
						Thread.sleep(180 * 1000);
						msgAll("&f&l • &c&lTo report a player do &8&l/&b&lreport&c&l!");
						Thread.sleep(180 * 1000);
						msgAll("&f&l • &9&lTo apply for staff, Just do &8&l/&b&lapply&9&l!");
						Thread.sleep(180 * 1000);
						// msgAll("&f&l • ");
						// Thread.sleep(180*1000);
						// msgAll("&f&l • ");
						// Thread.sleep(180*1000);
					} catch (InterruptedException e) {
					}
				}
			}
		});
	}

	@Override
	public void onDisable() {
		config.saveData();

		Bukkit.getScheduler().cancelTasks(this);
		plugin = null;
	}

	public void registerCommands() {
		getCommand("stop").setExecutor(new Management());
		getCommand("reloadconfig").setExecutor(new Management());
		getCommand("maintenance").setExecutor(new Management());
		getCommand("setrank").setExecutor(new Management());

		getCommand("ci").setExecutor(new Moderators());
		getCommand("fly").setExecutor(new Moderators());

		getCommand("gmc").setExecutor(new Admins());
		getCommand("gms").setExecutor(new Admins());
		getCommand("gma").setExecutor(new Admins());
		getCommand("gmsp").setExecutor(new Admins());

		getCommand("spawn").setExecutor(new Basic());
		getCommand("shop").setExecutor(new Basic());
		getCommand("apply").setExecutor(new Basic());
		getCommand("who").setExecutor(new Basic());
		getCommand("report").setExecutor(new Basic());
		getCommand("rtp").setExecutor(new Basic());
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

}
