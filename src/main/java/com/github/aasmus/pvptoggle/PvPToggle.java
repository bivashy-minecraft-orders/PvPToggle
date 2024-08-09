package com.github.aasmus.pvptoggle;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.aasmus.pvptoggle.listeners.PlayerJoinListener;
import com.github.aasmus.pvptoggle.listeners.PlayerLeaveListener;
import com.github.aasmus.pvptoggle.listeners.DamageListener;
import com.github.aasmus.pvptoggle.listeners.PlayerChangeWorldListener;
import com.github.aasmus.pvptoggle.utils.PersistentData;
import com.github.aasmus.pvptoggle.utils.PlaceholderAPIHook;

public class PvPToggle extends JavaPlugin implements Listener {

    public FileConfiguration config;
    public static List<String> blockedWorlds;
    public static PvPToggle instance;
    public HashMap<UUID, Boolean> players = new HashMap<>(); //False is pvp on True is pvp off
    public HashMap<UUID, Date> cooldowns = new HashMap<>();
    public PersistentData dataUtils;

    @Override
    public void onEnable() {
        instance = this;
        this.config = getConfig();
        this.saveDefaultConfig();

        File PVPData = new File(getDataFolder(), "Data");
        dataUtils = new PersistentData(PVPData);

        //register events
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangeWorldListener(), this);
        //register command
        this.getCommand("pvp").setExecutor(new PvPCommand());

        blockedWorlds = config.getStringList("SETTINGS.BLOCKED_WORLDS");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook(this).register();
        }
    }

    @Override
    public void onDisable() {

    }

}
