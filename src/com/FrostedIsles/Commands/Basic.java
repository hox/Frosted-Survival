package com.FrostedIsles.Commands;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Rank;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class Basic implements CommandExecutor {

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

		if (cmd.equalsIgnoreCase("spawn") || cmd.equalsIgnoreCase("hub")) {
			Main.sendMsg(p, "&7Teleporting you to spawn...");
			p.teleport(new Location(Bukkit.getWorld("Survival"), -779, 136.75, 1002.5, 0, 0));
		}

		if (cmd.equalsIgnoreCase("who")) {
			who(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("shop")) {
			Main.sendMsg(p, "&7Teleporting you to shop...");
			p.teleport(new Location(Bukkit.getWorld("Survival"), 6513.5, 63.2, 2174.5, 90, 0));
		}
		
		if(cmd.equalsIgnoreCase("rtp") || cmd.equalsIgnoreCase("wild")) {
			rtp(p);
		}
		
		if(cmd.equalsIgnoreCase("report")) {
			
		}
		
		if(cmd.equalsIgnoreCase("apply")) {
			IChatBaseComponent cm = ChatSerializer.a(Main.trColor("{\"text\":\"&7[&bFrosted&3Isles&7]&r Click Here to open the application link!\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"http://frostedisles.ddns.net/apply/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Apply for staff!\",\"color\":\"dark_purple\"}]}}}"));
			PacketPlayOutChat packet = new PacketPlayOutChat(cm);
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
		return true;
	}

	public String calcTime(int seconds) {
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
		return "Days: " + day + ", Hours: " + hours + ", Minutes: " + minute + ", Seconds: " + second;
	}

	private String whoMsg(Player t) {
		StringBuilder str = new StringBuilder();
		boolean op = false;
		boolean fly = false;
		if (t.isOp())
			op = true;
		if (t.isFlying())
			fly = true;
		int seconds = Math.abs(t.getTicksLived() / 20);
		str.append(Main.trColor("&cInfo of &f" + t.getName() + "&c:\n"));
		str.append(Main.trColor("&cIs Op: &f" + op + "\n"));
		str.append(Main.trColor("&cIs Flying: &f" + fly + "\n"));
		str.append(Main.trColor("&cTime played: " + calcTime(seconds) + "\n"));
		// str.append(trColor(""));
		return str.toString();
	}

	private void who(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		String usage = "&cUsage: &7/who {PLAYER}";
		if (console) {
			if (args.length == 1) {
				try {
					Player t = Bukkit.getPlayer(args[0]);
					sender.sendMessage(whoMsg(t));
				} catch (Exception e) {
					Main.sendMsg(sender, Main.pnf);
				}
			} else {
				Main.sendMsg(sender, usage);
			}
		} else {
			if (rank.getRank() >= Rank.Moderator()) {
				if (args.length == 1) {
					try {
						Player t = Bukkit.getPlayer(args[0]);
						sender.sendMessage(whoMsg(t));
					} catch (Exception e) {
						Main.sendMsg(sender, Main.pnf);
					}
				} else {
					Main.sendMsg(sender, usage);
				}
			}
		}
	}

	private void rtp(Player p) {
		Location loc = p.getLocation();

		Random r = new Random();

		int x = r.nextInt(500) + 1;
		int y = 69;
		int z = r.nextInt(500) + 1;

		Location teleportlocation = new Location(p.getWorld(), x, y, z);

		p.teleport(teleportlocation);

		Main.sendMsg(p,
				"&7Teleported " + (int) teleportlocation.distance(loc) + " blocks away from last known location.");
		p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 5.0F);
	}
}
