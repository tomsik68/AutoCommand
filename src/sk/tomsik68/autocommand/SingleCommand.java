package sk.tomsik68.autocommand;

import java.lang.reflect.Method;

import org.bukkit.command.CommandSender;

import sk.tomsik68.autocommand.args.ArgumentParsers;
import sk.tomsik68.autocommand.err.InvalidArgumentCountException;
import sk.tomsik68.autocommand.err.NoPermissionException;
import sk.tomsik68.permsguru.EPermissions;

public class SingleCommand implements CustomCommandExecutor {
    private final Method method;
    private final String help, usage, permission;
    private final boolean console, player;
    private final Object obj;

    public SingleCommand(Method method, Object obj) {
        this.method = method;
        AutoCommand annotation = method.getAnnotation(AutoCommand.class);
        help = annotation.help();
        usage = annotation.usage();
        permission = annotation.permission();
        console = annotation.console();
        player = annotation.player();
        this.obj = obj;
    }

    @Override
    public String getHelp() {
        return help;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public void runCommand(CommandSender sender, EPermissions perms, String[] args) throws Exception {
        if (!perms.has(sender, getPermission()))
            throw new NoPermissionException();
        Object[] objectArgs = ArgumentParsers.parse(method.getParameterTypes(), args);
        Object[] finalObjectArgs = new Object[objectArgs.length + 1];
        System.arraycopy(objectArgs, 0, finalObjectArgs, 1, objectArgs.length);
        finalObjectArgs[0] = sender;
        
        try {
            method.invoke(obj, finalObjectArgs);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentCountException(this);
        }
    }

    @Override
    public boolean isConsoleCommand() {
        return console;
    }

    @Override
    public boolean isPlayerCommand() {
        return player;
    }

}
