package server.commands;

import server.context.ApplicationContext;
import server.repository.AccountRepository;

import java.util.List;

public class BalanceCommand extends ApplicationCommand {
    public BalanceCommand(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    protected List<CommandStep> getSteps() {
        return List.of();
    }

    @Override
    protected String validateStep(String key, String value) {
        return null;
    }

    @Override
    protected String execute() {
        AccountRepository accountRepository = new AccountRepository();
        float balance = accountRepository.getBalanceByUserId(this.applicationContext.getLoggedInUserId());

        return "Your balance: " + balance;
    }
}
