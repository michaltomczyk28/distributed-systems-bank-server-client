package server.commands;

import server.context.ApplicationContext;
import server.model.UserInformation;
import server.repository.AccountRepository;
import server.repository.UserRepository;

import java.util.List;

public class EditUserCommand extends ApplicationCommand {
    private UserRepository userRepository;

    public EditUserCommand(ApplicationContext applicationContext) {
        super(applicationContext);

        this.userRepository = new UserRepository();
    }

    @Override
    protected List<CommandStep> getSteps() {
        return List.of(
            new CommandStep("username", "Username to edit"),
            new CommandStep("firstname", "First name (ENTER to skip)"),
            new CommandStep("lastname", "Last name (ENTER to skip)"),
            new CommandStep("pesel", "PESEL (ENTER to skip)")
        );
    }

    @Override
    protected String validateStep(String key, String value) {
        if(key.equals("username") && this.userRepository.isUsernameUnique(value)) {
            return "User does not exist";
        }

        if(value.length() == 0) {
            return null;
        }

        if(key.equals("pesel") && !value.matches("^\\d{11}$")) {
            return "Invalid PESEL";
        }

        if(value.length() < 3) {
            return "At least 3 characters needed";
        }

        return null;
    }

    @Override
    protected String execute() {
        String username = this.getValueForStep("username");
        String userId = this.userRepository.getUserIdByUsername(username);
        UserInformation userInformation = this.userRepository.getUserInformation(userId);

        String firstname = this.getValueForStep("firstname");
        String lastname = this.getValueForStep("lastname");
        String pesel = this.getValueForStep("pesel");

        this.userRepository.updateUser(
            userId,
            firstname.length() > 0 ? firstname : userInformation.getFirstname(),
            lastname.length() > 0 ? lastname : userInformation.getLastname(),
            pesel.length() > 0 ? pesel : userInformation.getPesel()
        );

        return "User updated successfully";
    }
}
