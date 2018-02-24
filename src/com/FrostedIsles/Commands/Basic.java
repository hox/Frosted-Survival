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
import com.FrostedIsles.Comp.Home;
import com.FrostedIsles.Comp.Kits;
import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Util;
import com.FrostedIsles.Comp.Warp;
import com.FrostedIsles.Comp.Rank;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class Basic implements CommandExecutor {
	private static ConfigurationManager config;
	
	private final int RTP_RADIUS = 10000;

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
			Util.sendMsg(p, "&7Teleporting you to spawn...");
			p.teleport(new Location(Bukkit.getWorld("Survival"), -779, 136.75, 1002.5, 0, 0));
		}
		
		if(cmd.equalsIgnoreCase("msg") || cmd.equalsIgnoreCase("pm") || cmd.equalsIgnoreCase("m") || cmd.equalsIgnoreCase("tell") || cmd.equalsIgnoreCase("t")) {
			msg(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("who")) {
			who(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("shop")) {
			Util.sendMsg(p, "&7Teleporting you to shop...");
			p.teleport(new Location(Bukkit.getWorld("Survival"), 6513.5, 63.2, 2174.5, 90, 0));
		}
		
		if(cmd.equalsIgnoreCase("rtp") || cmd.equalsIgnoreCase("wild")) {
			rtp(p);
		}
		
		if(cmd.equalsIgnoreCase("report")) {
			report(p, sender, args, console, rank);
		}
		
		if(cmd.equalsIgnoreCase("vote")) {
			
		}
		
		if(cmd.equalsIgnoreCase("apply")) {
			IChatBaseComponent cm = ChatSerializer.a(Util.trColor("{\"text\":\"&7[&bFrosted&3Isles&7]&r Click Here to open the application link!\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"http://frostedisles.ddns.net/apply/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Apply for staff!\",\"color\":\"dark_purple\"}]}}}"));
			PacketPlayOutChat packet = new PacketPlayOutChat(cm);
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
		
		if (cmd.equalsIgnoreCase("homes")) {
			Home.list(p);
		}
		
		if (cmd.equalsIgnoreCase("home")) {
			if (args.length > 0) {
				Home.teleport(p, args[0]);
			} else {
				Home.teleport(p);
			}
		}
		
		if (cmd.equalsIgnoreCase("sethome")) {
			if (args.length > 0) {
				Home.setHome(p, args[0], p.getLocation());
			} else {
				Home.setHome(p, p.getLocation());
			}
		}
		
		if (cmd.equalsIgnoreCase("delhome")) {
			if (args.length > 0) {
				Home.removeHome(p, args[0]);
			} else {
				Home.removeHome(p);
			}
		}
		
		if (cmd.equalsIgnoreCase("warps")) {
			Warp.list(p);
		}
		
		if (cmd.equalsIgnoreCase("warp")) {
			if (args.length == 0) {
				Warp.list(p);
			} else {
				Warp.teleport(p, args[0]);
			}
		}
		
		if (cmd.equalsIgnoreCase("kits")) {
			Kits.list(p);
		}
		
		if (cmd.equalsIgnoreCase("kit")) {
			if (args.length == 0) {
				Util.sendMsg(p, "Usage: /kit [KitName]");
			} else {
				Kits.giveKit(p, args[0]);
			}
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
		str.append(Util.trColor("&cInfo of &f" + t.getName() + "&c:\n"));
		str.append(Util.trColor("&cIs Op: &f" + op + "\n"));
		str.append(Util.trColor("&cIs Flying: &f" + fly + "\n"));
		str.append(Util.trColor("&cTime played: " + calcTime(seconds) + "\n"));
		// str.append(trColor(""));
		return str.toString();
	}

	private void msg(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		
	}
	
	private void who(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		String usage = "&cUsage: &7/who {PLAYER}";
		if (console) {
			if (args.length == 1) {
				try {
					Player t = Bukkit.getPlayer(args[0]);
					sender.sendMessage(whoMsg(t));
				} catch (Exception e) {
					Util.sendMsg(sender, Util.pnf);
				}
			} else {
				Util.sendMsg(sender, usage);
			}
		} else {
			if (rank.getRank() >= Rank.Moderator()) {
				if (args.length == 1) {
					try {
						Player t = Bukkit.getPlayer(args[0]);
						sender.sendMessage(whoMsg(t));
					} catch (Exception e) {
						Util.sendMsg(sender, Util.pnf);
					}
				} else {
					Util.sendMsg(sender, usage);
				}
			}
		}
	}

	private void rtp(Player p) {
		Location loc = p.getLocation();

		Random r = new Random();

		Location teleportlocation = null;
		
		for (int i = 0; i < 10; i++) { // Try up to 10 times to find a location
			boolean xPos = (r.nextInt(10) < 5); // Flip the sign for the X value?
			boolean zPos = (r.nextInt(10) < 5); // Flip the sign for the Z value?

			int x = r.nextInt(RTP_RADIUS);
			int z = r.nextInt(RTP_RADIUS);

			if (xPos) {
				x *= -1;
			}
			if (zPos) {
				z *= -1;
			}

			teleportlocation = new Location(p.getWorld(), x, 63, z); // Location to TP to
			Location newPos2 = new Location(p.getWorld(), x, 62, z); // Block below
			Location newPos3 = new Location(p.getWorld(), x, 64, z); // Block above

			boolean safe = false;
			while (newPos3.getY() < 255) {
				if (!teleportlocation.getBlock().isEmpty() || teleportlocation.getBlock().isLiquid()
						|| (teleportlocation.getBlock().getLightFromSky() < 8)) { // Current block is occupied
					teleportlocation.add(0, 1, 0);
					newPos2.add(0, 1, 0);
					newPos3.add(0, 1, 0);
				} else if (newPos2.getBlock().isLiquid() || newPos2.getBlock().isEmpty()) { // Block below is unsafe
					teleportlocation.add(0, 1, 0);
					newPos2.add(0, 1, 0);
					newPos3.add(0, 1, 0);
				} else if (!newPos3.getBlock().isEmpty()) { // Block at head height is occupied
															// (potential suffocation)
					teleportlocation.add(0, 1, 0);
					newPos2.add(0, 1, 0);
					newPos3.add(0, 1, 0);
				} else {
					p.teleport(teleportlocation.add(0.5, 0, 0.5)); // New position is safe to teleport to
					safe = true;
					break; // Stop going upwards
				}
			}
			if (safe)
				break; // Don't need to look for any new locations
		}

		if (teleportlocation.getY() >= 255) {
			Util.sendMsg(p, "&7We were unable to find a safe spot for you to teleport to. Please try again.");
			p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 5.0F, 5.0F);
			return;
		}

		Util.sendMsg(p, "&7Teleported " + (int) teleportlocation.distance(loc) +
						" blocks away from last known location.");
		p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 5.0F);
	}
	
	private void report(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		IChatBaseComponent cm = ChatSerializer.a(Util.trColor("{\"text\":\"&7[&bFrosted&3Isles&7]&r Click Here to open the reporting link!\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"http://frostedisles.ddns.net/report/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Report a player!\",\"color\":\"dark_purple\"}]}}}"));
		PacketPlayOutChat packet = new PacketPlayOutChat(cm);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}
}
