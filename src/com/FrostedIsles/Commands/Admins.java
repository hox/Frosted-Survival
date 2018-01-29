package com.FrostedIsles.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.FrostedIsles.Comp.Rank;
import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;
import com.FrostedIsles.Comp.Utilities;

public class Admins implements CommandExecutor {

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

		if (cmd.equalsIgnoreCase("gmc")) {
			gmc(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("gms")) {
			gms(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("gma")) {
			gma(p, sender, args, console, rank);
		}

		if (cmd.equalsIgnoreCase("gmsp")) {
			gmsp(p, sender, args, console, rank);
		}

		return true;
	}

	private void gmc(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (console) {
			if (args.length != 1) {
				Utilities.sendMsg(sender, "&cUsage: &a>>&7gmc {PLAYER}");
			} else {
				try {
					Player t = Bukkit.getPlayer(args[0]);
					t.setGameMode(GameMode.CREATIVE);
					Utilities.sendMsg(sender, "&7Success, You set " + t.getName() + "'s gamemode to Creative!");
					Utilities.sendMsg(t, "&7Your gamemode has been switched to Creative!");
				} catch (Exception e) {
					Utilities.sendMsg(sender, Utilities.pnf);
				}
			}
		} else {
			if (rank.getRank() >= Rank.Admin() || rank.getRank() == Rank.Builder()) {
				if (args.length == 0) {
					p.setGameMode(GameMode.CREATIVE);
					Utilities.sendMsg(sender, "&7Success, Your gamemode has been switched to Creative!");
				} else if (args.length == 1) {
					try {
						Player t = Bukkit.getPlayer(args[0]);
						Rank trank;
						String rankStr = config.getData().getString(t.getUniqueId().toString() + ".rank");
						trank = Enum.valueOf(Rank.class, rankStr);

						if (rank.getRank() >= Utilities.max - 1) {
							t.setGameMode(GameMode.CREATIVE);
							Utilities.sendMsg(sender, "&7Success, You set " + t.getName() + "'s gamemode to Creative!");
							Utilities.sendMsg(t, "&7Your gamemode has been switched to Creative!");
						} else {
							if (trank.getRank() == 0) {
								Utilities.sendMsg(sender, "&cError: &7You cant set a Member's gamemode to creative!");
							}
						}
					} catch (Exception e) {
						Utilities.sendMsg(sender, Utilities.pnf);
					}
				} else
					Utilities.sendMsg(sender, "&cUsage: &7/gmc [Player]");
			} else {
				Utilities.sendMsg(sender, Utilities.pd);
			}
		}
	}

	private void gms(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (console) {
			if (args.length != 1) {
				Utilities.sendMsg(sender, "&cUsage: &a>>&7gms {PLAYER}");
			} else {
				try {
					Player t = Bukkit.getPlayer(args[0]);
					t.setGameMode(GameMode.SURVIVAL);
					Utilities.sendMsg(sender, "&7Success, You set " + t.getName() + "'s gamemode to Survival!");
					Utilities.sendMsg(t, "&7Your gamemode has been switched to Survival!");
				} catch (Exception e) {
					Utilities.sendMsg(sender, Utilities.pnf);
				}
			}
		} else {
			if (rank.getRank() >= Rank.Admin() || rank.getRank() == Rank.Builder()) {
				if (args.length == 0) {
					p.setGameMode(GameMode.SURVIVAL);
					Utilities.sendMsg(sender, "&7Success, Your gamemode has been switched to Survival!");
				} else if (args.length == 1) {
					try {
						Player t = Bukkit.getPlayer(args[0]);
						Rank trank;
						String rankStr = config.getData().getString(t.getUniqueId().toString() + ".rank");
						trank = Enum.valueOf(Rank.class, rankStr);

						if (rank.getRank() >= Utilities.max - 1) {
							t.setGameMode(GameMode.SURVIVAL);
							Utilities.sendMsg(sender, "&7Success, You set " + t.getName() + "'s gamemode to Survival!");
							Utilities.sendMsg(t, "&7Your gamemode has been switched to Survival!");
						} else {
							if (trank.getRank() == 0) {
								Utilities.sendMsg(sender, "&cError: &7You cant set a Member's gamemode to Survival!");
							}
						}
					} catch (Exception e) {
						Utilities.sendMsg(sender, Utilities.pnf);
					}
				} else
					Utilities.sendMsg(sender, "&cUsage: &7/gmc [Player]");
			} else {
				Utilities.sendMsg(sender, Utilities.pd);
			}
		}
	}

	private void gma(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (console) {
			if (args.length != 1) {
				Utilities.sendMsg(sender, "&cUsage: &a>>&7gma {PLAYER}");
			} else {
				try {
					Player t = Bukkit.getPlayer(args[0]);
					t.setGameMode(GameMode.ADVENTURE);
					Utilities.sendMsg(sender, "&7Success, You set " + t.getName() + "'s gamemode to Adventure!");
					Utilities.sendMsg(t, "&7Your gamemode has been switched to Adventure!");
				} catch (Exception e) {
					Utilities.sendMsg(sender, Utilities.pnf);
				}
			}
		} else {
			if (rank.getRank() >= Rank.Admin() || rank.getRank() == Rank.Builder()) {
				if (args.length == 0) {
					p.setGameMode(GameMode.ADVENTURE);
					Utilities.sendMsg(sender, "&7Success, Your gamemode has been switched to Adventure!");
				} else if (args.length == 1) {
					try {
						Player t = Bukkit.getPlayer(args[0]);
						Rank trank;
						String rankStr = config.getData().getString(t.getUniqueId().toString() + ".rank");
						trank = Enum.valueOf(Rank.class, rankStr);

						if (rank.getRank() >= Utilities.max - 1) {
							t.setGameMode(GameMode.ADVENTURE);
							Utilities.sendMsg(sender, "&7Success, You set " + t.getName() + "'s gamemode to Adventure!");
							Utilities.sendMsg(t, "&7Your gamemode has been switched to Adventure!");
						} else {
							if (trank.getRank() == 0) {
								Utilities.sendMsg(sender, "&cError: &7You cant set a Member's gamemode to Adventure!");
							}
						}
					} catch (Exception e) {
						Utilities.sendMsg(sender, Utilities.pnf);
					}
				} else
					Utilities.sendMsg(sender, "&cUsage: &7/gmc [Player]");
			} else {
				Utilities.sendMsg(sender, Utilities.pd);
			}
		}
	}

	private void gmsp(Player p, CommandSender sender, String[] args, boolean console, Rank rank) {
		if (console) {
			if (args.length != 1) {
				Utilities.sendMsg(sender, "&cUsage: &a>>&7gmc {PLAYER}");
			} else {
				try {
					Player t = Bukkit.getPlayer(args[0]);
					t.setGameMode(GameMode.SPECTATOR);
					Utilities.sendMsg(sender, "&7Success, You set " + t.getName() + "'s gamemode to Spectator!");
					Utilities.sendMsg(t, "&7Your gamemode has been switched to Spectator!");
				} catch (Exception e) {
					Utilities.sendMsg(sender, Utilities.pnf);
				}
			}
		} else {
			if (rank.getRank() >= Rank.Admin() || rank.getRank() == Rank.Builder()) {
				if (args.length == 0) {
					p.setGameMode(GameMode.SPECTATOR);
					Utilities.sendMsg(sender, "&7Success, Your gamemode has been switched to Spectator!");
				} else if (args.length == 1) {
					try {
						Player t = Bukkit.getPlayer(args[0]);
						Rank trank;
						String rankStr = config.getData().getString(t.getUniqueId().toString() + ".rank");
						trank = Enum.valueOf(Rank.class, rankStr);

						if (rank.getRank() >= Utilities.max - 1) {
							t.setGameMode(GameMode.SPECTATOR);
							Utilities.sendMsg(sender, "&7Success, You set " + t.getName() + "'s gamemode to Spectator!");
							Utilities.sendMsg(t, "&7Your gamemode has been switched to Spectator!");
						} else {
							if (trank.getRank() == 0) {
								Utilities.sendMsg(sender, "&cError: &7You cant set a Member's gamemode to Spectator!");
							}
						}
					} catch (Exception e) {
						Utilities.sendMsg(sender, Utilities.pnf);
					}
				} else
					Utilities.sendMsg(sender, "&cUsage: &7/gmc [Player]");
			} else {
				Utilities.sendMsg(sender, Utilities.pd);
			}
		}
	}
}
