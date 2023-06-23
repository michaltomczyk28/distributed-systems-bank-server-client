package server;

import server.state.ClientStateFactory;

import java.net.Socket;

public class ClientConnectionThread extends ConnectionThread {
    public ClientConnectionThread(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    protected ClientStateFactory createStateFactory() {
        return new ClientStateFactory();
    }
}
