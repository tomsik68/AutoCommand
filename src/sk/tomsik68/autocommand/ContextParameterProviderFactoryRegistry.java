package sk.tomsik68.autocommand;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.command.CommandSender;

import sk.tomsik68.autocommand.context.ContextParameterProvider;
import sk.tomsik68.autocommand.context.ContextParameterProviderFactory;

class ContextParameterProviderFactoryRegistry {
    private ArrayList<ContextParameterProviderFactory> factories = new ArrayList<ContextParameterProviderFactory>();

    ContextParameterProviderFactoryRegistry() {

    }

    public Collection<ContextParameterProvider> createProviders(CommandSender sender) {
        ArrayList<ContextParameterProvider> result = new ArrayList<ContextParameterProvider>();
        for (ContextParameterProviderFactory factory : factories) {
            if (factory.canCreate(sender))
                factory.createProvider(sender);
        }
        return result;
    }

    public void registerFactory(ContextParameterProviderFactory factory) {
        factories.add(factory);
    }
}
