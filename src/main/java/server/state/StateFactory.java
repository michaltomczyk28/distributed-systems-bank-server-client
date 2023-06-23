package server.state;

import server.context.ApplicationContext;

public interface StateFactory {
    UnauthenticatedState createUnauthenticatedState(ApplicationContext applicationContext);
    CommandExecutionState createCommandExecutionState(ApplicationContext applicationContext);
}
