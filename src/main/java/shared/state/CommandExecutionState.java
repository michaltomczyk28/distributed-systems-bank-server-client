package shared.state;

import server.context.ApplicationContext;
import shared.communication.SocketCommunicationBus;

public class CommandExecutionState implements ApplicationState {
    private SocketCommunicationBus communicationBus;
    private ApplicationContext applicationContext;
    private boolean didDisplayMenu = false;

    public CommandExecutionState(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.communicationBus = applicationContext.getCommunicationBus();
    }

    @Override
    public void onInput(String input) {}

    @Override
    public void next() {
        if(!this.didDisplayMenu) {
            this.displayMenu();
        }
    }

    private void displayMenu() {
        this.communicationBus.sendMessage("""
            \n
            BANK APPLICATION
            ――――――――――――――――――――
            Available commands:
            
            1. Transfer money: ⟪ transfer ⟫
            2. Check your balance: ⟪ balance ⟫
            3. Withdraw money: ⟪ withdraw ⟫
            4. Make a deposit: ⟪ deposit ⟫
            
            Type a command:""");

        this.didDisplayMenu = true;
    }
}
