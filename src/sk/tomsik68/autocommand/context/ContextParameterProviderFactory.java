package sk.tomsik68.autocommand.context;

import org.bukkit.command.CommandSender;

public interface ContextParameterProviderFactory {
    public ContextParameterProvider createProvider(CommandSender sender);

    public boolean canCreate(CommandSender sender);
}
