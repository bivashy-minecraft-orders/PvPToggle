package com.github.aasmus.pvptoggle.utils;

import org.bukkit.entity.Player;
import com.github.aasmus.pvptoggle.PvPToggle;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final PvPToggle plugin;

    public PlaceholderAPIHook(PvPToggle plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        // %pvp_state%
        if (identifier.equals("state")) {
            return PvPToggle.instance.players.get(player.getUniqueId()) ? Chat.getColoredMessage("PLACEHOLDER_STATE_OFF") : Chat.getColoredMessage("PLACEHOLDER_STATE_ON");
        }

        return null;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "pvp";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

}
