package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.Permissions;
import com.senither.lilypadutilities.network.NetworkManager;
import com.senither.lilypadutilities.network.RequestType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private final LilypadUtilities plugin;

    public StaffChatCommand(LilypadUtilities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String playerName = "CONSOLE";
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(Permissions.STAFF_CHAT)) {
                plugin.getEnvoyer().missingPermission(player, Permissions.STAFF_CHAT);
                return false;
            }

            playerName = player.getName();
        }

        if (args.length == 0) {
            plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cYou must include the &nmessage&c you want to send.");
            return false;
        }

        plugin.getNetwork().messageRequest(RequestType.STAFF_CHAT, String.join(NetworkManager.SEPARATOR,
                playerName, String.join(" ", args)
        ));

        return true;
    }
}
