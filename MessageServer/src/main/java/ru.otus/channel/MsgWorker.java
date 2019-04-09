package ru.otus.channel;

import ru.otus.messages.Msg;

import java.io.Closeable;

public interface MsgWorker extends Closeable {
    void send(Msg msg);

    Msg poll();

    Msg take() throws InterruptedException;

    void close();

    boolean isConnected();

    int getOutputSize();
}
