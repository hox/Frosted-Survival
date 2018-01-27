package com.FrostedIsles.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Rank;

public class Moderators implements CommandExecutor {

	private static ConfigurationManager config;

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

		if (cmd.equalsIgnoreCase("ci") || cmd.equalsIgnoreCase("clear")) {
			clearInv(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("fly")) {
			fly(p, sender, args, console, rank);
		}

		return true;
	}

	private void fly(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (console) {
			Main.sendMsg(sender, Main.pd);
		} else {
			if (rank.getRank() >= Rank.Moderator()) {
				if (p.isFlying()) {
					p.setFlying(false);
					Main.sendMsg(p, "&cFlight mode enabled!");
				} else {
					p.setFlying(true);
					Main.sendMsg(p, "&cFlight mode disabled!");
				}
			}
		}
	}

	private void clearInv(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (console) {
			if (args.length != 1) {
				Main.sendMsg(sender, "&cUsage: &a>>&7ci {PLAYER}");
			} else {
				try {
					Player t = Bukkit.getPlayer(args[0]);
					t.getInventory().clear();
					Main.sendMsg(sender, "&7Success, You have cleared " + t.getName() + "'s Inventory!");
					Main.sendMsg(t, "&7Your Inventory has been cleared!");
				} catch (Exception e) {
					Main.sendMsg(sender, Main.pnf);
				}
			}
		} else if (rank.getRank() >= Rank.Moderator() || rank.getRank() == Rank.Builder()) {
			if (args.length == 0) {
				p.getInventory().clear();
				Main.sendMsg(sender, "&7Success, You have cleared your Inventory!");
			} else if (args.length == 1) {
				try {
					Player t = Bukkit.getPlayer(args[0]);
					t.getInventory().clear();
					Main.sendMsg(sender, "&7Success, You have cleared " + t.getName() + "'s Inventory!");
					Main.sendMsg(t, "&7Your Inventory has been cleared!");
				} catch (Exception e) {
					Main.sendMsg(sender, Main.pnf);
				}
			} else
				Main.sendMsg(sender, "&cUsage: &7/ci [PLAYER]");
		}
	}

}
