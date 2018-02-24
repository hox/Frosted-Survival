package com.FrostedIsles.Comp;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.FrostedIsles.Commands.Admins;
import com.FrostedIsles.Commands.Basic;
import com.FrostedIsles.Commands.Management;
import com.FrostedIsles.Commands.Moderators;
import com.FrostedIsles.Listeners.Chat;
import com.FrostedIsles.Listeners.InvClick;
import com.FrostedIsles.Listeners.Join;
import com.FrostedIsles.Listeners.Leave;
import com.FrostedIsles.Listeners.PreJoin;
import com.FrostedIsles.Notifier.NotifierServer;

public class Main extends JavaPlugin {
	public static Main plugin;
	public static ConfigurationManager config;
	public static ConfigurationManager kits;
	public static ConfigurationManager homes;
	public static ConfigurationManager warps;
	public static NotifierServer notifier;

	@Override
	public void onEnable() {
		plugin = this;
		registerConfig();
		registerCommands();
		registerEvents();
		AutoBroadcast();
		startNotifier();
		config.data.set("maintenance", false);
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelAllTasks();
		//notifier.finalize();
	}

	public void registerConfig() {
		config = new ConfigurationManager();
		config.setup(new File(this.getDataFolder(), "config.yml"));
		kits = new ConfigurationManager();
		kits.setup(new File(this.getDataFolder(), "kits.yml"));
		homes = new ConfigurationManager();
		homes.setup(new File(this.getDataFolder(), "homes.yml"));
		warps = new ConfigurationManager();
		warps.setup(new File(this.getDataFolder(), "warps.yml"));
	}

	public void registerCommands() {
		Management manage = new Management();
		getCommand("stop").setExecutor(manage);
		getCommand("reloadconfig").setExecutor(manage);
		getCommand("maintenance").setExecutor(manage);
		getCommand("setrank").setExecutor(manage);
		getCommand("setwarp").setExecutor(manage);
		getCommand("delwarp").setExecutor(manage);
		getCommand("addkit").setExecutor(manage);
		getCommand("delkit").setExecutor(manage);

		Moderators mod = new Moderators();
		getCommand("ci").setExecutor(mod);
		getCommand("fly").setExecutor(mod);
		getCommand("invsee").setExecutor(mod);

		Admins admin = new Admins();
		getCommand("gmc").setExecutor(admin);
		getCommand("gms").setExecutor(admin);
		getCommand("gma").setExecutor(admin);
		getCommand("gmsp").setExecutor(admin);
		getCommand("broadcaster").setExecutor(admin);

		Basic basic = new Basic();
		getCommand("spawn").setExecutor(basic);
		getCommand("shop").setExecutor(basic);
		getCommand("apply").setExecutor(basic);
		getCommand("who").setExecutor(basic);
		getCommand("report").setExecutor(basic);
		getCommand("rtp").setExecutor(basic);
		getCommand("home").setExecutor(basic);
		getCommand("homes").setExecutor(basic);
		getCommand("sethome").setExecutor(basic);
		getCommand("delhome").setExecutor(basic);
		getCommand("warp").setExecutor(basic);
		getCommand("warps").setExecutor(basic);
		getCommand("kit").setExecutor(basic);
		getCommand("kits").setExecutor(basic);
		getCommand("msg").setExecutor(basic);
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Chat(), this);
		pm.registerEvents(new Join(), this);
		pm.registerEvents(new PreJoin(), this);
		pm.registerEvents(new Leave(), this);
		pm.registerEvents(new InvClick(), this);
	}

	public void AutoBroadcast() {
		int delay;
		delay = config.getData().getInt("broadcaster-interval");
		new BukkitRunnable() {
			List<String> list = config.getData().getStringList("messages");
			int progress = 0;

			public void run() {
				if (progress == list.size()) {
					progress = 0;
				}
				if (progress < (list.size() + 1)) {
					progress++;
				} else {
					progress = 0;
				}
			}
		}.runTaskTimer(this, 0, delay);
	}

	public void startNotifier() {
		notifier = new NotifierServer();
		//notifier.message.SetNotifyMessage("Plugin Enabled", "The plugin has been enabled and notifier is working.");
	}
	
	public static FileConfiguration getConfigFile(String name) {
		name = name.toLowerCase();
		
		switch (name) {
		case "main":
			return config.data;
		case "kits":
			return kits.data;
		case "homes":
			return homes.data;
		case "warps":
			return warps.data;
		default:
			Log.warn("Config for " + name + " does not exist!");
			return null;
		}
	}
}
