package sk.tomsik68.autocommand;

import java.lang.reflect.Method;

import sk.tomsik68.autocommand.context.CommandExecutionContext;
import sk.tomsik68.autocommand.err.InvalidArgumentCountException;
import sk.tomsik68.autocommand.err.NoPermissionException;
import sk.tomsik68.permsguru.EPermissions;

class SingleCommand extends CustomCommandExecutor {
    private final Method method;
    private final String help, usage, permission;
    private final Object obj;

    SingleCommand(AutoCommandInstance context, Method method, Object obj, AutoCommand annotation) {
        super(context);
        this.method = method;
        help = annotation.help();
        usage = annotation.usage();
        permission = annotation.permission();
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
    public void runCommand(CommandExecutionContext context, EPermissions perms, String args) throws Exception {
        if (!perms.has(context.getSender(), getPermission()))
            throw new NoPermissionException();

        Object[] objectArgs = instance.getArgumentParsers().parse(context, method, instance.getProviders(context), args);
        Object[] finalObjectArgs = new Object[objectArgs.length + 1];
        System.arraycopy(objectArgs, 0, finalObjectArgs, 1, objectArgs.length);
        finalObjectArgs[0] = context;

        try {
            method.invoke(obj, finalObjectArgs);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentCountException(getUsage());
        }
    }

}
