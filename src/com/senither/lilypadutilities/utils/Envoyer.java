package com.senither.lilypadutilities.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Envoyer {

    public String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String colorize(String message, char defaultColor) {
        return colorize("&" + defaultColor + message);
    }

    public List<String> colorize(List<String> messages) {
        List<String> message = new ArrayList<>();
        for (String str : messages) {
            if (str == null) {
                continue;
            }
            message.add(colorize(str, '7'));
        }
        return message;
    }

    public List<String> colorize(List<String> messages, char defaultColor) {
        List<String> message = new ArrayList<>();
        for (String str : messages) {
            if (str == null) {
                continue;
            }
            message.add(colorize(str, defaultColor));
        }
        return message;
    }

    public String decolorize(String message) {
        return ChatColor.stripColor(message);
    }

    public List<String> decolorize(List<String> messages) {
        List<String> message = new ArrayList<>();
        for (String str : messages) {
            if (str == null) {
                continue;
            }
            message.add(decolorize(str));
        }
        return message;
    }

    public void missingPermission(Player player, String permission) {
        player.sendMessage(ChatColor.RED + "Influent permissions to execute this command.");
        player.sendMessage(ChatColor.RED + "You're missing the permission node " + ChatColor.ITALIC + permission);
    }

    public void missingPermission(CommandSender player, String permission) {
        player.sendMessage(ChatColor.RED + "Influent permissions to execute this command.");
        player.sendMessage(ChatColor.RED + "You're missing the permission node " + ChatColor.ITALIC + permission);
    }

    public void sendMessage(CommandSender player, String message) {
        player.sendMessage(colorize(message));
    }

    public void broadcast(String string) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendMessage(player, string);
        }
    }
}