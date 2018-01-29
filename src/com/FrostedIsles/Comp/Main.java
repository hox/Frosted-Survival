package com.FrostedIsles.Comp;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.FrostedIsles.Commands.Admins;
import com.FrostedIsles.Commands.Basic;
import com.FrostedIsles.Commands.Management;
import com.FrostedIsles.Commands.Moderators;
import com.FrostedIsles.Listeners.Chat;
import com.FrostedIsles.Listeners.Join;
import com.FrostedIsles.Listeners.Leave;
import com.FrostedIsles.Listeners.PreJoin;

public class Main extends JavaPlugin {
	public static Main plugin;
	public static ConfigurationManager config;

	HashMap<Player, Player> invites = new HashMap<Player, Player>();

	@Override
	public void onEnable() {
		plugin = this;
		registerConfig();
		registerCommands();
		registerEvents();
		AutoBroadcast();

	}

	@Override
	public void onDisable() {
		config.saveData();

		//Bukkit.getScheduler().cancelTasks(this);
		Bukkit.getScheduler().cancelAllTasks();
		plugin = null;
	}

	public void registerConfig() {
		saveResource("config.yml", false);
		config = new ConfigurationManager();
		config.setup(new File(this.getDataFolder(), "config.yml"));
		
	}
	
	public void registerCommands() {
		getCommand("stop").setExecutor(new Management());
		getCommand("reloadconfig").setExecutor(new Management());
		getCommand("maintenance").setExecutor(new Management());
		getCommand("setrank").setExecutor(new Management());

		getCommand("ci").setExecutor(new Moderators());
		getCommand("fly").setExecutor(new Moderators());

		getCommand("gmc").setExecutor(new Admins());
		getCommand("gms").setExecutor(new Admins());
		getCommand("gma").setExecutor(new Admins());
		getCommand("gmsp").setExecutor(new Admins());

		getCommand("spawn").setExecutor(new Basic());
		getCommand("shop").setExecutor(new Basic());
		getCommand("apply").setExecutor(new Basic());
		getCommand("who").setExecutor(new Basic());
		getCommand("report").setExecutor(new Basic());
		getCommand("rtp").setExecutor(new Basic());
	}
	
		public void registerEvents() {
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(new Chat(), this);
			pm.registerEvents(new Join(), this);
			pm.registerEvents(new PreJoin(), this);
			pm.registerEvents(new Leave(), this);
		}
		
		public void AutoBroadcast() {
			new BukkitRunnable() {
				List<String> list = getConfig().getStringList("messages");
				int progress = 0;
				public void run() {
					if(progress == list.size()) {
						progress = 0;
					}
					if(progress < (list.size() +1 )) {
						progress++;
					}else {
						progress = 0;
					}
				}
			}.runTaskTimer(this, 0, 100);
		}

}
