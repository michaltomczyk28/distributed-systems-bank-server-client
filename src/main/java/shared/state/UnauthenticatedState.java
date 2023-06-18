package shared.state;

import server.context.ApplicationContext;
import server.repository.UserRepository;
import shared.communication.SocketCommunicationBus;

public class UnauthenticatedState implements ApplicationState {
    private ApplicationContext applicationContext;
    private SocketCommunicationBus communicationBus;
    private UserRepository userRepository;
    private String username;
    private String password;
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
            return;
        }

        this.isPendingForResponse = true;
    }

    private void attemptLogin() {
        String userId = this.userRepository.authenticateUser(this.username, this.password);

        if(userId != null) {
            this.communicationBus.sendMessage("\nYou've been authenticated successfully!");

            this.applicationContext.setLoggedInUserId(userId);
            this.applicationContext.setApplicationState(new CommandExecutionState(this.applicationContext));

            return;
        }

        this.communicationBus.sendMessage("Invalid credentials. Try again!");
        this.username = null;
        this.password = null;
        this.isPendingForResponse = false;
    }
}
