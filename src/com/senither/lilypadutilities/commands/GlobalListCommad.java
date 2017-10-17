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
                servers.add(plugin.getConfig().getString("commands.glist.servers.offline")
                        .replace("{server}", item.getValue().getName()));
                continue;
            }
            totalPlayers += item.getValue().getPlayers();
            servers.add(plugin.getConfig().getString("commands.glist.servers.online")
                    .replace("{server}", item.getValue().getName())
                    .replace("{players}", "" + item.getValue().getPlayers()));
        }

        plugin.getEnvoyer().sendMessage(sender, plugin.getConfig().getString("commands.glist.title")
                .replace("{total}", "" + totalPlayers));
        plugin.getEnvoyer().sendMessage(sender, String.join(
                plugin.getConfig().getString("commands.glist.separator"),
                servers.stream().sorted().collect(Collectors.toList())
        ));
        return true;
    }
}
