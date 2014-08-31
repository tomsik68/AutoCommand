package sk.tomsik68.autocommand.examples;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import sk.tomsik68.autocommand.AutoCommand;
import sk.tomsik68.autocommand.context.CommandExecutionContext;

public class GivePluginCommands {
    public GivePluginCommands() {

    }

    @AutoCommand
    public void give(CommandExecutionContext context, Player player, Material item, int amount) {
        player.getInventory().addItem(new ItemStack(item, amount));
    }
}
