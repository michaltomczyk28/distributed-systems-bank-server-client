package server.context;

import server.state.StateFactory;
import shared.communication.SocketCommunicationBus;
import server.state.ApplicationState;

public class ClientApplicationContext implements ApplicationContext {
    private SocketCommunicationBus communicationBus;
    private StateFactory stateFactory;
    private ApplicationState applicationState;
    private String loggedInUserId;

    public ClientApplicationContext(SocketCommunicationBus communicationBus, StateFactory stateFactory) {
        this.communicationBus = communicationBus;
        this.stateFactory = stateFactory;
    }

    @Override
    public SocketCommunicationBus getCommunicationBus() {
        return this.communicationBus;
    }

    @Override
    public StateFactory getStateFactory() {
        return this.stateFactory;
    }

    @Override
    public ApplicationState getApplicationState() {
        return this.applicationState;
    }

    @Override
    public void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    @Override
    public String getLoggedInUserId() {
        return this.loggedInUserId;
    }

    @Override
    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }
}
