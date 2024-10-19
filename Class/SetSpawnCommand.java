package me.rexyiscool.betterSpawns;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    // Reference to the main plugin class
    private final BetterSpawns plugin;

    // Constructor to initialize the SetSpawnCommand with the plugin instance
    public SetSpawnCommand(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if the command sender is a player
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            // Check if the player has permission to set the spawn point
            if (player.hasPermission(plugin.setspawnPermission)) {
                // Set the spawn location to the player's current location
                player.getWorld().setSpawnLocation(player.getLocation());
                player.sendMessage("Successfully Set the Spawn Point"); // Send success message to the player
            } else {
                commandSender.sendMessage("You don't have permission to do this command"); // Send no permission message
            }
        } else {
            commandSender.sendMessage("Only players can use this command"); // Inform non-player senders
        }
        return true; // Indicate that the command was handled successfully
    }
}
