package sk.tomsik68.bukkit.autocommand;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import sk.tomsik68.bukkit.autocommand.err.InvalidArgumentCountException;
import sk.tomsik68.permsguru.EPermissions;

public class AutoCommandHandler implements CommandExecutor {
    private final CustomCommandExecutor exec;
    private final EPermissions perms;

    public AutoCommandHandler(CustomCommandExecutor executor, EPermissions p) {
        Validate.notNull(p);
        Validate.notNull(executor);
        this.perms = p;
        this.exec = executor;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            exec.runCommand(sender, args);
        } catch (InvalidArgumentCountException iace) {
            sender.sendMessage(ChatColor.RED + "");
        }

        return true;
    }
}
