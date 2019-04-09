package ru.otus.service;

import ru.otus.domain.User;

import java.util.List;

public interface FrontendService {

    void saveUser(User user);

    void deleteUser(User user);

    void getUsers();

    void sendUsers(List<User> users);
}
