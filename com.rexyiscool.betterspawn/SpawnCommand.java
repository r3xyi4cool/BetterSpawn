package me.rexyiscool.betterSpawns;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class SpawnCommand implements CommandExecutor {

    private final BetterSpawns plugin;

    public SpawnCommand(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location initialLocation = player.getLocation();
            player.sendMessage("Teleporting in " + plugin.teleportDelay + " seconds...");

            BukkitTask movementCheckTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                if (player.getLocation().distanceSquared(initialLocation) > 0.01) {
                    player.sendMessage("Teleportation canceled due to movement.");
                    Bukkit.getScheduler().cancelTask(plugin.teleportTaskId);
                    Bukkit.getScheduler().cancelTask(plugin.movementCheckTaskId);
                }
            }, 0L, 5L);
            plugin.movementCheckTaskId = movementCheckTask.getTaskId();

            BukkitTask teleportTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Bukkit.getScheduler().cancelTask(plugin.movementCheckTaskId);

                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage("Teleporting to spawn...");

                List<String> commandsToExecute = plugin.getConfig().getStringList("command.command");
                if (commandsToExecute != null && !commandsToExecute.isEmpty()) {
                    for (String cmd : commandsToExecute) {
                        String processedCommand = cmd.replace("%player%", player.getName());
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), processedCommand);
                    }
                } else {
                    player.sendMessage("No commands to execute after teleport.");
                }
            }, plugin.teleportDelay * 20);
            plugin.teleportTaskId = teleportTask.getTaskId();
            return true;
        } else {
            commandSender.sendMessage("This command can only be done by the player");
        }

        return false;
    }
}
