package sk.tomsik68.bukkit.autocommand;

import java.util.HashMap;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import sk.tomsik68.bukkit.autocommand.err.InvalidArgumentCountException;
import sk.tomsik68.permsguru.EPermissions;

public class MultipleCommands implements CustomCommandExecutor {
    private final HashMap<String, CustomCommandExecutor> subCommands = new HashMap<String, CustomCommandExecutor>();
    private final String help;
    private final EPermissions perms;

    public MultipleCommands(EPermissions p, String help) {
        this.help = help;
        this.perms = p;
    }

    @Override
    public String getHelp() {
        return help;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            throw new InvalidArgumentCountException();
        }
    }

}
