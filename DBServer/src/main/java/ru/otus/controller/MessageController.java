package ru.otus.controller;

import com.google.gson.Gson;
import ru.otus.app.Address;
import ru.otus.app.Commands;
import ru.otus.domain.User;
import ru.otus.messages.Msg;
import ru.otus.service.DbService;

public class MessageController {
    private final static Gson gson = new Gson();

    public Msg saveUser(DbService dbService, Msg msg) {
        User user = gson.fromJson(msg.getBody(), User.class);
        dbService.save(user);
        return new Msg(Address.DBSERVER, Address.FRONTEND, Commands.GET_USERS, gson.toJson(dbService.getUsers()));
    }

    public Msg getUsers(DbService dbService) {
        return new Msg(Address.DBSERVER, Address.FRONTEND, Commands.GET_USERS, gson.toJson(dbService.getUsers()));
    }

    public Msg deleteUser(DbService dbService, Msg msg) {
        User user = gson.fromJson(msg.getBody(), User.class);
        dbService.deleteUser(user);
        return new Msg(Address.DBSERVER, Address.FRONTEND, Commands.GET_USERS, gson.toJson(dbService.getUsers()));
    }
}
