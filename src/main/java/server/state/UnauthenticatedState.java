package server.state;

import server.context.ApplicationContext;
import server.repository.UserRepository;
import shared.communication.SocketCommunicationBus;

public abstract class UnauthenticatedState implements ApplicationState {
    protected ApplicationContext applicationContext;
    protected SocketCommunicationBus communicationBus;
    protected UserRepository userRepository;
    protected String username;
    protected String password;

    private boolean isPendingForResponse = false;

    public UnauthenticatedState(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        this.communicationBus = applicationContext.getCommunicationBus();
        this.communicationBus.registerListener(this);

        this.userRepository = new UserRepository();
    }

    @Override
    public void onInput(String input) {
        if(username == null) {
            this.username = input;
        } else if (password == null) {
            this.password = input;
        }

        this.isPendingForResponse = false;
    }
    @Override
    public void next() {
        if(this.isPendingForResponse) {
            return;
        }

        if(username == null) {
            this.communicationBus.sendMessage("Username: ");
        } else if (password == null) {
            this.communicationBus.sendMessage("Password: ");
        } else {
            this.attemptLogin();
            this.resetInternalState();

            return;
        }

        this.isPendingForResponse = true;
    }

    protected abstract void attemptLogin();

    private void resetInternalState() {
        this.username = null;
        this.password = null;
        this.isPendingForResponse = false;
    }
}
