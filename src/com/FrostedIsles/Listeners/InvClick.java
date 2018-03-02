package com.FrostedIsles.Listeners;

import java.io.File;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.FrostedIsles.Commands.Moderators;
import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;

public class InvClick implements Listener{

	private static ConfigurationManager config;
	
	public InvClick() {
		config = new ConfigurationManager();
		config.setup(new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml"));
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		if(e.getInventory() == Moderators.targetInv && e.getWhoClicked() == Moderators.playerClicked) {
			e.setCancelled(true);
		}
	}
	
	

}
