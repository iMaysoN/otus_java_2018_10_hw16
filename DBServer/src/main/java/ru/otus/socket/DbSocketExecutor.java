package ru.otus.socket;

import org.springframework.stereotype.Service;
import ru.otus.app.Commands;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.controller.MessageController;
import ru.otus.messages.Msg;
import ru.otus.service.DbService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.otus.server.ServerConfig.HOST_NAME;
import static ru.otus.server.ServerConfig.MESSAGE_SERVER_PORT;

@Service
public class DbSocketExecutor {
    private final DbService dbService;

    public DbSocketExecutor(DbService dbService) {
        this.dbService = dbService;
    }

    public void init(int localPort) throws Exception {
        SocketMsgWorker clientWorker = new DbSocketMsgWorker(HOST_NAME, MESSAGE_SERVER_PORT, localPort);
        clientWorker.init();

        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        threadExecutor.submit(() -> {
            try {
                while (true) {
                    Msg msg = clientWorker.take();
                    Msg response = execute(msg);
                    clientWorker.send(response);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadExecutor.shutdown();
    }

    private Msg execute(Msg msg) {
        final MessageController msgController = new MessageController();
        if (msg.getCommand() == Commands.SAVE_USER) {
            return msgController.saveUser(dbService, msg);
        } else if (msg.getCommand() == Commands.DELETE_USERS) {
            return msgController.deleteUser(dbService, msg);
        } else {
            return msgController.getUsers(dbService);
        }
    }
}
