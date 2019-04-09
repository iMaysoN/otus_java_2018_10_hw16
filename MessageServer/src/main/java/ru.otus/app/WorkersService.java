package ru.otus.app;

import ru.otus.channel.MsgWorker;
import ru.otus.messages.Msg;

import java.util.List;

public interface WorkersService {
    void addWorker(Address address, MsgWorker worker);

    List<MsgWorker> getWorkers();

    void deleteWorker(MsgWorker worker);

    void sendMessage(MsgWorker thisWorker, Msg msg);
}
