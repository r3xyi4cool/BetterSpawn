package me.rexyiscool.betterSpawns;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final BetterSpawns plugin;

    public SetSpawnCommand(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission(plugin.setspawnPermission)) {
                player.getWorld().setSpawnLocation(player.getLocation());
                player.sendMessage("Successfully Set the Spawn Point");
            } else {
                commandSender.sendMessage("You don't have permission to do this command");
            }
        } else {
            commandSender.sendMessage("Only players can use this command");
        }
        return true;
    }
}
