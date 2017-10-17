package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.Permissions;
import com.senither.lilypadutilities.contracts.commands.Command;
import com.senither.lilypadutilities.network.NetworkManager;
import com.senither.lilypadutilities.network.RequestType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand extends Command {

    public StaffChatCommand(LilypadUtilities plugin) {
        super(plugin, Permissions.STAFF_CHAT);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cYou must include the &nmessage&c you want to send.");
            return false;
        }

        plugin.getNetwork().messageRequest(RequestType.STAFF_CHAT, String.join(NetworkManager.SEPARATOR,
                (sender instanceof Player) ? ((Player) sender).getPlayer().getName() : "CONSOLE", String.join(" ", args)
        ));
        return true;
    }
}
