package server;

import server.context.ClientApplicationContext;
import shared.communication.SocketCommunicationBus;
import shared.state.ApplicationState;
import shared.state.UnauthenticatedState;

import java.net.Socket;

public class ClientConnectionThread implements Runnable {
    private Socket clientSocket;
    private ClientApplicationContext applicationContext;

    public ClientConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            this.applicationContext = new ClientApplicationContext(
                    new SocketCommunicationBus(clientSocket)
            );

            SocketCommunicationBus communicationBus = this.applicationContext.getCommunicationBus();

            this.applicationContext.setApplicationState(
                    new UnauthenticatedState(this.applicationContext)
            );

            while(true) {
                ApplicationState state = this.applicationContext.getApplicationState();
                state.next();

                communicationBus.handleIncomingInput();
            }
        } catch (Exception ignored) {}
    }
}
