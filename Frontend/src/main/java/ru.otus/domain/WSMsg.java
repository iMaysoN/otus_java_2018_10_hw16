package ru.otus.domain;

import java.util.List;

public class WSMsg {
    private String method;
    private User user;
    private List<User> users;

    public WSMsg() {
    }

    public WSMsg(String method, User user, List<User> users) {
        this.method = method;
        this.user = user;
        this.users = users;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "WSMsg{" +
                "method='" + method + '\'' +
                ", user=" + user +
                ", users=" + users +
                '}';
    }
}
