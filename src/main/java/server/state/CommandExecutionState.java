package server.state;

import server.commands.ApplicationCommand;
import server.context.ApplicationContext;
import shared.communication.SocketCommunicationBus;

public abstract class CommandExecutionState implements ApplicationState {
    protected SocketCommunicationBus communicationBus;
    protected ApplicationContext applicationContext;

    private ApplicationCommand currentCommand;
    private boolean didDisplayMenu = false;

    public CommandExecutionState(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.communicationBus = applicationContext.getCommunicationBus();
        this.communicationBus.registerListener(this);
    }

    @Override
    public void onInput(String input) {
        if(this.currentCommand == null) {
            try {
                this.currentCommand = this.createCommandByName(input);
            } catch(Exception e) {
                this.communicationBus.sendMessage("\nERROR: Invalid command!");

                this.didDisplayMenu = false;
                this.currentCommand = null;
            }

            return;
        }

        this.currentCommand.onInput(input);
    }

    @Override
    public void next() {
        if(!this.didDisplayMenu) {
            this.displayMenu();
            this.didDisplayMenu = true;

            return;
        }

        if(this.currentCommand != null) {
            this.currentCommand.next();
        }
    }

    protected abstract void displayMenu();

    protected abstract ApplicationCommand createCommandByName(String name);
}
