package shared.state;

import shared.communication.SocketCommunicationListener;

public interface ConnectionState extends SocketCommunicationListener {
    void next();
}
