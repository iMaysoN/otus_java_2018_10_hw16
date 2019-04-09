package ru.otus.service;

import ru.otus.domain.User;

import java.util.List;

public interface DbService {

    List<User> getUsers();

    void save(User user);

    void deleteUser(User user);
}