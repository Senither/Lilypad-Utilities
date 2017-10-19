package com.senither.lilypadutilities.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.Permissions;
import com.senither.lilypadutilities.contracts.commands.Command;
import com.senither.lilypadutilities.network.NetworkManager;
import com.senither.lilypadutilities.network.RequestType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class StaffChatCommand extends Command implements Listener {

    private static List<String> toggledPlayers = new ArrayList<>();

    public StaffChatCommand(LilypadUtilities plugin) {
        super(plugin, Permissions.STAFF_CHAT);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                plugin.getEnvoyer().sendMessage(sender, "&4[&cError&4] &cYou must include the &nmessage&c you want to send.");
                return false;
            }

            String uniqueId = ((Player) sender).getPlayer().getUniqueId().toString();
            plugin.getEnvoyer().sendMessage(sender, plugin.getConfig().getString("commands.staffchat.toggle")
                    .replace("{status}", toggledPlayer(uniqueId) ? "ON" : "OFF"));
            return true;
        }

        plugin.getNetwork().messageRequest(RequestType.STAFF_CHAT, String.join(NetworkManager.SEPARATOR,
                (sender instanceof Player) ? ((Player) sender).getPlayer().getName() : "CONSOLE", String.join(" ", args)
        ));
        return true;
    }

    private boolean toggledPlayer(String uniqueId) {
        if (toggledPlayers.contains(uniqueId)) {
            toggledPlayers.remove(uniqueId);
            return false;
        }

        toggledPlayers.add(uniqueId);
        return true;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onStaffChat(AsyncPlayerChatEvent event) {
        if (toggledPlayers.contains(event.getPlayer().getUniqueId().toString())) {
            plugin.getNetwork().messageRequest(RequestType.STAFF_CHAT, String.join(NetworkManager.SEPARATOR,
                    event.getPlayer().getName(), event.getMessage()
            ));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onStaffLeave(PlayerQuitEvent event) {
        if (toggledPlayers.contains(event.getPlayer().getUniqueId().toString())) {
            toggledPlayers.remove(event.getPlayer().getUniqueId().toString());
        }
    }
}
