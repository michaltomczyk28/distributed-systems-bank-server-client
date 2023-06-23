package server.state.factory;

import server.context.ApplicationContext;
import server.state.CommandExecutionState;
import server.state.UnauthenticatedState;

public interface StateFactory {
    UnauthenticatedState createUnauthenticatedState(ApplicationContext applicationContext);
    CommandExecutionState createCommandExecutionState(ApplicationContext applicationContext);
}
