package com.senither.lilypadutilities.network;

public enum RequestType {

    PLAYER_UPDATE("lily-utils.player-update");

    private final String channel;

    RequestType(String channel) {
        this.channel = channel;
    }

    public static RequestType fromChannel(String channel) {
        for (RequestType type : values()) {
            if (type.getChannel().equals(channel)) {
                return type;
            }
        }
        return null;
    }

    public String getChannel() {
        return channel;
    }
}
