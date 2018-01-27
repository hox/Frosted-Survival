package com.FrostedIsles.Listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;

public class Leave implements Listener {

	private static ConfigurationManager config;
	
	public Leave(Plugin main) {
		Bukkit.getServer().getPluginManager().registerEvents(this, main);
		config = new ConfigurationManager();
		config.setup(new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml"));
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		Bukkit.broadcastMessage(Main.trColor("&7[&c-&7] &b" + p.getName()));
	}
}
