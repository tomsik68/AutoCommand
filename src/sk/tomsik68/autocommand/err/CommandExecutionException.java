package sk.tomsik68.autocommand.err;

import org.bukkit.command.CommandException;

import sk.tomsik68.autocommand.CustomCommandExecutor;

public class CommandExecutionException extends CommandException {
    private static final long serialVersionUID = 6818431981799769676L;
    private final CustomCommandExecutor exec;

    public CommandExecutionException(CustomCommandExecutor exec) {
        this.exec = exec;
    }

    public String getCorrectUsage() {
        return exec.getUsage();
    }
}
