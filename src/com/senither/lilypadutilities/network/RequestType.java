package com.senither.lilypadutilities.network;

public enum RequestType {

    PLAYER_UPDATE("lily-utils.player-update"),
    STAFF_CHAT("lily-utils.staff-chat"),
    ALERT_BROADCAST("lily-utils.alert-broadcast");

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
