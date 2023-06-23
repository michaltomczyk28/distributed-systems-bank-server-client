package server.state;

import server.context.ApplicationContext;
import server.state.factory.StateFactory;

public class AdminUnauthenticatedState extends UnauthenticatedState {

    public AdminUnauthenticatedState(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    protected void attemptLogin() {
        String userId = this.userRepository.authenticateAdmin(this.username, this.password);

        if(userId != null) {
            this.communicationBus.sendMessage("\nYou've been authenticated successfully as an administrator!");


            StateFactory stateFactory = this.applicationContext.getStateFactory();
            CommandExecutionState commandExecutionState = stateFactory.createCommandExecutionState(this.applicationContext);

            this.applicationContext.setApplicationState(commandExecutionState);
            this.applicationContext.setLoggedInUserId(userId);

            return;
        }

        this.communicationBus.sendMessage("Invalid credentials. Try again!");
    }

}
