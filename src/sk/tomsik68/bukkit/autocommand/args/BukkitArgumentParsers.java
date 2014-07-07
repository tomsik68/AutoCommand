package sk.tomsik68.bukkit.autocommand.args;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BukkitArgumentParsers {
    static {
        ArgumentParsers.registerArgumentParser(Player.class, new PlayerParser());
        ArgumentParsers.registerArgumentParser(OfflinePlayer.class, new OfflinePlayerParser());
        ArgumentParsers.registerArgumentParser(World.class, new WorldParser());
        ArgumentParsers.registerArgumentParser(Plugin.class, new PluginParser());
    }

    public static class PlayerParser implements ArgumentParser {
        @Override
        public Object parse(String str) throws ArgumentParserException {
            Player player = Bukkit.getPlayerExact(str);
            if (player == null)
                player = Bukkit.getPlayer(str);
            return player;
        }
    }

    public static class OfflinePlayerParser implements ArgumentParser {
        @Override
        public Object parse(String str) throws ArgumentParserException {
            OfflinePlayer player = Bukkit.getOfflinePlayer(str);
            return player;
        }
    }

    public static class PluginParser implements ArgumentParser {
        @Override
        public Object parse(String str) throws ArgumentParserException {
            return Bukkit.getPluginManager().getPlugin(str);
        }
    }

    public static class WorldParser implements ArgumentParser {

        @Override
        public Object parse(String str) throws ArgumentParserException {
            World world = Bukkit.getWorld(str);
            if (world == null) {
                try {
                    world = Bukkit.getWorld(UUID.fromString(str));
                } catch (Exception e) {
                }
            }
            return world;
        }

    }
}
