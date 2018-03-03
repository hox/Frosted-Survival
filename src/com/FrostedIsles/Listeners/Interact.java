package com.FrostedIsles.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.FrostedIsles.Comp.Util;
import com.FrostedIsles.GUI.CrateGUI;

public class Interact implements Listener{

	private CrateGUI crate;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(!event.hasItem()) {
			return;
		}else if(event.hasBlock()) {
			return;
		}else if(!event.getItem().hasItemMeta()) {
			return;
		}else if(event.getClickedBlock().getType() != Material.CHEST) {
			return;
		}else if(event.getItem().getType() != Material.TRIPWIRE_HOOK) {
			return;
		}else if(!event.getItem().getItemMeta().getDisplayName().contains("Crate Key")) {
			return;
		}
		
		String type = event.getItem().getItemMeta().getLore().get(1);
		
		ItemStack newItemStack = new ItemStack(event.getItem().getType(), event.getItem().getAmount()-1);
		
		Player p = event.getPlayer();
		p.setItemInHand(newItemStack);
		
		crate.activateCrate(p);
		
		event.setCancelled(true);
		
		Util.sendMsg(p, "&c You Used your " + type + " crate key");
	}
}
