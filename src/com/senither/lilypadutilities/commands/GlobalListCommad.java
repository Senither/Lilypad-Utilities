package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.network.NetworkServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GlobalListCommad implements CommandExecutor {

    private final LilypadUtilities plugin;

    public GlobalListCommad(LilypadUtilities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can not be used in the console!");
            return false;
        }

        List<String> servers = new ArrayList<>();

        for (Map.Entry<String, NetworkServer> item : plugin.getNetwork().getNetworkServers().entrySet()) {
            if (item.getValue().isOffline()) {
                servers.add("&c" + item.getValue().getName());
                continue;
            }
            servers.add(String.format("&a%s &7(&f%s&7)", item.getValue().getName(), item.getValue().getPlayers()));
        }

        plugin.getEnvoyer().sendMessage(sender, "&6&l[&e+&6&l] &6Global Server List");
        plugin.getEnvoyer().sendMessage(sender, String.join(
                "&e, ", servers.stream().sorted().collect(Collectors.toList())
        ));
        return true;
    }
}
