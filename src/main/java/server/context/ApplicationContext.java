package server.context;

import shared.communication.SocketCommunicationBus;
import server.state.ApplicationState;

public interface ApplicationContext {
    SocketCommunicationBus getCommunicationBus();
    ApplicationState getApplicationState();
    void setApplicationState(ApplicationState applicationState);
    String getLoggedInUserId();
    void setLoggedInUserId(String loggedInUserId);
}
