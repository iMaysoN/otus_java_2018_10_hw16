package ru.otus;

import ru.otus.app.Address;
import ru.otus.runner.ProcessRunnerImpl;
import ru.otus.server.SocketMsgServer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        File frontend = new File("../hw16/Frontend/target/frontend-1.0.jar");
        File dbServer = new File("../hw16/DBServer/target/db-server-1.0.jar");
        if (frontend.exists() && dbServer.exists()) {
            System.out.println(frontend.getPath());
            new ServerMain().start(frontend, dbServer);
        } else {
            if (!frontend.exists())
                System.out.println("Can't find " + frontend.getPath());
            if (!dbServer.exists())
                System.out.println("Can't find " + dbServer.getPath());
        }
    }

    private void start(File frontend, File dbServer) throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        String firstDb = String.format("java -jar %s %s", dbServer.getPath(), Address.DBSERVER.getFromPort());
        String secondDb = String.format("java -jar %s %s", dbServer.getPath(), Address.DBSERVER.getFromPort() + 1);
        startClient(executorService, firstDb);
        startClient(executorService, secondDb);

        String firstFe = String.format("java -jar %s %s --server.port=8080", frontend.getPath(), Address.FRONTEND.getFromPort());
        String secondFe = String.format("java -jar %s %s --server.port=8090", frontend.getPath(), Address.FRONTEND.getFromPort() + 1);
        startClient(executorService, firstFe);
        startClient(executorService, secondFe);

        SocketMsgServer server = new SocketMsgServer();
        server.start();
        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
                    System.out.println("Trying execute: " + command);
                    try {
                        new ProcessRunnerImpl().start(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },
                7, TimeUnit.SECONDS);
    }

}
