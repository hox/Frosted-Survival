package com.FrostedIsles.Comp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Home {
	private static ConfigurationManager homes;

	public Home() {
		homes = new ConfigurationManager();
		homes.setup(new File(Main.plugin.getDataFolder(), "homes.yml"));
	}

	public static void setHome(Player p, String name, Location loc) {
		ConfigurationSection home = getPlayerData(p).createSection(name);

		home.set("World", loc.getWorld().getName());
		home.set("X", loc.getX());
		home.set("Y", loc.getY());
		home.set("Z", loc.getZ());
		home.set("Pitch", loc.getPitch());
		home.set("Yaw", loc.getYaw());
		
		homes.saveData();
	}

	public static void setHome(Player p, Location loc) {
		String first;
		if (getPlayerData(p).getValues(true).isEmpty()) {
			first = "";
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
			homes.saveData();
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
		homes.add("Your homes: ");

		for (String h : getPlayerData(p).getValues(true).keySet()) {
			homes.add(h);
		}

		String toSend = Util.buildMessage((String[]) homes.toArray(), ", ");
		Util.sendMsgNoPre(p, toSend);
	}

	private static ConfigurationSection getPlayerData(Player p) {
		ConfigurationSection player = homes.data.getConfigurationSection(p.getUniqueId().toString());

		if (player == null) {
			player = homes.data.createSection(p.getUniqueId().toString());
		}

		return player;
	}
}
