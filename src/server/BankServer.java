package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(8081);
        } catch (IOException e) {
            System.out.println("Couldn't create a server socket.");
            System.exit(-1);
        }

        System.out.println("Server socket has been initialized: " + serverSocket);

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }

            System.out.println("New connection: " + socket);

            new Thread(new BankServerThread(socket)).start();
        }
    }
}
