package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.Permissions;
import com.senither.lilypadutilities.network.RequestType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertBroadcastCommand implements CommandExecutor {

    private final LilypadUtilities plugin;

    public AlertBroadcastCommand(LilypadUtilities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(Permissions.STAFF_ALERT)) {
                plugin.getEnvoyer().missingPermission(player, Permissions.STAFF_ALERT);
                return false;
            }
        }

        if (args.length == 0) {
            plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cYou must include the &nmessage&c you want to send.");
            return false;
        }

        plugin.getNetwork().messageRequest(RequestType.ALERT_BROADCAST, String.join(" ", args));

        return true;
    }
}
