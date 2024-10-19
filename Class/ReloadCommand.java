package me.rexyiscool.betterSpawns;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    // Reference to the main plugin class
    private final BetterSpawns plugin;

    // Constructor to initialize the ReloadCommand with the plugin instance
    public ReloadCommand(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if the command sender is a player
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            // Check if the player has permission to reload the configuration
            if (player.hasPermission(plugin.reloadPermission)) {
                plugin.reloadConfig(); // Reload the plugin's configuration
                player.sendMessage("§a§lSpawn Plugin Has Been Reloaded"); // Send success message to the player
            } else {
                player.sendMessage("§c§lYou don't have the permission to do this command"); // Send no permission message
            }
        } else {
            // If the command sender is not a player (console or other), reload the configuration
            plugin.reloadConfig(); 
            commandSender.sendMessage("Spawn Plugin Has Been Reloaded."); // Send success message to the command sender
        }
        return true; // Indicate that the command was handled successfully
    }
}
