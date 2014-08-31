package sk.tomsik68.autocommand.context;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public abstract class LocatedCommandExecutionContext extends CommandExecutionContext {

    public LocatedCommandExecutionContext(CommandSender sender) {
        super(sender);
    }

    public abstract Location getLocation();

}
