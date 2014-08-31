package sk.tomsik68.autocommand.context;

import org.bukkit.command.CommandSender;

public class CommandExecutionContext {
    protected final CommandSender sender;

    public CommandExecutionContext(CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
    }

}
