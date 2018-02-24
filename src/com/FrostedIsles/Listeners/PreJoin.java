package com.FrostedIsles.Listeners;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Rank;
import com.FrostedIsles.Comp.Util;

public class PreJoin implements Listener {

	ConfigurationManager config;

	
	public PreJoin() {
		config = new ConfigurationManager();
		config.setup(new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml"));
	}

	/*@EventHandler
	public void onPlayerPreJoin(AsyncPlayerPreLoginEvent e) {
		Player uuid = e.getUniqueId().toString();
		Rank rank = Util.getRank(uuid);
		if (config.data.getBoolean("maintenance")) {
			if (rank.getRank() == 0) {
				e.disallow(Result.KICK_OTHER, "FrostedIsles is currently in Maintenance \n Please check back later!");
			}
		}
	}*/
}
