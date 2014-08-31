package sk.tomsik68.autocommand;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import sk.tomsik68.autocommand.args.ArgumentParserException;
import sk.tomsik68.autocommand.context.CommandExecutionContext;
import sk.tomsik68.autocommand.context.CommandExecutionContextFactory;
import sk.tomsik68.autocommand.err.CommandExecutionException;
import sk.tomsik68.autocommand.err.ErrorMessageProvider;
import sk.tomsik68.autocommand.err.NoPermissionException;
import sk.tomsik68.autocommand.err.NoSuchCommandException;
import sk.tomsik68.permsguru.EPermissions;

class AutoCommandExecutorWrapper implements CommandExecutor {
    private final CustomCommandExecutor exec;
    private final EPermissions perms;
    private ErrorMessageProvider errorMessages;
    private CommandExecutionContextFactory contextFactory;

    AutoCommandExecutorWrapper(CustomCommandExecutor executor, EPermissions p, ErrorMessageProvider errMessageProvider) {
        Validate.notNull(p);
        Validate.notNull(executor);
        this.perms = p;
        this.exec = executor;
        errorMessages = errMessageProvider;
        contextFactory = new CommandExecutionContextFactory();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            CommandExecutionContext context = contextFactory.createContext(sender);
            String argumentsInOneString = joinArgumentsIntoString(args);
            exec.runCommand(context, perms, argumentsInOneString);
        } catch (ArgumentParserException ape) {
            sender.sendMessage(ChatColor.RED + ape.getMessage());
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

    public static final String joinArgumentsIntoString(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder = builder.append(arg).append(' ');
        }
        return builder.toString();
    }

}
