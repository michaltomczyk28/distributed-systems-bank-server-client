package server.commands;

import server.context.ApplicationContext;
import server.repository.AccountRepository;

import java.util.Arrays;
import java.util.List;

public class TransferCommand extends ApplicationCommand {
    private final AccountRepository accountRepository;

    public TransferCommand(ApplicationContext applicationContext) {
        super(applicationContext);

        this.accountRepository = new AccountRepository();
    }

    @Override
    protected List<CommandStep> getSteps() {
        return Arrays.asList(
            new CommandStep("accountNumber", "Target account number"),
            new CommandStep("amount", "Amount of money")
        );
    }

    @Override
    protected String validateStep(String key, String value) {
        if(key.equals("accountNumber")) {
            String userId = this.applicationContext.getLoggedInUserId();
            String ownerId = this.accountRepository.getOwnerId(value);

            if(ownerId == null) {
                return "Account number does not exist";
            }

            if(ownerId.equals(userId)) {
                return "Provide account number of a different user";
            }
        }

        if(key.equals("amount")) {
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

        return null;
    }

    @Override
    protected String execute() {
        double amount = Double.parseDouble(this.getValueForStep("amount"));
        String accountNumber = this.getValueForStep("accountNumber");

        this.accountRepository.transferMoney(this.applicationContext.getLoggedInUserId(), accountNumber, amount);

        return "Successfully send money to " + accountNumber;
    }
}
