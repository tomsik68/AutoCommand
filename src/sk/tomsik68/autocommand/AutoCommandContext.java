package sk.tomsik68.autocommand;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import sk.tomsik68.autocommand.args.ArgumentParsers;
import sk.tomsik68.autocommand.args.ArgumentTokenizer;
import sk.tomsik68.autocommand.context.ContextParameterProvider;
import sk.tomsik68.autocommand.context.ContextParameterProviderFactory;
import sk.tomsik68.autocommand.err.DefaultErrorMessageProvider;
import sk.tomsik68.autocommand.err.ErrorMessageProvider;
import sk.tomsik68.permsguru.EPermissions;

public class AutoCommandContext {
    private final Plugin ownerPlugin;
    private final ArgumentParsers argumentParsers;
    private final ErrorMessageProvider errorMessageProvider;
    private final CommandRegistrationManager commandRegistration;
    private final ContextParameterProviderFactoryRegistry cppfr = new ContextParameterProviderFactoryRegistry();
    private final EPermissions perms;

    public AutoCommandContext(Plugin plugin, EPermissions permissionSystem, ArgumentTokenizer tokenizer, ErrorMessageProvider provider) {
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

    public void registerContextParameterProviderFactory(ContextParameterProviderFactory factory) {
        cppfr.registerFactory(factory);
    }

    public Collection<ContextParameterProvider> getProviders(CommandSender sender) {
        return cppfr.createProviders(sender);
    }
}
