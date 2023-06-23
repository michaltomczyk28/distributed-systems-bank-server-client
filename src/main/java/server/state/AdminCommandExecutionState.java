package server.state;

import server.commands.*;
import server.context.ApplicationContext;

public class AdminCommandExecutionState extends CommandExecutionState {
    public AdminCommandExecutionState(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    protected void displayMenu() {
        this.communicationBus.sendMessage("""
            \n
            BANK ADMINISTRATOR APPLICATION
            ――――――――――――――――――――
            Available commands:
            
            1. Create new user: ⟪ create ⟫
            1. Edit existing user: ⟪ edit ⟫
            
            Type a command:""");
    }

    @Override
    protected ApplicationCommand createCommandByName(String name) {
        switch(name) {
            case "create":
                return new CreateUserCommand(this.applicationContext);

            case "edit":
                return new EditUserCommand(this.applicationContext);
        }

        throw new RuntimeException("Invalid command");
    }
}
