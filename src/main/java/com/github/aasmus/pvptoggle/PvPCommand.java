package com.github.aasmus.pvptoggle;

import com.github.aasmus.pvptoggle.events.PVPToggleEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import com.github.aasmus.pvptoggle.utils.Chat;
import com.github.aasmus.pvptoggle.utils.Util;

public class PvPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) { //check if command sender is console
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if (args.length == 0) {
                Chat.send(console, "HELP_HEADER");
                Chat.send(console, "HELP_SET_OTHERS");
            } else {
                try {
                    Player other = Bukkit.getPlayerExact(args[1]);
                    if (other == null) { //make sure the player is online
                        Chat.send(console, "NO_PLAYER", args[1]);
                    } else { //set pvp state
                        Boolean current = PvPToggle.instance.players.get(other.getUniqueId());
                        if (args[0].equals("reload")) {
                            reloadConfig();
                            return true;
                        } else if (args[0].equals("toggle")) {
                            if (current) {
                                if (Util.setPlayerState(other, false, console)) {
                                    Bukkit.getPluginManager().callEvent(new PVPToggleEvent(other, true));
                                    Chat.send(other, "PVP_STATE_ENABLED");
                                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                                        Util.particleEffect(other.getPlayer());
                                    }
                                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                        Util.changeNametag(other.getPlayer(), "&c");
                                    }
                                }
                            } else {
                                if (Util.setPlayerState(other, true, console)) {
                                    Bukkit.getPluginManager().callEvent(new PVPToggleEvent(other, false));
                                    Chat.send(other, "PVP_STATE_DISABLED");
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("on")) {
                            if (Util.setPlayerState(other, false, console)) {
                                Bukkit.getPluginManager().callEvent(new PVPToggleEvent(other, true));
                                Chat.send(other, "PVP_STATE_ENABLED");
                                if (current) {
                                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                                        Util.particleEffect(other.getPlayer());
                                    }
                                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                        Util.changeNametag(other.getPlayer(), "&c");
                                    }
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("off")) {
                            if (Util.setPlayerState(other, true, console)) {
                                Bukkit.getPluginManager().callEvent(new PVPToggleEvent(other, false));
                                Chat.send(other, "PVP_STATE_DISABLED");
                                if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                    Util.changeNametag(other.getPlayer(), "reset");
                                }
                            }
                        }
                        current = PvPToggle.instance.players.get(other.getUniqueId());
                        Chat.send(console, "PVP_STATE_CHANGED_OTHERS", other.getDisplayName(), current);
                    }
                } catch (Exception e) {
                    //nothing needs to be done
                }
            }
        } else if (sender instanceof Player) { //check if command sender is player
            if (cmd.getName().equalsIgnoreCase("pvp")) {
                Player p = (Player) sender;
                if (args.length == 0) {
                    Chat.send(p, "PVP_STATUS", null, PvPToggle.instance.players.get(p.getUniqueId()));
                    Chat.send(p, "HELP_HEADER");
                    Chat.send(p, "HELP_GENERAL_USEAGE");
                    if (p.hasPermission("pvptoggle.others"))
                        Chat.send(p, "HELP_VIEW_OTHERS");
                    if (p.hasPermission("pvptoggle.others.set"))
                        Chat.send(p, "HELP_SET_OTHERS");
                } else if (args.length == 1) {
                    if (args[0].equals("reload") && p.hasPermission("pvptoggle.reload")) {
                        reloadConfig();
                        return true;
                    }
                    if (p.hasPermission("pvptoggle.allow")) {

                        if (!Util.getCooldown(p) || p.hasPermission("pvptoggle.bypass")) {
                            Boolean current = PvPToggle.instance.players.get(p.getUniqueId());
                            if (args[0].equals("toggle")) {
                                if (current) {
                                    Util.setCooldownTime(p);
                                    if (Util.setPlayerState(p, false, p)) {
                                        Bukkit.getPluginManager().callEvent(new PVPToggleEvent(p, true));
                                        Chat.send(p, "PVP_STATE_ENABLED");
                                        if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                                            Util.particleEffect(p.getPlayer());
                                        }
                                        if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                            Util.changeNametag(p.getPlayer(), "&c");
                                        }
                                    }
                                } else {
                                    if (Util.setPlayerState(p, true, p)) {
                                        Bukkit.getPluginManager().callEvent(new PVPToggleEvent(p, false));
                                        Chat.send(p, "PVP_STATE_DISABLED");
                                    }
                                }
                            } else if (args[0].equalsIgnoreCase("on")) {
                                Util.setCooldownTime(p);
                                if (Util.setPlayerState(p, false, p)) {
                                    Bukkit.getPluginManager().callEvent(new PVPToggleEvent(p, true));
                                    Chat.send(p, "PVP_STATE_ENABLED");
                                    if (current) {
                                        if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                                            Util.particleEffect(p.getPlayer());
                                        }
                                        if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                            Util.changeNametag(p.getPlayer(), "&c");
                                        }
                                    }
                                }
                            } else if (args[0].equalsIgnoreCase("off")) {
                                if (Util.setPlayerState(p, true, p)) {
                                    Bukkit.getPluginManager().callEvent(new PVPToggleEvent(p, false));
                                    Chat.send(p, "PVP_STATE_DISABLED");
                                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                        Util.changeNametag(p.getPlayer(), "reset");
                                    }
                                }
                            } else if (args[0].equalsIgnoreCase("status")) {
                                Chat.send(p, "PVP_STATUS", null, current);
                            } else {
                                if (sender.hasPermission("pvptoggle.others")) {
                                    Player other = Bukkit.getPlayerExact(args[0]);
                                    if (other == null) {
                                        Chat.send(p, "NO_PLAYER", args[0]);
                                    } else {
                                        current = PvPToggle.instance.players.get(other.getUniqueId());
                                        Chat.send(p, "PVP_STATUS_OTHERS", other.getDisplayName(), current);
                                    }
                                } else {
                                    if (!args[0].contains("\\")) {
                                        Chat.send(p, "COMMAND_INVALID_PARAMETER", args[0]);
                                    }
                                }
                            }
                        }
                    }
                } else if (args.length == 2) {
                    if (sender.hasPermission("pvptoggle.others.set")) {
                        Player other = Bukkit.getPlayerExact(args[1]);
                        if (other == null) {
                            Chat.send(p, "NO_PLAYER", args[1]);
                        } else {
                            Boolean current = PvPToggle.instance.players.get(other.getUniqueId());
                            if (args[0].equals("toggle")) {
                                if (current) {
                                    if (Util.setPlayerState(other, false, sender)) {
                                        Bukkit.getPluginManager().callEvent(new PVPToggleEvent(other, true));
                                        Chat.send(other, "PVP_STATE_ENABLED");
                                        if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                                            Util.particleEffect(other.getPlayer());
                                        }
                                        if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                            Util.changeNametag(p.getPlayer(), "&c");
                                        }
                                    }
                                } else {
                                    if (Util.setPlayerState(other, true, sender)) {
                                        Bukkit.getPluginManager().callEvent(new PVPToggleEvent(other, false));
                                        Chat.send(other, "PVP_STATE_DISABLED");
                                    }
                                }
                            } else if (args[0].equalsIgnoreCase("on")) {
                                if (Util.setPlayerState(other, false, sender)) {
                                    Bukkit.getPluginManager().callEvent(new PVPToggleEvent(other, true));
                                    if (current) {
                                        if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                                            Util.particleEffect(other.getPlayer());
                                        }
                                        if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                            Util.changeNametag(p.getPlayer(), "&c");
                                        }
                                    }
                                    Chat.send(other, "PVP_STATE_ENABLED");
                                }
                            } else if (args[0].equalsIgnoreCase("off")) {
                                if (Util.setPlayerState(other, true, sender)) {
                                    Bukkit.getPluginManager().callEvent(new PVPToggleEvent(other, false));
                                    Chat.send(other, "PVP_STATE_DISABLED");
                                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                                        Util.changeNametag(other.getPlayer(), "reset");
                                    }
                                }
                            }
                            current = PvPToggle.instance.players.get(other.getUniqueId());
                            Chat.send(p, "PVP_STATE_CHANGED_OTHERS", other.getDisplayName(), current);
                        }
                    } else {
                        Chat.send(p, "COMMAND_NO_PERMISSION");
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void reloadConfig() {
        PvPToggle.instance.reloadConfig();
    }

}
