package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.contracts.commands.Command;
import com.senither.lilypadutilities.network.NetworkServer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GlobalListCommad extends Command {

    public GlobalListCommad(LilypadUtilities plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        List<String> servers = new ArrayList<>();

        long totalPlayers = 0;
        for (Map.Entry<String, NetworkServer> item : plugin.getNetwork().getNetworkServers().entrySet()) {
            if (item.getValue().isOffline()) {
                servers.add("&c" + item.getValue().getName());
                continue;
            }
            totalPlayers += item.getValue().getPlayers();
            servers.add(String.format("&a%s &7(&f%s&7)", item.getValue().getName(), item.getValue().getPlayers()));
        }

        plugin.getEnvoyer().sendMessage(sender, "&6&l[&e+&6&l] &6Global Server List");
        plugin.getEnvoyer().sendMessage(sender, String.format("&eThere are a total of &6%s &eplayers online.", totalPlayers));
        plugin.getEnvoyer().sendMessage(sender, String.join(
                "&e, ", servers.stream().sorted().collect(Collectors.toList())
        ));
        return true;
    }
}
