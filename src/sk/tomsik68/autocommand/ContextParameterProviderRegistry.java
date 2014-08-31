package sk.tomsik68.autocommand;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.command.CommandSender;

import sk.tomsik68.autocommand.context.CommandExecutionContext;
import sk.tomsik68.autocommand.context.ContextParameterProvider;

class ContextParameterProviderRegistry {
    private ArrayList<ContextParameterProvider> providers = new ArrayList<ContextParameterProvider>();

    ContextParameterProviderRegistry() {

    }

    public Collection<ContextParameterProvider> getProviders(CommandExecutionContext context) {
        ArrayList<ContextParameterProvider> result = new ArrayList<ContextParameterProvider>();
        for (ContextParameterProvider provider : providers) {
            if (provider.canProvide(context))
                result.add(provider);
        }
        return result;
    }

    public void registerProvider(ContextParameterProvider provider) {
        providers.add(provider);
    }
}
