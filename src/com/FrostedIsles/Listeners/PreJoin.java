package com.FrostedIsles.Listeners;

import java.io.File;

import org.bukkit.event.Listener;
import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;

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
