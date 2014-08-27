package sk.tomsik68.autocommand;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoCommandExamples {

    @AutoCommand(
            console = true, 
            player = true, 
            permission = "example.give", 
            usage = "<player> <item> <amount>"
            )
    public void give(CommandSender sender,Player target, Material item, int amount) {

    }

    @AutoCommand(
            console = true, 
            player = true, 
            permission = "example.strike", 
            usage = "<player>"
            )
    public void strike(CommandSender sender, Player otherPlayer) {

    }

}
