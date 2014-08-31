package sk.tomsik68.autocommand.context;

import org.bukkit.command.CommandSender;

public class ConsoleCommandExecutionContext extends CommandExecutionContext {

    public ConsoleCommandExecutionContext(CommandSender sender) {
        super(sender);
    }

}
