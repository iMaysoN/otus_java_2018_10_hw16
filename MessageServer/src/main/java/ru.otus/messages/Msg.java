package ru.otus.messages;

import ru.otus.app.Address;
import ru.otus.app.Commands;

public class Msg {
    private final Address from;
    private final Address to;
    private final Commands command;
    private final String body;

    public Msg(Address from, Address to,Commands command, String body) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.command = command;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public Commands getCommand() {
        return command;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "from=" + from +
                ", to=" + to +
                ", command=" + command +
                ", body='" + body + '\'' +
                '}';
    }
}
