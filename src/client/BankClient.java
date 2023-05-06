package client;

import java.net.Socket;

public class BankClient {
    public static void main(String[] args) {
        Socket serverSocket = null;

        try {
            serverSocket = new Socket("localhost", 8081);
        } catch (Exception e) {
            System.out.println("Could not establish a connection with server.");
            System.exit(-1);
        }

        System.out.println("Connection with server has been established: " + serverSocket);
    }
}
