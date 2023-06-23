package server.state.factory;

import server.context.ApplicationContext;
import server.state.*;

public class AdminStateFactory implements StateFactory {
    @Override
    public UnauthenticatedState createUnauthenticatedState(ApplicationContext applicationContext) {
        return new AdminUnauthenticatedState(applicationContext);
    }

    @Override
    public CommandExecutionState createCommandExecutionState(ApplicationContext applicationContext) {
        return new AdminCommandExecutionState(applicationContext);
    }
}
