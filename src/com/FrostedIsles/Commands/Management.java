package com.FrostedIsles.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Rank;

public class Management implements CommandExecutor {
	
	private ConfigurationManager config;
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String cmd, String[] args) {
		config = new ConfigurationManager();
		config.setup(new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml"));

		Player p = null;
		boolean console = true;
		Rank rank = null;

		try {
			p = Bukkit.getPlayer(sender.getName());
			String rankStr = config.getData().getString(p.getUniqueId().toString() + ".rank");
			rank = Rank.valueOf(rankStr);
			console = false;
		} catch (Exception e) {
		}

		if (cmd.equalsIgnoreCase("stop") || cmd.equalsIgnoreCase("restart")) {
			restart(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("setrank")) {
			setRank(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("reloadconfig")) {
			reloadconf(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("resetconfig")) {
			resetconf(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("maintenance")) {
			maintenance(p, sender, args, console, rank);
		}

		return true;
	}

	private void maintenance(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {

		config.reloadData();

		if (console) {
			if (config.data.getBoolean("maintenance")) {
				config.data.set("maintenance", false);
				Main.msgAll("&cStaff, Please be alert that an Administrator has disabled Maintenance Mode!");
			} else {
				config.data.set("maintenance", true);
				for (Player pls : Bukkit.getOnlinePlayers()) {
					Rank plsRank = Main.getRank(pls);
					if (plsRank.getRank() == 0) {
						pls.kickPlayer("FrostedIsles is going into Maintenance \n Please check back later!");
					}
				}
				Main.msgAll("&cStaff, Please be alert that an Administrator has enabled Maintenance Mode!");
			}
		} else {
			if (rank.getRank() == Rank.Owner()) {
				if (config.data.getBoolean("maintenance")) {
					config.data.set("maintenance", false);
					Main.msgAll("&cStaff, Please be alert that an Administrator has disabled Maintenance Mode!");
				} else {
					config.data.set("maintenance", true);
					for (Player pls : Bukkit.getOnlinePlayers()) {
						Rank plsRank = Main.getRank(pls);
						if (plsRank.getRank() == 0) {
							pls.kickPlayer("FrostedIsles is going into Maintenance \n Please check back later!");
						}
					}
					Main.msgAll("&cStaff, Please be alert that an Administrator has enabled Maintenance Mode!");
				}
			}
		}
		config.saveData();
	}

	private void setRank(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (console || rank.getRank() >= Rank.Manager()) {
			if (args.length == 2) {
				String puuid = config.data.getString(args[0]);
				if (args[1].equals("Default") || args[1].equals("Builder") || args[1].equals("Moderator")
						|| args[1].equals("Admin") || args[1].equals("Developer") || args[1].equals("Manager")
						|| args[1].equals("CoOwner") || args[1].equals("Owner")) {
					String rankStr = args[1].toString();
					config.data.set(puuid + ".rank", rankStr);
					config.saveData();
				} else {
					Main.sendMsg(sender, "&cError: &7Rank not found in registry! Note: Ranks are case-sensitive.");
				}
			} else {
				Main.sendMsg(sender, "&cUsage: &7/setrank {PLAYER} {RANK}");
			}
		} else {
			Main.sendMsg(sender, Main.pd);
		}
	}

	private void reloadconf(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (!console) {
			if (rank.getRank() < Rank.Manager()) {
				Main.sendMsg(sender, Main.pd);
				return;
			}
		} else {

			config.reloadData();
			Main.sendMsg(sender, "&7Reloaded config!");
		}
	}

	private void resetconf(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (!console) {
			if (rank.getRank() < Rank.Manager()) {
				Main.sendMsg(sender, Main.pd);
				return;
			}
		} else {
			config.clearData();
			Main.sendMsg(sender, "&7Reset config!");
		}
	}

	private void restart(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getPlugin(Main.class), new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 60; i > 0; i--) {
						if (i != 1 && i == 60 || i == 30 || i == 15 || i == 10 || i <= 5) {
							Main.msgAll("&cThe server will be restarting in " + i + " seconds..");
						}
						if (i == 1) {
							Main.msgAll("&cThe server will be restarting in " + i + " second..");
						}

						Thread.sleep(1000);
					}
					Main.msgAll("&cThe server is restarting");
					Thread.sleep(1000);
				} catch (Exception e) {
					Log.error("An error has occurred while attempting to restart. " + e.getMessage());
				}
				Bukkit.shutdown();
			}
		});
	}

}
