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


            StateFactory stateFactory = this.applicationContext.getStateFactory();
            CommandExecutionState commandExecutionState = stateFactory.createCommandExecutionState(this.applicationContext);

            this.applicationContext.setApplicationState(commandExecutionState);
            this.applicationContext.setLoggedInUserId(userId);

            return;
        }

        this.communicationBus.sendMessage("Invalid credentials. Try again!");
    }

}
