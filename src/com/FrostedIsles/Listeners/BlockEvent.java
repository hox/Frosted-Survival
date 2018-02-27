package com.FrostedIsles.Listeners;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockEvent implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		
		if (b.getType().equals(Material.MOB_SPAWNER)) {
			CreatureSpawner s = (CreatureSpawner) b.getState();
			ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
			
			if (tool.getType() == Material.IRON_PICKAXE || tool.getType() == Material.DIAMOND_PICKAXE || tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
				ArrayList<String> type = new ArrayList<String>();
				type.add(s.getSpawnedType().toString());
				
				ItemStack i = new ItemStack(b.getType(), 1);
				ItemMeta im = i.getItemMeta();
				im.setLore(type);
				i.setItemMeta(im);

				b.getWorld().dropItemNaturally(b.getLocation(), i);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Block b = e.getBlockPlaced();
		ItemMeta hold = e.getItemInHand().getItemMeta();
		
		if (b.getType().equals(Material.MOB_SPAWNER) && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			CreatureSpawner s = (CreatureSpawner) b.getState();
			String name = hold.getLore().get(0);
			EntityType type = EntityType.fromName(name);
			Log.info(type.toString());
			
			s.setSpawnedType(type);
			s.update();
		}
	}
}
