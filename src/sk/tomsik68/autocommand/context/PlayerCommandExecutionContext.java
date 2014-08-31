package sk.tomsik68.autocommand.context;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommandExecutionContext extends EntityCommandExecutionContext {

    public PlayerCommandExecutionContext(CommandSender sender, Player entity) {
        super(sender, entity);
    }

    public Player getPlayer() {
        return (Player) entity;
    }

}
