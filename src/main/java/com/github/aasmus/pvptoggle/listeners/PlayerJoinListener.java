package com.github.aasmus.pvptoggle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.github.aasmus.pvptoggle.PvPToggle;
import com.github.aasmus.pvptoggle.utils.Util;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!PvPToggle.instance.getConfig().getBoolean("SETTINGS.PERSISTENT_PVP_STATE")) {
                PvPToggle.instance.players.put(p.getUniqueId(),
                        PvPToggle.instance.getConfig().getBoolean("SETTINGS.DEFAULT_PVP_OFF")); //add player to players hash map and set their pvp state
                if (PvPToggle.instance.players.get(p.getUniqueId())) {
                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                        Util.particleEffect(p);
                    }
                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                        Util.changeNametag(p, "&c");
                    }
                }
            } else {
                PvPToggle.instance.dataUtils.addPlayer(p);
                PvPToggle.instance.players.put(p.getUniqueId(),
                        PvPToggle.instance.dataUtils.getPlayerPvPState(p)); //add player to players hash map and set their pvp state
                if (PvPToggle.instance.players.get(p.getUniqueId())) {
                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                        Util.particleEffect(p);
                    }
                    if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                        Util.changeNametag(p, "&c");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!PvPToggle.instance.getConfig().getBoolean("SETTINGS.PERSISTENT_PVP_STATE")) {
            PvPToggle.instance.players.put(player.getUniqueId(),
                    PvPToggle.instance.getConfig().getBoolean("SETTINGS.DEFAULT_PVP_OFF")); //add player to players hash map and set their pvp state
            if (PvPToggle.instance.players.get(player.getUniqueId())) {
                if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                    Util.particleEffect(player);
                }
                if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                    Util.changeNametag(player, "&c");
                }
            }
        } else {
            PvPToggle.instance.dataUtils.addPlayer(player);
            PvPToggle.instance.players.put(player.getUniqueId(),
                    PvPToggle.instance.dataUtils.getPlayerPvPState(player)); //add player to players hash map and set their pvp state
            if (PvPToggle.instance.players.get(player.getUniqueId())) {
                if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.PARTICLES")) {
                    Util.particleEffect(player);
                }
                if (PvPToggle.instance.getConfig().getBoolean("SETTINGS.NAMETAG")) {
                    Util.changeNametag(player, "&c");
                }
            }
        }
    }

}
