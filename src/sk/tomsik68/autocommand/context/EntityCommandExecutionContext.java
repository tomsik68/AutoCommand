package sk.tomsik68.autocommand.context;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class EntityCommandExecutionContext extends LocatedCommandExecutionContext {
    protected final Entity entity;

    public EntityCommandExecutionContext(CommandSender sender, Entity entity) {
        super(sender);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public Location getLocation() {
        return entity.getLocation();
    }

}
