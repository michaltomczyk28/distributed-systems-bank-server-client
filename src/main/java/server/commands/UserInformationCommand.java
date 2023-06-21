package server.commands;

import server.context.ApplicationContext;
import server.model.UserInformation;
import server.repository.UserRepository;

import java.util.List;

public class UserInformationCommand extends ApplicationCommand {
    private final UserRepository userRepository;

    public UserInformationCommand(ApplicationContext applicationContext) {
        super(applicationContext);

        this.userRepository = new UserRepository();
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
        UserInformation user = this.userRepository.getUserInformation(this.applicationContext.getLoggedInUserId());

        return String.format("""
                Username: %s
                First name: %s
                Last name: %s,
                PESEL: %s
                
                Account number: %s""",
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getPesel(),
                user.getAccountNumber()
        );
    }
}
