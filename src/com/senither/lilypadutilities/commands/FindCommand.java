package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.Permissions;
import com.senither.lilypadutilities.contracts.commands.Command;
import com.senither.lilypadutilities.network.RequestType;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindCommand extends Command {

    public static final Map<String, List<String>> LISTENERS = new HashMap<>();

    public FindCommand(LilypadUtilities plugin) {
        super(plugin, Permissions.STAFF_FIND);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cYou must include the &nname&c of the player you want to find.");
            return false;
        }

        String playerName = args[0].toLowerCase();
        if (!LISTENERS.containsKey(playerName)) {
            LISTENERS.put(playerName, new ArrayList<>());
        }

        LISTENERS.get(playerName).add(sender.getName());
        plugin.getNetwork().messageRequest(RequestType.FIND_PLAYER, playerName);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (LISTENERS.containsKey(playerName)) {
                LISTENERS.remove(playerName);
                plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cFained to find any player online called &4" + args[0]);
            }
        }, 50);
        return true;
    }
}
