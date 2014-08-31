package sk.tomsik68.autocommand;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;

import sk.tomsik68.autocommand.context.CommandExecutionContext;
import sk.tomsik68.permsguru.EPermissions;

abstract class CustomCommandExecutor {
    protected final AutoCommandInstance instance;

    public CustomCommandExecutor(AutoCommandInstance instance) {
        Validate.notNull(instance);
        this.instance = instance;
    }

    public abstract String getHelp();

    public abstract String getUsage();

    public abstract String getPermission();

    public abstract void runCommand(CommandExecutionContext context, EPermissions perms, String[] args) throws Exception;
}
