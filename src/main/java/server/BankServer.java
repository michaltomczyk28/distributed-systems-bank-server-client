package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

interface ConnectionThreadFunction {
    ConnectionThread createConnectionThread(Socket socket);
}

public class BankServer {
    public static void main(String[] args) {
        Thread clientServerThread = createThreadForServerSocket("client server", 8081, ClientConnectionThread::new);
        Thread adminServerThread = createThreadForServerSocket("admin server", 8082, AdminConnectionThread::new);

        clientServerThread.start();
        adminServerThread.start();
    }

    private static Thread createThreadForServerSocket(String name, int port, ConnectionThreadFunction connectionThreadFunction) {
        return new Thread(
            () -> {
                ServerSocket serverSocket = null;
                Socket socket = null;

                try {
                    serverSocket = new ServerSocket(port);
                } catch (IOException e) {
                    System.out.println(name + "> couldn't create a server socket");
                    return;
                }

                System.out.println(name + "> server socket has been initialized: " + serverSocket);

                while (true) {
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        System.out.println(name + "> IOException: " + e.getMessage());
                    }

                    System.out.println(name + "> new connection: " + socket);

                    ConnectionThread connectionThread = connectionThreadFunction.createConnectionThread(socket);
                    new Thread(connectionThread).start();
                }
            }
        );

    }
}
