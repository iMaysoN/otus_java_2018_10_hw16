package ru.otus.app;

import java.util.Arrays;

public enum Address {
    FRONTEND(48654, 48999),
    DBSERVER(49001, 49150);

    private final int fromPort;
    private final int tillPort;

    Address(int fromPort, int tillPort) {
        this.fromPort = fromPort;
        this.tillPort = tillPort;
    }

    public int getFromPort() {
        return fromPort;
    }

    public int getTillPort() {
        return tillPort;
    }

    public static Address getAddress(int port) {
        return Arrays.stream(Address.values())
                .filter(address -> address.getTillPort() > port && address.getFromPort() <= port)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown port"));
    }
}
