package server.state.factory;

import server.context.ApplicationContext;
import server.state.ClientCommandExecutionState;
import server.state.ClientUnauthenticatedState;
import server.state.CommandExecutionState;
import server.state.UnauthenticatedState;

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
