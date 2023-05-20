package server;

import shared.SocketCommunicationBus;
import shared.SocketCommunicationListener;

import java.net.Socket;

public class ClientConnectionThread implements Runnable, SocketCommunicationListener {
    private Socket clientSocket;
    private SocketCommunicationBus communicationBus;

    public ClientConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            this.communicationBus = new SocketCommunicationBus(clientSocket);
            this.communicationBus.registerListener(this);
            this.communicationBus.sendMessage("--- BANK SERVER ---");

            while(true) {
                this.communicationBus.handleIncomingInput();
            }
        } catch (Exception ignored) {}
    }

    @Override
    public void onInput(String input) {
        System.out.println("FROM CLIENT: " + input);
    }
}
