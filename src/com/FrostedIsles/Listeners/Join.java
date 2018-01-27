package com.FrostedIsles.Listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;

public class Join implements Listener {

	private static ConfigurationManager config;

	public Join(Plugin main) {
		Bukkit.getServer().getPluginManager().registerEvents(this, main);
		config = new ConfigurationManager();
		config.setup(new File(main.getDataFolder(), "config.yml"));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(null);
		if(config.data.getBoolean(p.getUniqueId().toString()+".banned")) {
			p.kickPlayer("You have been permanently banned from FrostedIsles! \n You may appeal on the forums");
			Bukkit.broadcastMessage(Main.trColor("&7[Server] User " + p.getName() + ", tried to login but is banned!"));
			return;
		}
		Bukkit.broadcastMessage(Main.trColor("&7[&a+&7] &b" + p.getName()));
		if(!config.data.contains(p.getUniqueId().toString() + ".banned")) {
			config.data.set(p.getUniqueId().toString() + ".banned", false);
		}
		if (config.data.contains(p.getUniqueId().toString() + ".name")
				|| config.data.contains(p.getUniqueId().toString() + ".uuid")) {
			config.data.set(p.getUniqueId().toString() + ".uuid", p.getUniqueId().toString());
		} else {
			Main.msgAll("&f&l" + p.getName() + " joined for the first time!");
			config.data.set(p.getUniqueId().toString() + ".uuid", p.getUniqueId().toString());
			config.data.set(p.getUniqueId().toString() + ".rank", "Default");
			config.data.set(p.getUniqueId().toString() + ".name", p.getName());
			config.data.set(p.getName(), p.getUniqueId().toString());
			config.saveData();
		}
	}

}
