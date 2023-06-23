package server.state;

import server.commands.*;
import server.context.ApplicationContext;

public class ClientCommandExecutionState extends CommandExecutionState {
    public ClientCommandExecutionState(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    protected void displayMenu() {
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
    }

    @Override
    protected ApplicationCommand createCommandByName(String name) {
        switch(name) {
            case "me":
                return new UserInformationCommand(this.applicationContext);

            case "balance":
                return new GetBalanceCommand(this.applicationContext);

            case "deposit":
                return new DepositMoneyCommand(this.applicationContext);

            case "withdraw":
                return new WithdrawMoneyCommand(this.applicationContext);

            case "transfer":
                return new TransferCommand(this.applicationContext);
        }

        throw new RuntimeException("Invalid command");
    }
}
