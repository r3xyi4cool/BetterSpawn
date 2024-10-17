package me.rexyiscool.betterSpawns;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnCommand implements CommandExecutor {

    private final BetterSpawns plugin;
    private final Map<Player, Integer> teleportTasks = new HashMap<>();  // Store teleport task IDs per player
    private final Map<Player, Integer> movementCheckTasks = new HashMap<>();  // Store movement check task IDs per player

    public SpawnCommand(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location initialLocation = player.getLocation();  // Store the player's location at the start of teleport

            // Notify the player of the teleport delay
            ChatColor playerColor = plugin.getPlayerColor(player);
            player.sendTitle(playerColor + "Teleporting in " + plugin.teleportDelay,playerColor + " seconds...",20,40,20);

            // Task to monitor if the player moves during the teleportation delay
            BukkitTask movementCheckTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                // Cancel teleport if player moves
                if (player.getLocation().distanceSquared(initialLocation) > 0.01) {
                    player.sendMessage(playerColor + "Teleportation canceled due to movement.");
                    Bukkit.getScheduler().cancelTask(teleportTasks.get(player));  // Cancel teleport task
                    Bukkit.getScheduler().cancelTask(movementCheckTasks.get(player));  // Cancel movement check task
                    teleportTasks.remove(player);  // Clean up task tracking
                    movementCheckTasks.remove(player);
                }
            }, 0L, 5L);  // Check for movement every 5 ticks (0.25 seconds)
            movementCheckTasks.put(player, movementCheckTask.getTaskId());

            // Task to teleport the player after the delay if they don't move
            BukkitTask teleportTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Bukkit.getScheduler().cancelTask(movementCheckTasks.get(player));  // Stop movement check once teleport happens
                movementCheckTasks.remove(player);

                player.teleport(player.getWorld().getSpawnLocation());  // Teleport player to the world spawn
                player.sendMessage(playerColor + "Teleporting to spawn...");

                // Execute any configured commands after teleport
                List<String> commandsToExecute = plugin.getConfig().getStringList("command.command");
                if (commandsToExecute != null && !commandsToExecute.isEmpty()) {
                    for (String cmd : commandsToExecute) {
                        // Replace %player% with the player's name in the command and execute it
                        String processedCommand = cmd.replace("%player%", player.getName());
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), processedCommand);
                    }
                } else {
                    player.sendMessage(playerColor + "No commands to execute after teleport.");
                }

                teleportTasks.remove(player);  // Clean up task tracking after teleport

            }, plugin.teleportDelay * 20);  // Delay is multiplied by 20 to convert seconds to ticks
            teleportTasks.put(player, teleportTask.getTaskId());

            return true;
        } else {
            // Notify non-player senders that the command is player-only
            commandSender.sendMessage("This command can only be executed by a player.");
        }

        return false;
    }
}
