package server.commands;

public class CommandStep {
    private String key;
    private String label;

    public CommandStep(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return this.key;
    }

    public String getLabel() {
        return this.label;
    }
}
