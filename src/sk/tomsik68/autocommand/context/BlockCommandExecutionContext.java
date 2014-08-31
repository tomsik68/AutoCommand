package sk.tomsik68.autocommand.context;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

public class BlockCommandExecutionContext extends CommandExecutionContext {
    protected final Block block;

    public BlockCommandExecutionContext(CommandSender sender, Block block) {
        super(sender);
        this.block = block;
    }

    public final Block getBlock() {
        return block;
    }

}
