package server.commands;

import server.context.ApplicationContext;
import server.repository.AccountRepository;

import java.util.List;

public class WithdrawMoneyCommand extends ApplicationCommand {
    private final AccountRepository accountRepository;

    public WithdrawMoneyCommand(ApplicationContext applicationContext) {
        super(applicationContext);

        this.accountRepository = new AccountRepository();
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
            double amount = Double.parseDouble(value);

            if(amount <= 0) {
                return "Amount must be a positive number";
            }

            String loggedInUserId = this.applicationContext.getLoggedInUserId();
            double balance = this.accountRepository.getBalanceByUserId(loggedInUserId);

            if(balance < amount) {
                return "You don't have enough money.";
            }

            return null;
        } catch(NumberFormatException e) {
            return "Amount must be a valid number";
        }
    }

    @Override
    protected String execute() {
        double amount = Double.parseDouble(this.getValueForStep("amount"));
        this.accountRepository.withdrawMoneyForUser(this.applicationContext.getLoggedInUserId(), amount);

        return "Successfully withdrawn money";
    }
}
