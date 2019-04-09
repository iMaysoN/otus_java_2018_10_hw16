package ru.otus.socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import ru.otus.app.Address;
import ru.otus.app.Commands;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.domain.User;
import ru.otus.messages.Msg;
import ru.otus.service.FrontendService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.otus.server.ServerConfig.HOST_NAME;
import static ru.otus.server.ServerConfig.MESSAGE_SERVER_PORT;

@Service
public class FrontendSocketExecutor {

    private SocketMsgWorker clientWorker;
    private FrontendService frontendService;

    public void init(int localPort, FrontendService frontendService) throws IOException {
        this.frontendService = frontendService;
        clientWorker = new FrontendMsgWorker(HOST_NAME, MESSAGE_SERVER_PORT, localPort);
        clientWorker.init();

        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        threadExecutor.submit(() -> {
            try {
                while (true) {
                    Msg msg = clientWorker.take();
                    getUsers(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadExecutor.shutdown();
    }

    private void getUsers(Msg msg) {
        if (msg.getCommand() == Commands.GET_USERS) {
            List<User> users = new Gson().fromJson(msg.getBody(), new TypeToken<List<User>>() {
            }.getType());
            frontendService.sendUsers(users);
        }
    }

    public void sendMessage(Commands command, String body) {
        Msg msg = new Msg(Address.FRONTEND, Address.DBSERVER, command, body);
        clientWorker.send(msg);
    }
}
