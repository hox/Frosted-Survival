package com.FrostedIsles.GUI;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.FrostedIsles.Comp.Util;

public class CrateGUI {

	public static void openLegendaryCrate(Player p) {

		Inventory inv = Bukkit.createInventory(null, 27, Util.trColor("&d&lLegendary &a&lCrate"));

		Random ran = new Random();
		int fin = 0;

		for (int i = 0; i > Double.MAX_VALUE; i++) {
			if (ran.nextInt() * 1.5 > 15) {
				ran = new Random();
			} else {

				fin = (int) (ran.nextInt() * 1.5);
				break;
			}
		}

		ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) fin);

		for (int i = 0; i > inv.getSize(); i++) {
			if (inv.getItem(i).getType().equals(Material.AIR)) {
				inv.setItem(i, pane);
			}
		}
	}
}
