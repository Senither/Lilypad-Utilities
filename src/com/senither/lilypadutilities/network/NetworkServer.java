package com.senither.lilypadutilities.network;

public class NetworkServer {

    private final String name;
    private long lastupdated;
    private long players;

    public NetworkServer(String name) {
        this.name = name;
        this.players = 0;
    }

    public String getName() {
        return name;
    }

    public long getLastupdated() {
        return lastupdated;
    }

    public long getPlayers() {
        return players;
    }

    public void setPlayers(long players) {
        this.players = players;
        this.lastupdated = System.currentTimeMillis();
    }

    public boolean isOffline() {
        return (System.currentTimeMillis() - 5000) > lastupdated;
    }
}
