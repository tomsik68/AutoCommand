package sk.tomsik68.autocommand.context;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandExecutionContextFactory {
    public CommandExecutionContext createContext(CommandSender sender) {
        CommandExecutionContext result;
        if (sender instanceof BlockCommandSender) {
            Block block = ((BlockCommandSender) sender).getBlock();
            if (block.getType() == Material.COMMAND) {
                result = new CommandBlockCommandExecutionContext(sender, block);
            } else {
                result = new BlockCommandExecutionContext(sender, block);
            }
        } else if (sender instanceof Entity) {
            if (sender instanceof Player) {
                result = new PlayerCommandExecutionContext(sender, (Player) sender);
            } else {
                result = new EntityCommandExecutionContext(sender, (Entity) sender);
            }
        } else
            result = new ConsoleCommandExecutionContext(sender);
        return result;
    }
}
