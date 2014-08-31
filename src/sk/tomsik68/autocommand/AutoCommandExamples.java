package sk.tomsik68.autocommand;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class AutoCommandExamples {

    @AutoCommand(
            permission = "example.give", 
            usage = "<player> <item> <amount>",
            help="Gives player an item"
            )
    public void give(CommandSender sender,@ContextArg Player target, Material item, int amount) {

    }

    @AutoCommand(
            permission = "example.strike", 
            usage = "<player>"
            )
    public void strike(CommandSender sender, Player otherPlayer) {

    }

}
