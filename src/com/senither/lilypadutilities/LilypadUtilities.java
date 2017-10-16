package com.senither.lilypadutilities;

import com.senither.lilypadutilities.commands.AlertBroadcastCommand;
import com.senither.lilypadutilities.commands.GlobalListCommad;
import com.senither.lilypadutilities.commands.StaffChatCommand;
import com.senither.lilypadutilities.network.NetworkManager;
import com.senither.lilypadutilities.utils.Envoyer;
import lilypad.client.connect.api.Connect;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LilypadUtilities extends JavaPlugin {

    public static final Logger LOGGER = Logger.getLogger("LilyPad-Utilities");

    private Envoyer envoyer;
    private Connect connect;
    private NetworkManager network;

    @Override
    public void onEnable() {
        Plugin plugin = getServer().getPluginManager().getPlugin("LilyPad-Connect");
        if (plugin == null) {
            setEnabled(false);
            LOGGER.log(Level.SEVERE, "Failed to find dependency LilyPad-Connect, disabling...");
            return;
        }

        envoyer = new Envoyer();

        getCommand("glist").setExecutor(new GlobalListCommad(this));
        getCommand("staffchat").setExecutor(new StaffChatCommand(this));
        getCommand("alert").setExecutor(new AlertBroadcastCommand(this));

        connect = getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        connect.registerEvents(network = new NetworkManager(this));
    }

    @Override
    public void onDisable() {
        connect.unregisterEvents(network);
    }

    public Envoyer getEnvoyer() {
        return envoyer;
    }

    public Connect getConnect() {
        return connect;
    }

    public NetworkManager getNetwork() {
        return network;
    }
}
