package sk.tomsik68.bukkit.autocommand;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import sk.tomsik68.permsguru.EPermissions;

public interface CustomCommandExecutor {
    public String getHelp();

    public String getUsage();

    public String getPermission();

    public void runCommand(CommandSender sender, EPermissions perms, String[] args) throws CommandException;
}
