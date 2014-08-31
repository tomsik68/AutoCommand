package sk.tomsik68.autocommand.err;

import org.bukkit.command.CommandException;

public class CommandExecutionException extends CommandException {
    private static final long serialVersionUID = 6818431981799769676L;
    private final String correctUsage;

    public CommandExecutionException(String correctUsage) {
        this.correctUsage = correctUsage;
    }

    public String getCorrectUsage() {
        return correctUsage;
    }
}
