package com.senither.lilypadutilities.network;

import com.senither.lilypadutilities.LilypadUtilities;
import com.senither.lilypadutilities.Permissions;
import com.senither.lilypadutilities.commands.FindCommand;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResult;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NetworkManager implements Runnable {

    public static final String SEPARATOR = "\000";

    private final LilypadUtilities plugin;
    private final HashMap<String, NetworkServer> servers;

    public NetworkManager(LilypadUtilities plugin) {
        this.plugin = plugin;
        this.servers = new HashMap<>();

        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 60L, 20L);
    }

    @EventListener
    public void onLilyMessage(MessageEvent event) {
        if (!servers.containsKey(event.getSender())) {
            servers.put(event.getSender(), new NetworkServer(event.getSender()));
        }

        try {
            RequestType requestType = RequestType.fromChannel(event.getChannel());
            if (requestType == null) {
                return;
            }

            String[] args = event.getMessageAsString().split(SEPARATOR);

            switch (requestType) {
                case PLAYER_UPDATE:
                    servers.get(event.getSender()).setPlayers(Integer.parseInt(args[0], 10));
                    break;

                case ALERT_BROADCAST:
                    plugin.getEnvoyer().broadcast("&6[&eAlert&6] &f" + String.join(" ", args));
                    break;

                case STAFF_CHAT:
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (player.hasPermission(Permissions.STAFF_CHAT)) {
                            plugin.getEnvoyer().sendMessage(player, String.format("&8[&cStaffChat&8] &e%s &6(&e%s&6)&e: &f%s",
                                    args[0], event.getSender(), String.join(" ", Arrays.copyOfRange(args, 1, args.length))
                            ));
                        }
                    }
                    break;

                case FIND_PLAYER:
                    String playerName = args[0];
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (player.getName().equalsIgnoreCase(playerName)) {
                            plugin.getNetwork().messageRequest(RequestType.FOUND_PLAYER, player.getName(), event.getSender());
                            break;
                        }
                    }
                    break;

                case FOUND_PLAYER:
                    String foundPlayerName = args[0];
                    if (!FindCommand.LISTENERS.containsKey(foundPlayerName.toLowerCase())) {
                        break;
                    }

                    String foundMessage = String.format("&6[&eFound&6] %s &ewas found on &6%s", foundPlayerName, event.getSender());
                    for (String playerListener : FindCommand.LISTENERS.get(foundPlayerName.toLowerCase())) {
                        Player player = Bukkit.getPlayer(playerListener);
                        if (player != null) plugin.getEnvoyer().sendMessage(player, foundMessage);
                    }
                    FindCommand.LISTENERS.remove(foundPlayerName.toLowerCase());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        messageRequest(RequestType.PLAYER_UPDATE, "" + plugin.getServer().getOnlinePlayers().size());
    }

    public HashMap<String, NetworkServer> getNetworkServers() {
        return servers;
    }

    public void messageRequest(RequestType requestType, String message, List<String> servers) {
        try {
            this.reconnectToNetwork();

            MessageRequest request = new MessageRequest(servers, requestType.getChannel(), message);

            plugin.getConnect().request(request);
        } catch (UnsupportedEncodingException | RequestException ex) {
            throw new RuntimeException("Error while sending a message request." + ex);
        } catch (Throwable ex) {
            throw new RuntimeException("Error whilst redirecting a player. The connection seems to have been closed and won't open again." + ex);
        }
    }

    public void messageRequest(RequestType requestType, String message, String... servers) {
        messageRequest(requestType, message, Arrays.asList(servers));
    }

    public void teleportRequest(String player, String server) {
        try {
            this.reconnectToNetwork();

            RedirectRequest request = new RedirectRequest(server, player);
            FutureResult future = plugin.getConnect().request(request);

            future.await().getStatusCode();
        } catch (RequestException | InterruptedException ex) {
            throw new RuntimeException("Error whilst redirecting a player." + ex);
        } catch (Throwable ex) {
            throw new RuntimeException("Error whilst redirecting a player. The connection seems to have been closed and won't open again." + ex);
        }
    }

    private void reconnectToNetwork() throws Throwable {
        if (!plugin.getConnect().isConnected()) {
            plugin.getConnect().connect();
        }
    }
}