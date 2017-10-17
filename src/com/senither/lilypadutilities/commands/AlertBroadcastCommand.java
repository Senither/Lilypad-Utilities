package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.Permissions;
import com.senither.lilypadutilities.contracts.commands.Command;
import com.senither.lilypadutilities.network.RequestType;
import org.bukkit.command.CommandSender;

public class AlertBroadcastCommand extends Command {

    public AlertBroadcastCommand(LilypadUtilities plugin) {
        super(plugin, Permissions.STAFF_ALERT);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cYou must include the &nmessage&c you want to send.");
            return false;
        }

        plugin.getNetwork().messageRequest(RequestType.ALERT_BROADCAST, String.join(" ", args));
        return true;
    }
}
