package com.FrostedIsles.Comp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Home {

	public static void setHome(Player p, String name, Location loc) {
		ConfigurationSection pd = getPlayerData(p);
		Rank r = Util.getRank(p);
		int maxHomes;
		
		if (r == Rank.Default) {
			maxHomes = 1;
		}
		else if (r == Rank.VIP) {
			maxHomes = 5;
		}
		else if (r == Rank.VIPPlus) {
			maxHomes = 10;
		}
		else {
			maxHomes = Integer.MAX_VALUE;
		}
		
		if (pd.getValues(false).size() > maxHomes + 1) {
			Util.sendMsg(p, "You have maxed out your number of homes! Please delete one to set a new home.");
			return;
		}
		
		ConfigurationSection home = pd.createSection(name);

		home.set("World", loc.getWorld().getName());
		home.set("X", loc.getX());
		home.set("Y", loc.getY());
		home.set("Z", loc.getZ());
		home.set("Pitch", loc.getPitch());
		home.set("Yaw", loc.getYaw());

		Main.homes.saveData();
		Util.sendMsg(p, "Successfully set home " + name + "!");
	}

	public static void setHome(Player p, Location loc) {
		String first;
		if (getPlayerData(p).getValues(false).isEmpty()) {
			first = "1";
		} else {
			first = (String) getPlayerData(p).getValues(true).keySet().toArray()[0];
		}

		setHome(p, first, loc);
	}

	public static void removeHome(Player p, String name) {
		ConfigurationSection data = getPlayerData(p);
		Object w = data.get(name);

		if (w != null) {
			data.set(name, null);
			Main.homes.saveData();
			
			Util.sendMsg(p, "Successfully deleted home " + name + "!");
		} else {
			Util.sendMsg(p, "You do not have a home named " + name + "!");
		}
	}

	public static void removeHome(Player p) {
		String first;
		if (getPlayerData(p).getValues(true).isEmpty()) {
			first = "";
		} else {
			first = (String) getPlayerData(p).getValues(true).keySet().toArray()[0];
		}

		removeHome(p, first);
	}

	public static void teleport(Player p, String name) {
		ConfigurationSection home = getPlayerData(p).getConfigurationSection(name);

		if (home != null) {
			World w = Bukkit.getWorld(home.getString("World"));
			double x = home.getDouble("X");
			double y = home.getDouble("Y");
			double z = home.getDouble("Z");
			float pitch = (float) home.getDouble("Pitch");
			float yaw = (float) home.getDouble("Yaw");

			p.teleport(new Location(w, x, y, z, yaw, pitch));
		} else {
			Util.sendMsg(p, "Error: That home does not exist!");
		}
	}

	public static void teleport(Player p) {
		String first;
		if (getPlayerData(p).getValues(true).isEmpty()) {
			first = "";
		} else {
			first = (String) getPlayerData(p).getValues(true).keySet().toArray()[0];
		}
		teleport(p, first);
	}

	public static void list(Player p) {
		List<String> homes = new ArrayList<>();
		ConfigurationSection pd = getPlayerData(p);
		homes.add("Your homes: ");
		
		
		for (String h : pd.getValues(false).keySet()) {
			homes.add(h);
		}

		String toSend = Util.buildMessage(homes.toArray(new String[homes.size()]), ", ");
		Util.sendMsgNoPre(p, toSend);
	}

	private static ConfigurationSection getPlayerData(Player p) {
		ConfigurationSection player;
		FileConfiguration data = Main.getConfigFile("homes");
		
		player = data.getConfigurationSection(p.getUniqueId().toString());
		
		if (player == null) {
			data.set(p.getUniqueId().toString() + ".Œ", "§");
			Main.homes.saveData();
		
			player = data.getConfigurationSection(p.getUniqueId().toString());
		}

		return player;
	}
}
