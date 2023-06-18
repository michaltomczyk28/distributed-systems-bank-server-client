package server.commands;

import server.context.ApplicationContext;
import server.repository.AccountRepository;

import java.util.List;

public class DepositMoneyCommand extends ApplicationCommand {
    public DepositMoneyCommand(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    protected List<CommandStep> getSteps() {
        return List.of(
            new CommandStep("amount", "Amount")
        );
    }

    @Override
    protected String validateStep(String key, String value) {
        try {
            float amount = Float.parseFloat(value);

            if(amount <= 0) {
                return "Amount must be a positive number";
            }

            return null;
        } catch(NumberFormatException e) {
            return "Amount must be a valid number";
        }
    }

    @Override
    protected String execute() {
        float amount = Float.parseFloat(this.getValueForStep("amount"));

        AccountRepository accountRepository = new AccountRepository();
        accountRepository.depositMoneyForUser(this.applicationContext.getLoggedInUserId(), amount);

        return "Successfully deposited money";
    }
}
