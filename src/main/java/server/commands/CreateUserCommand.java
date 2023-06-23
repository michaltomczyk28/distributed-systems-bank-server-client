package server.commands;

import server.context.ApplicationContext;
import server.model.NewUserDTO;
import server.repository.AccountRepository;
import server.repository.UserRepository;

import java.util.List;
import java.util.Random;

public class CreateUserCommand extends ApplicationCommand {
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public CreateUserCommand(ApplicationContext applicationContext) {
        super(applicationContext);

        this.accountRepository = new AccountRepository();
        this.userRepository = new UserRepository();
    }

    @Override
    protected List<CommandStep> getSteps() {
        return List.of(
            new CommandStep("firstname", "First name"),
            new CommandStep("lastname", "Last name"),
            new CommandStep("pesel", "PESEL"),
            new CommandStep("username", "Username"),
            new CommandStep("password", "Password")
        );
    }

    @Override
    protected String validateStep(String key, String value) {
        if(key.equals("pesel") && !value.matches("^\\d{11}$")) {
            return "Invalid PESEL";
        }

        if(key.equals("username") && !this.userRepository.isUsernameUnique(value)){
            return "Username must be unique";
        }

        if(value.length() < 3) {
            return "At least 3 characters needed";
        }

        return null;
    }

    @Override
    protected String execute() {
        NewUserDTO newUser = new NewUserDTO(
                this.getValueForStep("firstname"),
                this.getValueForStep("lastname"),
                this.getValueForStep("pesel"),
                this.getValueForStep("username"),
                this.getValueForStep("password")
        );

        String userId = this.userRepository.createUser(newUser);
        this.accountRepository.createAccount(userId, this.generateAccountNumber());

        return "Successfully created new user";
    }

    private String generateAccountNumber() {
        String result = "PL";
        Random rand = new Random();

        for(int i = 0; i < 14; i++) {
            int n = rand.nextInt(10);
            result += Integer.toString(n);
        }

        if(this.accountRepository.getOwnerId(result) != null) {
            return generateAccountNumber();
        }

        return result;
    }
}
