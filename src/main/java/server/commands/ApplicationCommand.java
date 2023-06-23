package server.commands;

import server.context.ApplicationContext;
import server.state.ClientCommandExecutionState;

import java.util.HashMap;
import java.util.List;

public abstract class ApplicationCommand {
    protected ApplicationContext applicationContext;

    private int currentStep = 0;
    private boolean isWaitingForInput = false;
    private HashMap<String, String> values;
    private String validationError;

    public ApplicationCommand(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.values = new HashMap<>();
    }

    public void onInput(String input) {
        if(!isWaitingForInput) {
            return;
        }

        this.isWaitingForInput = false;
        String currentStepKey = this.getSteps().get(this.currentStep).getKey();

        this.validationError = this.validateStep(currentStepKey, input);
        if(this.validationError != null) {
            return;
        }

        this.values.put(currentStepKey, input);
        this.currentStep++;
    }

    public void next() {
        if(isWaitingForInput) {
            return;
        }

        var communicationBus = this.applicationContext.getCommunicationBus();
        var steps = this.getSteps();

        if(this.validationError != null) {
            communicationBus.sendMessage("\nERROR: " + this.validationError);
            this.goBackToMenu();

            return;
        }

        if(this.currentStep < steps.size()) {
            communicationBus.sendMessage("\n");
            communicationBus.sendMessage(steps.get(this.currentStep).getLabel() + ':');
        } else {
            String result = this.execute();
            communicationBus.sendMessage("\n");
            communicationBus.sendMessage(result);

            this.goBackToMenu();

            return;
        }

        isWaitingForInput = true;
    }

    protected String getValueForStep(String key) {
        return this.values.get(key);
    };

    protected abstract List<CommandStep> getSteps();

    protected abstract String validateStep(String key, String value);

    protected abstract String execute();

    private void goBackToMenu() {
        this.applicationContext.setApplicationState(new ClientCommandExecutionState(this.applicationContext));
    }
}
