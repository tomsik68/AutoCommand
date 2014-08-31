package sk.tomsik68.autocommand.context;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;

public class CommandBlockCommandExecutionContext extends BlockCommandExecutionContext {

    public CommandBlockCommandExecutionContext(CommandSender sender, Block block) {
        super(sender, block);
        if(block.getType() != Material.COMMAND){
            throw new IllegalArgumentException("Specified block must be command block");
        }
    }

    public final CommandBlock getCommandBlock() {
        return (CommandBlock) block.getState();
    }

}
