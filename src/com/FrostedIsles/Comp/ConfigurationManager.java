package com.FrostedIsles.Comp;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationManager {

	static ConfigurationManager instance = new ConfigurationManager();
	public FileConfiguration data;
	File dfile;

	java.util.logging.Logger Logger = Bukkit.getLogger();

	public static ConfigurationManager getInstance() {
		return instance;
	}

	public void setup(File file) {
		this.dfile = file;
		if (!this.dfile.exists()) {
			try {
				this.dfile.createNewFile();
				Main.plugin.saveResource("config.yml", false);
				
			} catch (IOException e) {
				Logger.severe(ChatColor.RED + "Could not create file configuration!");
			}
		}
		this.data = YamlConfiguration.loadConfiguration(this.dfile);
	}

	public FileConfiguration getData() {
		return this.data;
	}

	public void clearData() {
		try {
			this.dfile.delete();
			setup(dfile);
		} catch (Exception e) {
		}
	}

	public void saveData() {
		try {
			this.data.save(this.dfile);
		} catch (IOException e) {
			Logger.severe(ChatColor.RED + "Could not save file configuration!");
		}
	}

	public void reloadData() {
		this.data = YamlConfiguration.loadConfiguration(this.dfile);
	}

}