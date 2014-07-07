package sk.tomsik68.bukkit.autocommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import sk.tomsik68.permsguru.EPermissions;

public class AutoCommandRunner {
    private final EPermissions perms;

    public AutoCommandRunner(EPermissions p) {
        this.perms = p;
    }

    public void registerCommands() {

    }

    public boolean runCommand(CommandSender sender, Command cmd, String label, String[] args) {

        return false;
    }

}
