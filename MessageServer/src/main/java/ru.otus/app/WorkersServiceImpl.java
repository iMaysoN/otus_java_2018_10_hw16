package ru.otus.app;

import ru.otus.channel.MsgWorker;
import ru.otus.messages.Msg;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class WorkersServiceImpl implements WorkersService {
    private final Map<Address, List<MsgWorker>> workers;

    public WorkersServiceImpl() {
        workers = new HashMap<>();
    }

    public void initWorkersForAddress(Address address) {
        workers.put(address, new CopyOnWriteArrayList<>());
    }

    public void addWorker(Address address, MsgWorker worker) {
        if (!workers.containsKey(address)) {
            initWorkersForAddress(address);
        }
        workers.get(address).add(worker);
    }

    @Override
    public List<MsgWorker> getWorkers() {
        List<MsgWorker> msgWorkers = new ArrayList<>();
        workers.values().forEach(msgWorkers::addAll);
        return msgWorkers;
    }

    @Override
    public void deleteWorker(MsgWorker worker) {
        worker.close();
        workers.values().forEach(workersList -> workersList.remove(worker));
    }

    @Override
    public void sendMessage(MsgWorker thisWorker, Msg msg) {
        if (msg.getFrom() == Address.FRONTEND && msg.getTo() == Address.DBSERVER) {
            workers.get(Address.DBSERVER).stream()
                    .min(Comparator.comparingInt(MsgWorker::getOutputSize))
                    .ifPresent(msgWorker -> msgWorker.send(msg));
        } else {
            workers.get(msg.getTo()).forEach(msgWorker -> {
                if (msgWorker != thisWorker) {
                    msgWorker.send(msg);
                }
            });
        }
    }
}
