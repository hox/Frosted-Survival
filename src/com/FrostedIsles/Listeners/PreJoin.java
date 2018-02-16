package com.FrostedIsles.Listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Rank;

public class PreJoin implements Listener {

	ConfigurationManager config;

	
	public PreJoin() {
		config = new ConfigurationManager();
		config.setup(new File(Main.plugin.getDataFolder(), "config.yml"));
	}

	@EventHandler
	public void onPlayerPreJoin(AsyncPlayerPreLoginEvent e) {
		String uuid = e.getUniqueId().toString();
		Rank rank;
		String rankStr = config.data.getString(uuid + ".rank");
		rank = Rank.valueOf(rankStr);
		if (config.data.getBoolean("maintenance")) {
			if (rank.getRank() == 0) {
				e.disallow(Result.KICK_OTHER, "FrostedIsles is currently in Maintenance \n Please check back later!");
			}
		}
	}
}
