package shared.state;

import server.repository.UserRepository;
import shared.communication.SocketCommunicationBus;

public class UnauthenticatedConnectionState implements ConnectionState {
    private SocketCommunicationBus communicationBus;
    private UserRepository userRepository;
    private String username;
    private String password;
    private boolean isPendingForResponse = false;

    public UnauthenticatedConnectionState(SocketCommunicationBus communicationBus) {
        this.communicationBus = communicationBus;
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
            return;
        }

        this.isPendingForResponse = true;
    }

    private void attemptLogin() {
        boolean result = this.userRepository.canAuthenticate(this.username, this.password);

        if(result) {
            this.communicationBus.sendMessage("You've been authenticated successfully!");
            this.isPendingForResponse = true;
            return;
        }

        this.communicationBus.sendMessage("Invalid credentials. Try again!");
        this.username = null;
        this.password = null;
        this.isPendingForResponse = false;
    }
}
