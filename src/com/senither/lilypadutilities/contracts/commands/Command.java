package com.senither.lilypadutilities.contracts.commands;

import com.senither.lilypadutilities.LilypadUtilities;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor {

    protected final LilypadUtilities plugin;
    protected final String permissionRequirement;

    public Command(LilypadUtilities plugin, String permissionRequirement) {
        this.plugin = plugin;
        this.permissionRequirement = permissionRequirement;
    }

    public Command(LilypadUtilities plugin) {
        this(plugin, null);
    }

    @Override
    public final boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (permissionRequirement != null && (sender instanceof Player)) {
            if (!sender.hasPermission(permissionRequirement)) {
                plugin.getEnvoyer().missingPermission(sender, permissionRequirement);
                return false;
            }
        }

        return onCommand(sender, args);
    }

    public abstract boolean onCommand(CommandSender sender, String[] args);
}
