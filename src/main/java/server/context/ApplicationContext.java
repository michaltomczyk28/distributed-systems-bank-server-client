package server.context;

import server.state.StateFactory;
import shared.communication.SocketCommunicationBus;
import server.state.ApplicationState;

public interface ApplicationContext {
    SocketCommunicationBus getCommunicationBus();
    StateFactory getStateFactory();
    ApplicationState getApplicationState();
    void setApplicationState(ApplicationState applicationState);
    String getLoggedInUserId();
    void setLoggedInUserId(String loggedInUserId);
}
