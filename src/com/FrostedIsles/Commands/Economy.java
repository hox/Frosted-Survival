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
import com.FrostedIsles.Comp.Util;
import com.FrostedIsles.Economy.EconomyManager;
import com.massivecraft.factions.integration.Econ;

public class Economy implements CommandExecutor{
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

		if (cmd.equalsIgnoreCase("economy")) {
			eco(p, sender, args, console, rank);
		}
		return true;
	}

	private void eco(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		
		if(rank.getRank() >= Rank.Admin() || console) {
			if(args.length == 0) {
				Util.sendMsg(sender, "&cEconomy");
				Util.sendMsg(sender, "&6- to add balance do /economy add");
				Util.sendMsg(sender, "&6- to clear all the messages use /economy remove");
				Util.sendMsg(sender, "&6- to set the interval use /economy set");
			}
			if(args.length != 3 && args.length !=0 ) {
				Util.sendMsg(sender, "&cUsage: /economy <add/remove/set> <player> <amount>");
			}
			if(args[0].equalsIgnoreCase("add")) {
				if(!EconomyManager.hasAccount(args[1])) {
					Util.sendMsg(sender, "&cError: player does not have an account");
				}
				double amount = 0;
				try {
					amount = Double.parseDouble(args[2]);
				}catch (Exception e) {
					Util.sendMsg(sender, "&cError: Invalid amount");
				}
				EconomyManager.setBalance(args[1], EconomyManager.getBalance(args[1])+ amount);
			}
			else if(args[0].equalsIgnoreCase("remove")) {
				if(!EconomyManager.hasAccount(args[1])) {
					Util.sendMsg(sender, "&cError: player does not have an account");
				}
				double amount = 0;
				try {
					amount = Double.parseDouble(args[2]);
				}catch (Exception e) {
					Util.sendMsg(sender, "&cError: Invalid amount");
				}
				EconomyManager.setBalance(args[1], EconomyManager.getBalance(args[1])- amount);
			}
			else if(args[0].equalsIgnoreCase("set")) {
				if(!EconomyManager.hasAccount(args[1])) {
					Util.sendMsg(sender, "&cError: player does not have an account");
				}
				double amount = 0;
				try {
					amount = Double.parseDouble(args[2]);
				}catch (Exception e) {
					Util.sendMsg(sender, "&cError: Invalid amount");
				}
				EconomyManager.setBalance(args[1], amount);
			}else {
				Util.sendMsg(sender, Util.pnf);
				
			}
		}else {
			Util.sendMsg(sender, Util.pd);
		}

		
	}

}
