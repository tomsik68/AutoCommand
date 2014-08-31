package sk.tomsik68.autocommand;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;

import sk.tomsik68.permsguru.EPermissions;

abstract class CustomCommandExecutor {
    protected final AutoCommandContext context;

    public CustomCommandExecutor(AutoCommandContext ctx) {
        Validate.notNull(ctx);
        context = ctx;
    }

    public abstract String getHelp();

    public abstract String getUsage();

    public abstract String getPermission();

    public abstract void runCommand(CommandSender sender, EPermissions perms, String[] args) throws Exception;
}
