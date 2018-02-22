package com.FrostedIsles.Comp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kits {	
	public static void giveKit(Player p, String kitname) {
		ConfigurationSection section = Main.kits.data.getConfigurationSection(kitname);
		
		if (section != null) {
			Set<String> items = section.getValues(true).keySet();
			
			for (String n : items) {
				p.getInventory().addItem(section.getItemStack(n));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void addKit(String name, String[] items) {
		ConfigurationSection section = Main.kits.data.createSection(name);
		
		for (Integer i = 0; i < items.length; i++) {
			String[] item = items[i].split(",");
			String[] id = item[0].split(":");
			ItemStack stack;
			
			if (id.length == 2) {
				stack = new ItemStack(Integer.parseInt(id[0]), Integer.parseInt(item[1]), Short.parseShort(id[1]));
			} else {
				stack = new ItemStack(Integer.parseInt(id[0]), Integer.parseInt(item[1]));
			}
			
			section.set(i.toString(), stack);
		}
		
		Main.kits.saveData();
	}
	
	public static void removeKit(String name) {
		Object kit = Main.kits.data.get(name);
		
		if (kit != null) {
			Main.kits.data.set(name, null);
			Main.kits.saveData();
		}
	}
	
	public static void list(Player p) {
		List<String> k = new ArrayList<>();
		k.add("Available kits: ");

		if (Main.kits.data.getValues(true).isEmpty()) {
			k.add("There are no kits!");
		} else {
			for (String h : Main.kits.data.getValues(false).keySet()) {
				k.add(h);
			}
		}

		String toSend = Util.buildMessage((String[]) k.toArray(new String[k.size()]), ", ");
		Util.sendMsgNoPre(p, toSend);
	}
}
