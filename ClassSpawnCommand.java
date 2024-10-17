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
    private final Map<Player, Integer> teleportTasks = new HashMap<>();  // Store teleport task per player
    private final Map<Player, Integer> movementCheckTasks = new HashMap<>();  // Store movement check task per player

    public SpawnCommand(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location initialLocation = player.getLocation();


            ChatColor playerColor = plugin.getPlayerColor(player);
            player.sendMessage(playerColor + "Teleporting in " + plugin.teleportDelay + " seconds...");


            BukkitTask movementCheckTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                if (player.getLocation().distanceSquared(initialLocation) > 0.01) {
                    player.sendMessage(playerColor + "Teleportation canceled due to movement.");
                    Bukkit.getScheduler().cancelTask(teleportTasks.get(player));
                    Bukkit.getScheduler().cancelTask(movementCheckTasks.get(player));
                    teleportTasks.remove(player);
                    movementCheckTasks.remove(player);
                }
            }, 0L, 5L);
            movementCheckTasks.put(player, movementCheckTask.getTaskId());


            BukkitTask teleportTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Bukkit.getScheduler().cancelTask(movementCheckTasks.get(player));
                movementCheckTasks.remove(player);

                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(playerColor + "Teleporting to spawn...");


                List<String> commandsToExecute = plugin.getConfig().getStringList("command.command");
                if (commandsToExecute != null && !commandsToExecute.isEmpty()) {
                    for (String cmd : commandsToExecute) {
                        String processedCommand = cmd.replace("%player%", player.getName());
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), processedCommand);
                    }
                } else {
                    player.sendMessage(playerColor + "No commands to execute after teleport.");
                }

                teleportTasks.remove(player);

            }, plugin.teleportDelay * 20);
            teleportTasks.put(player, teleportTask.getTaskId());

            return true;
        } else {
            commandSender.sendMessage("This command can only be executed by a player.");
        }

        return false;
    }
}
