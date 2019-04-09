package ru.otus.socket;

import ru.otus.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

public class DbSocketMsgWorker extends SocketMsgWorker {
    private final Socket socket;

    DbSocketMsgWorker(String host, int port, int localPort) throws IOException {
        this(new Socket(host, port, null, localPort));
    }

    private DbSocketMsgWorker(Socket socket) {
        super(socket);
        this.socket = socket;
    }

    public void close() {
        try {
            super.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
