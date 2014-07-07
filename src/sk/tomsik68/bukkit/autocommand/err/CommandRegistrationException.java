package sk.tomsik68.bukkit.autocommand.err;

import org.bukkit.command.CommandException;

public class CommandRegistrationException extends CommandException {

    private static final long serialVersionUID = -2448261049749023836L;

    public CommandRegistrationException(String message) {
        super(message);
    }
}
