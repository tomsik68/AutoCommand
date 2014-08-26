package sk.tomsik68.bukkit.autocommand;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import sk.tomsik68.bukkit.autocommand.err.CommandExecutionException;
import sk.tomsik68.bukkit.autocommand.err.DefaultErrorMessageProvider;
import sk.tomsik68.bukkit.autocommand.err.IErrorMessageProvider;
import sk.tomsik68.bukkit.autocommand.err.InvalidArgumentCountException;
import sk.tomsik68.bukkit.autocommand.err.NoPermissionException;
import sk.tomsik68.bukkit.autocommand.err.NoSuchCommandException;
import sk.tomsik68.permsguru.EPermissions;

public class AutoCommandHandler implements CommandExecutor {
    private final CustomCommandExecutor exec;
    private final EPermissions perms;
    private IErrorMessageProvider errorMessages;

    public AutoCommandHandler(CustomCommandExecutor executor, EPermissions p) {
        Validate.notNull(p);
        Validate.notNull(executor);
        this.perms = p;
        this.exec = executor;
        errorMessages = new DefaultErrorMessageProvider();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!perms.has(sender, exec.getPermission()))
                throw new NoPermissionException();
            exec.runCommand(sender, perms, args);
        } catch (CommandExecutionException iace) {
            sender.sendMessage(ChatColor.RED + "Correct usage: " + iace.getCorrectUsage());
        } catch (NoSuchCommandException nsce) {
            sender.sendMessage(ChatColor.RED + errorMessages.unknownCommand());
        } catch (NoPermissionException npmsne) {
            sender.sendMessage(ChatColor.RED + errorMessages.noPermission());
        } catch (Throwable t) {
            sender.sendMessage(ChatColor.RED + errorMessages.unknownError());
            t.printStackTrace();
        }

        return true;
    }

    public IErrorMessageProvider getErrorMessagesProvider() {
        return errorMessages;
    }

    public void setErrorMessagesProvider(IErrorMessageProvider errorMessages) {
        this.errorMessages = errorMessages;
    }
}
