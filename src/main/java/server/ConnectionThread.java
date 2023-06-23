package server;

import server.context.ApplicationContext;
import server.context.ApplicationContextImpl;
import server.state.ApplicationState;
import server.state.factory.StateFactory;
import server.state.UnauthenticatedState;
import shared.communication.SocketCommunicationBus;

import java.net.Socket;

public abstract class ConnectionThread implements Runnable {
    private Socket clientSocket;
    private ApplicationContext applicationContext;

    public ConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            this.applicationContext = new ApplicationContextImpl(
                    new SocketCommunicationBus(clientSocket),
                    this.createStateFactory()
            );

            SocketCommunicationBus communicationBus = this.applicationContext.getCommunicationBus();
            StateFactory stateFactory = this.applicationContext.getStateFactory();

            UnauthenticatedState unauthenticatedState = stateFactory.createUnauthenticatedState(this.applicationContext);
            this.applicationContext.setApplicationState(unauthenticatedState);

            while(true) {
                ApplicationState state = this.applicationContext.getApplicationState();
                state.next();

                communicationBus.handleIncomingInput();
            }
        } catch (Exception ignored) {}
    }

    protected abstract StateFactory createStateFactory();
}
