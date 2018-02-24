package com.FrostedIsles.Comp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Warp {	
	public static void addWarp(String name, Location loc) {
		ConfigurationSection warp = Main.warps.data.createSection(name);
		
		warp.set("World", loc.getWorld().getName());
		warp.set("X", loc.getX());
		warp.set("Y", loc.getY());
		warp.set("Z", loc.getZ());
		warp.set("Pitch", loc.getPitch());
		warp.set("Yaw", loc.getYaw());
		
		Main.warps.saveData();
	}
	
	public static boolean removeWarp(String name) {
		Object w = Main.warps.data.get(name);
		
		if (w != null) {
			Main.warps.data.set(name, null);
			Main.warps.saveData();
			
			return true;
		}
		
		return false;
	}
	
	public static void teleport(Player p, String name) {
		ConfigurationSection warp = Main.warps.data.getConfigurationSection(name);
		
		if (warp != null) {
			World w = Bukkit.getWorld(warp.getString("World"));
			double x = warp.getDouble("X");
			double y = warp.getDouble("Y");
			double z = warp.getDouble("Z");
			float pitch = (float)warp.getDouble("Pitch");
			float yaw = (float)warp.getDouble("Yaw");
			
			p.teleport(new Location(w, x, y, z, yaw, pitch));
			
			Util.sendMsg(p, "Successfully teleported to warp " + name + "!");
		} else {
			Util.sendMsg(p, "That warp does not exist!");
		}
	}
	
	public static void list(Player p) {
		List<String> w = new ArrayList<>();
		w.add("Warps: ");
		
		if (Main.warps.data.getValues(false).isEmpty()) {
			w.add("There are no warps!");
		} else {
			for (String h : Main.warps.data.getValues(false).keySet()) {
				w.add(h);
			}
		}

		String toSend = Util.buildMessage((String[]) w.toArray(new String[w.size()]), ", ");
		Util.sendMsgNoPre(p, toSend);
	}
}
