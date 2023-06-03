package server;

import shared.communication.SocketCommunicationBus;
import shared.communication.SocketCommunicationListener;
import shared.state.ConnectionState;
import shared.state.UnauthenticatedConnectionState;

import java.net.Socket;

public class ClientConnectionThread implements Runnable, SocketCommunicationListener {
    private Socket clientSocket;
    private SocketCommunicationBus communicationBus;
    private ConnectionState connectionState;

    public ClientConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            this.communicationBus = new SocketCommunicationBus(clientSocket);
            this.communicationBus.registerListener(this);
            this.communicationBus.sendMessage("--- BANK SERVER ---");

            this.connectionState = new UnauthenticatedConnectionState(this.communicationBus);

            while(true) {
                this.connectionState.next();
                this.communicationBus.handleIncomingInput();
            }
        } catch (Exception ignored) {}
    }

    @Override
    public void onInput(String input) {
        System.out.println("FROM CLIENT: " + input);
    }
}
