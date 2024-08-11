package com.github.aasmus.pvptoggle.utils;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import com.github.aasmus.pvptoggle.PvPToggle;

public class Chat {

    // sends message without a parameter
    public static void send(CommandSender sender, String message) {
        String msg = PvPToggle.instance.getConfig().getString("MESSAGES." + message);
        if (msg == null || msg.isEmpty())
            return;
        BukkitAudiences audiences = PvPToggle.instance.adventure();
        audiences.sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(msg));
    }

    // sends message with a parameter
    public static void send(CommandSender sender, String message, String parameter) {
        String msg = PvPToggle.instance.getConfig().getString("MESSAGES." + message);
        if (msg == null || msg.isEmpty())
            return;
        BukkitAudiences audiences = PvPToggle.instance.adventure();
        String output = msg.replaceAll("<parameter>", parameter);
        audiences.sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(output));
    }

    // sends message with a parameter and pvp state
    public static void send(CommandSender sender, String message, String parameter, boolean pvpState) {
        String msg = PvPToggle.instance.getConfig().getString("MESSAGES." + message);
        if (msg == null || msg.isEmpty())
            return;
        String output = msg.replaceAll("<parameter>", parameter);
        if (pvpState) {
            output = output.replaceAll("<pvpstate>", "off");
        } else {
            output = output.replaceAll("<pvpstate>", "on");
        }
        BukkitAudiences audiences = PvPToggle.instance.adventure();
        audiences.sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(output));
    }

    public static String getColoredMessage(String message) {
        String msg = PvPToggle.instance.getConfig().getString("MESSAGES." + message);
        if (msg == null || msg.isEmpty())
            return msg;
        return BukkitComponentSerializer.legacy().serialize(MiniMessage.miniMessage().deserialize(msg));
    }

}
