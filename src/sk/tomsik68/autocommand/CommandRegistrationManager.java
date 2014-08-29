package sk.tomsik68.autocommand;

import java.lang.reflect.Method;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import sk.tomsik68.autocommand.err.CommandRegistrationException;

class CommandRegistrationManager {
    private final AutoCommandContext context;

    CommandRegistrationManager(AutoCommandContext context) {
        this.context = context;
    }

    void register(PluginCommand cmd, Object obj) {
        int commandMethods = 0;
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AutoCommand.class)) {
                ++commandMethods;
            }
        }
        AutoCommandExecutor handler;
        if (commandMethods == 0) {
            throw new IllegalArgumentException("You need to annotate your command methods with @AutoCommand");
        } else if (commandMethods == 1) {
            SingleCommand exec = null;
            for (Method method : methods) {
                AutoCommand annotation = method.getAnnotation(AutoCommand.class);
                if (annotation != null) {
                    exec = new SingleCommand(context, method, obj, annotation);
                }
            }
            handler = new AutoCommandExecutor(exec, context.getPermissions(), context.getErrorMessageProvider());
        } else {
            MultipleCommands exec = new MultipleCommands(context, cmd.getDescription());
            Validate.notNull(obj);
            for (Method method : methods) {
                AutoCommand annotation = method.getAnnotation(AutoCommand.class);
                if (annotation != null) {
                    if (method.getParameterTypes().length == 0) {
                        throw new CommandRegistrationException("The annotated method needs at least one parameter(CommandSender)");
                    }
                    if (!method.getParameterTypes()[0].equals(CommandSender.class)) {
                        throw new CommandRegistrationException("First parameter of your command method must be " + CommandSender.class.getName());
                    }
                    String name = annotation.name();
                    if (name == null || name.length() == 0) {
                        name = method.getName();
                    }
                    exec.registerCommand(name, new SingleCommand(context, method, obj, annotation));
                }
            }
            handler = new AutoCommandExecutor(exec, context.getPermissions(), context.getErrorMessageProvider());
        }
        Validate.notNull(handler);
        cmd.setExecutor(handler);
    }
}
