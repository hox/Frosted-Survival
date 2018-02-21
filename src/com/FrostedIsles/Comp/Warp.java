package com.FrostedIsles.Comp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Warp {
	private static ConfigurationManager warps;
	
	public Warp() {
		warps = new ConfigurationManager();
		warps.setup(new File(Main.plugin.getDataFolder(), "warps.yml"));
	}
	
	public static void addWarp(String name, Location loc) {
		ConfigurationSection warp = warps.data.createSection(name);
		
		warp.set("World", loc.getWorld().getName());
		warp.set("X", loc.getX());
		warp.set("Y", loc.getY());
		warp.set("Z", loc.getZ());
		warp.set("Pitch", loc.getPitch());
		warp.set("Yaw", loc.getYaw());
		
		warps.saveData();
	}
	
	public static void removeWarp(String name) {
		Object w = warps.data.get(name);
		
		if (w != null) {
			warps.data.set(name, null);
			warps.saveData();
		}
	}
	
	public static void teleport(Player p, String name) {
		ConfigurationSection warp = warps.data.getConfigurationSection(name);
		
		if (warp != null) {
			World w = Bukkit.getWorld(warp.getString("World"));
			double x = warp.getDouble("X");
			double y = warp.getDouble("Y");
			double z = warp.getDouble("Z");
			float pitch = (float)warp.getDouble("Pitch");
			float yaw = (float)warp.getDouble("Yaw");
			
			p.teleport(new Location(w, x, y, z, yaw, pitch));
		}
	}
	
	public static void list(Player p) {
		List<String> w = new ArrayList<>();
		w.add("Warps: ");
		
		if (warps.data.getValues(true).isEmpty()) {
			w.add("There are no warps!");
		} else {
			for (String h : warps.data.getValues(true).keySet()) {
				w.add(h);
			}
		}

		String toSend = Util.buildMessage((String[]) w.toArray(), ", ");
		Util.sendMsgNoPre(p, toSend);
	}
}
