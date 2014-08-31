package sk.tomsik68.autocommand;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;
import org.bukkit.util.ChatPaginator.ChatPage;

import sk.tomsik68.autocommand.context.CommandExecutionContext;
import sk.tomsik68.autocommand.err.CommandRegistrationException;
import sk.tomsik68.autocommand.err.InvalidArgumentCountException;
import sk.tomsik68.autocommand.err.NoSuchCommandException;
import sk.tomsik68.permsguru.EPermissions;

class MultipleCommands extends CustomCommandExecutor {
    private final HashMap<String, CustomCommandExecutor> subCommands = new HashMap<String, CustomCommandExecutor>();
    private final String help;

    MultipleCommands(AutoCommandInstance ctx, String help) {
        super(ctx);
        this.help = help;
    }

    void registerCommand(String name, CustomCommandExecutor cmd) throws CommandRegistrationException {
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
        return "<sub-command> <parameters...>";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public void runCommand(CommandExecutionContext context, EPermissions perms, String[] args) throws Exception {
        CommandSender sender = context.getSender();
        if (args.length == 0) {
            sendHelp(sender, perms, 1);
            throw new InvalidArgumentCountException(getUsage());
        }
        String subCommandName = args[0];
        if (subCommandName.equalsIgnoreCase("?") || subCommandName.equalsIgnoreCase("help")) {
            int page = 1;
            if (args.length >= 2) {
                if (isInt(args[1]))
                    page = Integer.parseInt(args[1]);
            }
            sendHelp(sender, perms, page);
            return;
        }
        if (!subCommands.containsKey(subCommandName)) {
            sendHelp(sender, perms, 1);
            throw new NoSuchCommandException();
        }
        CustomCommandExecutor subCommand = subCommands.get(subCommandName);

        String[] args2;
        if (args.length > 1) {
            args2 = new String[args.length - 1];
            System.arraycopy(args, 1, args2, 0, args.length - 1);
        } else
            args2 = args;
        subCommand.runCommand(context, perms, args2);
    }

    private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void sendHelp(CommandSender sender, EPermissions perms, int pageNumber) {
        StringBuilder helpString = new StringBuilder();
        for (Entry<String, CustomCommandExecutor> entry : subCommands.entrySet()) {
            if (perms.has(sender, entry.getValue().getPermission())) {
                helpString = helpString.append(ChatColor.AQUA).append(entry.getKey()).append(' ').append(ChatColor.LIGHT_PURPLE)
                        .append(entry.getValue().getUsage()).append(' ').append(ChatColor.WHITE).append(" - ").append(ChatColor.GOLD)
                        .append(entry.getValue().getHelp()).append('\n');
            }
        }
        ChatPage page = ChatPaginator.paginate(helpString.toString(), pageNumber);
        sender.sendMessage(ChatColor.DARK_PURPLE + "===========Help page " + pageNumber + "/" + page.getTotalPages() + "==========");
        sender.sendMessage(page.getLines());

    }

}
