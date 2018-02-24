package com.FrostedIsles.Comp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.Inventory;

public class Kits {
	public static void giveKit(Player p, String kitname) {
		ConfigurationSection section = Main.kits.data.getConfigurationSection(kitname);

		if (section != null) {
			Set<String> items = section.getValues(false).keySet();

			for (String n : items) {
				ConfigurationSection i = section.getConfigurationSection(n);
				
				//Manually reconstruct the item from the config
				ItemStack item = new ItemStack(
						Material.getMaterial(i.getString("type", "AIR")),
						i.getInt("amount", 1),
						(short) i.getInt("damage", 0));
				
				ItemMeta im = item.getItemMeta();
				//Apply custom name and lore, if applicable
				if (i.contains("name")) {
					im.setDisplayName(i.getString("name"));
				}
				if (i.contains("lore")) {
					im.setLore(i.getStringList("lore"));
				}
				
				//Apply all enchantments, if any
				ConfigurationSection ench = i.getConfigurationSection("enchants");				
				if (ench != null) {
					for (Entry<String, Object> e : ench.getValues(false).entrySet()) {
						Enchantment en = Enchantment.getByName(e.getKey());
						int level = (int) e.getValue();
						
						im.addEnchant(en, level, true);
					}
				}
				item.setItemMeta(im);
				
				p.getInventory().addItem(item);
			}

			Util.sendMsg(p, "You have received the " + kitname + " kit!");
		} else {
			Util.sendMsg(p, "That kit does not exist!");
		}
	}

	public static void addKit(String name, Player sender) {
		ConfigurationSection section = Main.kits.data.createSection(name);
		Inventory inv = sender.getInventory();

		Integer n = 1;
		// Pull items from the sender's inventory and use them for the kit
		for (int i = 0; i < 36; i++) {
			ItemStack item = inv.getItem(i);

			if (item != null) {
				String ns = n.toString() + ".";
				ItemMeta im = item.getItemMeta();

				section.set(ns + "type", item.getType().name());
				section.set(ns + "damage", item.getDurability());
				section.set(ns + "amount", item.getAmount());

				section.set(ns + "name", im.getDisplayName());
				section.set(ns + "lore", im.getLore());

				for (Entry<Enchantment, Integer> e : im.getEnchants().entrySet()) {
					section.set(ns + "enchants." + e.getKey().getName(), e.getValue());
				}

				n++;
			}
		}

		Main.kits.saveData();
		Util.sendMsg(sender, "Successfully created " + name + " kit!");
	}

	public static boolean removeKit(String name) {
		Object kit = Main.kits.data.get(name);

		if (kit != null) {
			Main.kits.data.set(name, null);
			Main.kits.saveData();
			
			return true;
		}
		return false;
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
