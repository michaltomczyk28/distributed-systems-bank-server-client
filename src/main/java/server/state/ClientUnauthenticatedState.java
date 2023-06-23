package server.state;

import server.context.ApplicationContext;

public class ClientUnauthenticatedState extends UnauthenticatedState {

    public ClientUnauthenticatedState(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    protected void attemptLogin() {
        String userId = this.userRepository.authenticateUser(this.username, this.password);

        if(userId != null) {
            this.communicationBus.sendMessage("\nYou've been authenticated successfully!");

            this.applicationContext.setLoggedInUserId(userId);
            this.applicationContext.setApplicationState(new CommandExecutionState(this.applicationContext));

            return;
        }

        this.communicationBus.sendMessage("Invalid credentials. Try again!");
    }

}
