package ru.otus.app;

public enum Commands {
    SAVE_USER("SaveUser"),
    READ_USERS("GetUsers"),
    DELETE_USERS("DeleteUser");

    private final String commandClass;

    Commands(String commandClass) {
        this.commandClass = commandClass;
    }

    public String getCommandClass() {
        return commandClass;
    }
}
