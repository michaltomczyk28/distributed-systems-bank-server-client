package shared.state;

import server.commands.*;
import server.context.ApplicationContext;
import shared.communication.SocketCommunicationBus;

public class CommandExecutionState implements ApplicationState {
    private SocketCommunicationBus communicationBus;
    private ApplicationContext applicationContext;
    private ApplicationCommand currentCommand;
    private boolean didDisplayMenu = false;

    public CommandExecutionState(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.communicationBus = applicationContext.getCommunicationBus();
        this.communicationBus.registerListener(this);
    }

    @Override
    public void onInput(String input) {
        if(currentCommand == null) {
            this.currentCommand = this.createCommandByName(input);

            return;
        }

        this.currentCommand.onInput(input);
    }

    @Override
    public void next() {
        if(!this.didDisplayMenu) {
            this.displayMenu();
            return;
        }

        if(this.currentCommand != null) {
            this.currentCommand.next();
        }
    }

    private void displayMenu() {
        this.communicationBus.sendMessage("""
            \n
            BANK APPLICATION
            ――――――――――――――――――――
            Available commands:
            
            1. My account: ⟪ me ⟫
            1. Transfer money: ⟪ transfer ⟫
            2. Check your balance: ⟪ balance ⟫
            3. Withdraw money: ⟪ withdraw ⟫
            4. Make a deposit: ⟪ deposit ⟫
            
            Type a command:""");

        this.didDisplayMenu = true;
    }

    private ApplicationCommand createCommandByName(String name) {
        switch(name) {
            case "me":
                return new UserInformationCommand(this.applicationContext);

            case "balance":
                return new GetBalanceCommand(this.applicationContext);

            case "deposit":
                return new DepositMoneyCommand(this.applicationContext);

            case "withdraw":
                return new WithdrawMoneyCommand(this.applicationContext);
        }

        this.communicationBus.sendMessage("\nERROR: Invalid command!");
        this.didDisplayMenu = false;

        return null;
    }
}
