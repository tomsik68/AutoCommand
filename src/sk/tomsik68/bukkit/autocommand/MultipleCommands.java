package sk.tomsik68.bukkit.autocommand;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import sk.tomsik68.bukkit.autocommand.err.InvalidArgumentCountException;
import sk.tomsik68.bukkit.autocommand.err.NoPermissionException;
import sk.tomsik68.bukkit.autocommand.err.NoSuchCommandException;
import sk.tomsik68.permsguru.EPermissions;

public class MultipleCommands implements CustomCommandExecutor {
    private final HashMap<String, CustomCommandExecutor> subCommands = new HashMap<String, CustomCommandExecutor>();
    private final String help;

    public MultipleCommands(String help) {
        this.help = help;
    }

    public void registerCommands(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AutoCommand.class)) {
                
            }
        }
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
    public void runCommand(CommandSender sender, EPermissions perms, String[] args) throws CommandException {
        if (args.length == 0) {
            throw new InvalidArgumentCountException();
        }
        String subCommandName = args[0];
        if (!subCommands.containsKey(subCommandName)) {
            throw new NoSuchCommandException();
        }
        CustomCommandExecutor subCommand = subCommands.get(subCommandName);
        if (!perms.has(sender, subCommand.getPermission()))
            throw new NoPermissionException();
        String[] args2 = new String[args.length - 1];
        System.arraycopy(args, 0, args2, 1, args.length - 1);
        subCommand.runCommand(sender, perms, args2);
    }

}
