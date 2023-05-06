package server;

import java.net.Socket;

public class BankServerThread implements Runnable {
    private Socket clientSocket;

    public BankServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

    }
}
