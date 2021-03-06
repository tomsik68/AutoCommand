package sk.tomsik68.autocommand;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import sk.tomsik68.autocommand.args.ArgumentParsers;
import sk.tomsik68.autocommand.args.ArgumentTokenizer;
import sk.tomsik68.autocommand.context.CommandExecutionContext;
import sk.tomsik68.autocommand.context.ContextParameterProvider;
import sk.tomsik68.autocommand.err.DefaultErrorMessageProvider;
import sk.tomsik68.autocommand.err.ErrorMessageProvider;
import sk.tomsik68.permsguru.EPermissions;

public class AutoCommandInstance {
    private final Plugin ownerPlugin;
    private final ArgumentParsers argumentParsers;
    private final ErrorMessageProvider errorMessageProvider;
    private final CommandRegistrationManager commandRegistration;
    private final ContextParameterProviderRegistry cppr;
    private final EPermissions perms;

    public AutoCommandInstance(Plugin plugin, EPermissions permissionSystem, ArgumentTokenizer tokenizer, ErrorMessageProvider provider) {
        Validate.notNull(plugin, "You have to specify plugin");
        Validate.notNull(tokenizer, "You have to specify a tokenizer");
        if (permissionSystem == null) {
            permissionSystem = EPermissions.SP;
        }
        if (provider == null) {
            provider = new DefaultErrorMessageProvider();
        }
        this.ownerPlugin = plugin;
        argumentParsers = new ArgumentParsers(tokenizer);
        errorMessageProvider = provider;
        this.perms = permissionSystem;
        commandRegistration = new CommandRegistrationManager(this);
        cppr = new ContextParameterProviderRegistry();
    }

    public Plugin getOwner() {
        return ownerPlugin;
    }

    public ArgumentParsers getArgumentParsers() {
        return argumentParsers;
    }

    public ErrorMessageProvider getErrorMessageProvider() {
        return errorMessageProvider;
    }

    public void registerCommands(String commandName, Object obj) {
        Validate.notEmpty(commandName, "You have to specify command name");
        Validate.notNull(obj, "The object you're trying to register commands from is null");
        PluginCommand pluginCommand = ownerPlugin.getServer().getPluginCommand(commandName);
        Validate.notNull(pluginCommand, "You have to register your command in plugin.yml");
        commandRegistration.register(pluginCommand, obj);
    }

    public EPermissions getPermissions() {
        return perms;
    }

    public void registerContextParameterProvider(ContextParameterProvider provider) {
        cppr.registerProvider(provider);
    }

    public Collection<ContextParameterProvider> getProviders(CommandExecutionContext context) {
        return cppr.getProviders(context);
    }
}
