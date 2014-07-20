package sk.tomsik68.bukkit.autocommand;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import sk.tomsik68.bukkit.autocommand.err.CommandRegistrationException;
import sk.tomsik68.bukkit.autocommand.err.InvalidArgumentCountException;
import sk.tomsik68.bukkit.autocommand.err.NoSuchCommandException;
import sk.tomsik68.permsguru.EPermissions;

public class MultipleCommands implements CustomCommandExecutor {
    private final HashMap<String, CustomCommandExecutor> subCommands = new HashMap<String, CustomCommandExecutor>();
    private final String help;

    public MultipleCommands(String help) {
        this.help = help;
    }

    public void registerCommands(Object obj) throws CommandRegistrationException {
        Validate.notNull(obj);
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            AutoCommand annotation = method.getAnnotation(AutoCommand.class);
            if (annotation != null) {
                String name = ((AutoCommand) annotation).name();
                if (name == null || name.length() == 0) {
                    name = method.getName();
                }
                registerCommand(name, new SingleCommand(method, obj));
            }
        }
    }

    public void registerCommand(String name, CustomCommandExecutor cmd) throws CommandRegistrationException {
        Validate.notNull(name, "Name must be specified!");
        if (name.length() == 0)
            throw new IllegalArgumentException("Name must be specified!");
        Validate.notNull(cmd);
        if (subCommands.containsKey(name))
            throw new CommandRegistrationException("Such command already exists: " + name);
        subCommands.put(name, cmd);
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

        String[] args2;
        if (args.length > 1) {
            args2 = new String[args.length - 1];
            System.arraycopy(args, 0, args2, 1, args.length - 1);
        } else
            args2 = args;
        subCommand.runCommand(sender, perms, args2);
    }

    @Override
    public boolean isConsoleCommand() {
        return true;
    }

    @Override
    public boolean isPlayerCommand() {
        return true;
    }

}
