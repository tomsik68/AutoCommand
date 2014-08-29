package sk.tomsik68.autocommand;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import sk.tomsik68.autocommand.err.CommandExecutionException;
import sk.tomsik68.autocommand.err.ErrorMessageProvider;
import sk.tomsik68.autocommand.err.NoPermissionException;
import sk.tomsik68.autocommand.err.NoSuchCommandException;
import sk.tomsik68.permsguru.EPermissions;

public class AutoCommandExecutor implements CommandExecutor {
    private final CustomCommandExecutor exec;
    private final EPermissions perms;
    private ErrorMessageProvider errorMessages;

    AutoCommandExecutor(CustomCommandExecutor executor, EPermissions p, ErrorMessageProvider errMessageProvider) {
        Validate.notNull(p);
        Validate.notNull(executor);
        this.perms = p;
        this.exec = executor;
        errorMessages = errMessageProvider;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("Arguments for root handler:" + args.length);
        try {
            if (!perms.has(sender, exec.getPermission()))
                throw new NoPermissionException();
            exec.runCommand(sender, perms, args);
        } catch (CommandExecutionException iace) {
            if (iace.getCorrectUsage() != null && !iace.getCorrectUsage().isEmpty())
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

}
