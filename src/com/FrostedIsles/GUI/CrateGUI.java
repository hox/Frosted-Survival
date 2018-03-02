package com.FrostedIsles.GUI;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Util;

public class CrateGUI {

	
	static Map<UUID, Integer> crateUsesMap;
	static Random random;
	
	static String[] prizes = {"Material:Diamond:&6Diamond", "Material:Dirt:&4Evil Dirt", "Rank:VIP:VIP Rank"};
	
	/*public void LegendaryCrate(Player player) {

		Inventory inv = Bukkit.createInventory(null, 27, Util.trColor("&d&lLegendary &a&lCrate"));
		

		

		for (int i = 0; i > inv.getSize(); i++) {
			if (inv.getItem(i).getType().equals(Material.AIR)) {
				inv.setItem(i, pane);
			}
		}
	}*/
	
	private static String choosePrize() {
		return prizes[random.nextInt(prizes.length)];
	}
	private static Material[] items = {Material.DIAMOND, Material.REDSTONE, Material.NAME_TAG};
	
	public static void activateCrate(Player p) {
		/*if(this.crateUsesMap.get(player.getUniqueId()) == null) {
			this.crateUsesMap.put(player.getUniqueId(), 0);
		}
		this.crateUsesMap.put(player.getUniqueId(), this.crateUsesMap.get(player.getUniqueId()) + 1);*/
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, Util.trColor("&d&lLegendary &a&lCrate"));
		p.openInventory(inv);
		startInventory(inv, p);
	}
		
	private static void startInventory(final Inventory inv, final Player p) {
		startFrame((short) 1, 0L, inv, p);
		startFrame((short) 2, 10L, inv, p);
		startFrame((short) 3, 15L, inv, p);
		startFrame((short) 4, 20L, inv, p);
		startFrame((short) 5, 25L, inv, p);
		startFrame((short) 6, 30L, inv, p);
		startFrame((short) 7, 35L, inv, p);
		startFrame((short) 9, 40L, inv, p);
		startFrame((short) 10, 45L, inv, p);
		startFrame((short) 5, 20L, inv, p);
		selectPrize(p, inv);
	}
	
	private static void startFrame(final short sh, final long delay, final Inventory inv, final Player p) {
		final Sound sound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		new BukkitRunnable() {	
			public void run() {
				
				for(int i =0; i<inv.getSize(); i++) {
					
					inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, sh));
					
				}
				ItemStack is = new ItemStack(items[random.nextInt(items.length)]);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(Util.trColor("&6Reward"));
				is.setItemMeta(im);
				inv.setItem(13, is);
				p.playSound(p.getLocation(), sound, 1, 1);
				
				cancel();
			}
		}.runTaskLater(Main.plugin, delay);
	}
	
	private static void selectPrize(final Player player, final Inventory inv) {
		new BukkitRunnable() {
			
			public void run() {
				String prize = choosePrize();
				
				String[] prizeIndex = prize.split("\\:");
				if(prize.contains("Rank")) {
					Util.msgAll(player.getDisplayName() +" won the " + prizeIndex[2] + " rank!");
					//TODO add set rank
					
					
					ItemStack prizeItem = new ItemStack(Material.NAME_TAG);
					ItemMeta prizeMeta = prizeItem.getItemMeta();
					prizeMeta.setDisplayName(Util.trColor("Item: " + prizeIndex[2]));
					prizeItem.setItemMeta(prizeMeta);
					inv.setItem(13, prizeItem);
					
					
				}else if (prize.contains("Material")) {
					Util.msgAll(player.getDisplayName() +" won the prize " + prizeIndex[2] + "!");
					player.getInventory().addItem(new ItemStack(Material.valueOf(prizeIndex[1].toUpperCase())));
				}
				
				ItemStack prizeItem = new ItemStack(Material.matchMaterial(prizeIndex[1].replaceAll(" ", " ")));
				ItemMeta prizeMeta = prizeItem.getItemMeta();
				prizeMeta.setDisplayName(Util.trColor("Item: " + prizeIndex[2]));
				prizeItem.setItemMeta(prizeMeta);
				inv.setItem(13, prizeItem);
				
			}
			
			//Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
			//FireworkMeta fireMeta = firework.getFireworkMeta();
			//fireMeta.setPower(10);

				}.runTaskLater(Main.plugin, 55L);
	}
	
}
