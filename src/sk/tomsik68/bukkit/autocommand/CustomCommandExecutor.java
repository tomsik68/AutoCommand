package sk.tomsik68.bukkit.autocommand;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public interface CustomCommandExecutor {
    public String getHelp();

    public String getUsage();

    public String getPermission();

    public void runCommand(CommandSender sender, String[] args) throws CommandException;
}
