package server.state;

import shared.communication.SocketCommunicationListener;

public interface ApplicationState extends SocketCommunicationListener {
    void next();
}
