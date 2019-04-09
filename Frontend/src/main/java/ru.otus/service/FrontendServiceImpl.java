package ru.otus.service;

import com.google.gson.Gson;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import ru.otus.app.Commands;
import ru.otus.domain.User;
import ru.otus.domain.WSMsg;
import ru.otus.socket.FrontendSocketExecutor;

import java.util.List;

@Service
public class FrontendServiceImpl implements FrontendService {
    private final FrontendSocketExecutor socketExecutor;
    private final SimpMessageSendingOperations sendingOperations;

    public FrontendServiceImpl(FrontendSocketExecutor socketExecutor, SimpMessageSendingOperations sendingOperations) {
        this.socketExecutor = socketExecutor;
        this.sendingOperations = sendingOperations;
    }

    @Override
    public void saveUser(User user) {
        socketExecutor.sendMessage(Commands.SAVE_USER, new Gson().toJson(user));
    }

    @Override
    public void deleteUser(User user) {
        socketExecutor.sendMessage(Commands.DELETE_USERS, new Gson().toJson(user));
    }

    @Override
    public void getUsers() {
        socketExecutor.sendMessage(Commands.READ_USERS, "");
    }

    @Override
    public void sendUsers(List<User> users) {
        sendingOperations.convertAndSend("/topic/response", new WSMsg("send", null, users));
    }
}
