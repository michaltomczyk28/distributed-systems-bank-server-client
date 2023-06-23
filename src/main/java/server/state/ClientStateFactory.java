package server.state;

import server.context.ApplicationContext;

public class ClientStateFactory implements StateFactory {
    @Override
    public UnauthenticatedState createUnauthenticatedState(ApplicationContext applicationContext) {
        return new ClientUnauthenticatedState(applicationContext);
    }

    @Override
    public CommandExecutionState createCommandExecutionState(ApplicationContext applicationContext) {
        return new ClientCommandExecutionState(applicationContext);
    }
}
