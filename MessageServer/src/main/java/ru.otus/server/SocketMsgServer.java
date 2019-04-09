package ru.otus.server;

import ru.otus.app.Address;
import ru.otus.app.WorkersService;
import ru.otus.app.WorkersServiceImpl;
import ru.otus.channel.MsgWorker;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.messages.Msg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketMsgServer {
    private static final int THREADS_NUMBER = 1;
    private static final int PORT = ServerConfig.MESSAGE_SERVER_PORT;

    private final ExecutorService executor;
    private final WorkersService workersService;

    public SocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workersService = new WorkersServiceImpl();
    }

    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Socket message server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept();
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                Address address = Address.getAddress(socket.getPort());
                workersService.addWorker(address, worker);
                System.out.println("New worker added for address: " + address);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (MsgWorker worker : workersService.getWorkers()) {
                if (worker.isConnected()) {
                    Msg msg = worker.poll();
                    while (msg != null) {
                        System.out.println("Message: " + msg);
                        workersService.sendMessage(worker, msg);
                        msg = worker.poll();
                    }
                } else {
                    workersService.deleteWorker(worker);
                }
            }
        }
    }
}
