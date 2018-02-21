package com.FrostedIsles.Comp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Kits {
	private static ConfigurationManager kits;
	
	public Kits() {
		kits = new ConfigurationManager();
		kits.setup(new File(Main.plugin.getDataFolder(), "kits.yml"));
	}
	
	public static void giveKit(Player p, String kitname) {
		ConfigurationSection section = kits.data.getConfigurationSection(kitname);
		
		if (section != null) {
			Inventory inv = p.getInventory();
			Set<String> items = section.getValues(true).keySet();
			
			for (String n : items) {
				if (section.isItemStack(n)) { // Key goes with an item stack
					inv.addItem(section.getItemStack(n));
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void addKit(String name, String[] items) {
		ConfigurationSection section = kits.data.createSection(name);
		
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
		
		kits.saveData();
	}
	
	public static void removeKit(String name) {
		Object kit = kits.data.get(name);
		
		if (kit != null) {
			kits.data.set(name, null);
			kits.saveData();
		}
	}
	
	public static void list(Player p) {
		List<String> k = new ArrayList<>();
		k.add("Available kits: ");

		if (kits.data.getValues(true).isEmpty()) {
			k.add("There are no kits!");
		} else {
			for (String h : kits.data.getValues(true).keySet()) {
				k.add(h);
			}
		}

		String toSend = Util.buildMessage((String[]) k.toArray(), ", ");
		Util.sendMsgNoPre(p, toSend);
	}
}
