package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.Permissions;
import com.senither.lilypadutilities.network.NetworkServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCommand implements CommandExecutor {

    private final LilypadUtilities plugin;

    public SendCommand(LilypadUtilities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(Permissions.STAFF_SEND)) {
                plugin.getEnvoyer().missingPermission(player, Permissions.STAFF_SEND);
                return false;
            }
        }

        if (args.length < 2) {
            plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cRequired arguments missing.");
            plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cExample: &4/&csend &4[&cplayer&4] &4[&cserver&4]");
            return false;
        }

        Player player = plugin.getServer().getPlayer(args[0]);
        if (player == null || !player.isOnline()) {
            plugin.getEnvoyer().sendMessage(sender, String.format("&4[&cError&4] &cThere are no player online with the name &4%s.", args[0]));
            return false;
        }

        NetworkServer server = getNetworkServerFrom(args[1]);
        if (server == null || server.isOffline()) {
            plugin.getEnvoyer().sendMessage(sender, String.format("&4[&cError&4] &cThe &4%s&c server is either non-existent or are currently offline.", args[1]));
            return false;
        }

        plugin.getNetwork().teleportRequest(player.getName(), server.getName());
        plugin.getEnvoyer().sendMessage(sender, String.format("&2[&aSuccess&2] &2%s&a has been moved to the &2%s&a server.", player.getName(), server.getName()));
        return true;
    }

    private NetworkServer getNetworkServerFrom(String name) {
        for (NetworkServer networkServer : plugin.getNetwork().getNetworkServers().values()) {
            if (networkServer.getName().equalsIgnoreCase(name)) {
                return networkServer;
            }
        }
        return null;
    }
}