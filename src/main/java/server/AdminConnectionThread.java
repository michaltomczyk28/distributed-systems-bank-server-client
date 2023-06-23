package server;

import server.state.factory.AdminStateFactory;
import server.state.factory.ClientStateFactory;
import server.state.factory.StateFactory;

import java.net.Socket;

public class AdminConnectionThread extends ConnectionThread {
    public AdminConnectionThread(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    protected StateFactory createStateFactory() {
        return new AdminStateFactory();
    }
}
