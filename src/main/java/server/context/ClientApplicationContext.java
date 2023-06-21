package server.context;

import shared.communication.SocketCommunicationBus;
import server.state.ApplicationState;

public class ClientApplicationContext implements ApplicationContext {
    private SocketCommunicationBus communicationBus;
    private ApplicationState applicationState;
    private String loggedInUserId;

    public ClientApplicationContext(SocketCommunicationBus communicationBus) {
        this.communicationBus = communicationBus;
    }

    public SocketCommunicationBus getCommunicationBus() {
        return this.communicationBus;
    }

    public ApplicationState getApplicationState() {
        return this.applicationState;
    }

    public void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    public String getLoggedInUserId() {
        return this.loggedInUserId;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }
}
