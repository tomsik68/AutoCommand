package sk.tomsik68.autocommand.examples;

import org.bukkit.plugin.java.JavaPlugin;

import sk.tomsik68.autocommand.AutoCommandInstance;
import sk.tomsik68.autocommand.args.StringRespectingArgumentTokenizer;
import sk.tomsik68.permsguru.EPermissions;

public class GivePlugin extends JavaPlugin {
    private AutoCommandInstance autoCommand;
    
    @Override
    public void onEnable() {
        autoCommand = new AutoCommandInstance(this, EPermissions.SP, new StringRespectingArgumentTokenizer(), null);
        autoCommand.registerCommands("give", new GivePluginCommands());
    }

    @Override
    public void onDisable() {
        
    }
}
