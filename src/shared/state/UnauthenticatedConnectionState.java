package shared.state;

import shared.communication.SocketCommunicationBus;

public class UnauthenticatedConnectionState implements ConnectionState {
    private SocketCommunicationBus communicationBus;
    private String username;
    private String password;
    private boolean isPendingForResponse = false;

    public UnauthenticatedConnectionState(SocketCommunicationBus communicationBus) {
        this.communicationBus = communicationBus;
        this.communicationBus.registerListener(this);
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
            this.communicationBus.sendMessage("You've been authenticated successfully!");
        }

        this.isPendingForResponse = true;
    }
}
