package me.rexyiscool.spawnPluginBlitz;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public final class SpawnPluginBlitz extends JavaPlugin {

    private int teleportDelay;
    private String setspawnPermission;
    private String reloadPermission;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        if (getConfig() == null) {
            getLogger().severe("Config file is missing!");
        } else {
            getLogger().info("Config file loaded successfully.");
        }

        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("spawnreload").setExecutor(new ReloadCommand(this));

        reloadPermission = getConfig().getString("spawn-reload");
        teleportDelay = getConfig().getInt("teleport-delay");
        setspawnPermission = getConfig().getString("setspawnperm");
        getLogger().info("The Blitz Spawn Plugin is enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Blitz Spawn plugin has been shutdown Successfully");
    }

    public static class SetSpawnCommand implements CommandExecutor {

        private final SpawnPluginBlitz plugin;

        public SetSpawnCommand(SpawnPluginBlitz plugin) {
            this.plugin = plugin;
        }

        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (player.hasPermission(plugin.setspawnPermission)) {
                    player.getWorld().setSpawnLocation(player.getLocation());
                    player.sendMessage("Successfully Set the Spawn Point"); // Corrected typo
                } else {
                    commandSender.sendMessage("You don't have permission to do this command");
                }
            } else {
                commandSender.sendMessage("Only players can use this command"); // Improved message
            }
            return true;
        }
    }

    public static class SpawnCommand implements CommandExecutor {

        private final SpawnPluginBlitz plugin;

        public SpawnCommand(SpawnPluginBlitz plugin) {
            this.plugin = plugin;
        }

        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                player.sendMessage("Teleporting in " + plugin.teleportDelay + " seconds...");
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.teleport(player.getWorld().getSpawnLocation());
                    player.sendMessage("Teleporting to spawn...");
                    List<String> commandsToExecute = plugin.getConfig().getStringList("command.command");
                    if (commandsToExecute != null && !commandsToExecute.isEmpty()) {
                        for (String cmd : commandsToExecute) {
                            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd);
                            player.sendMessage("Executing command: " + cmd);
                        }
                    } else {
                        player.sendMessage("No commands to execute after teleport.");
                    }
                }, plugin.teleportDelay * 20);
                return true;
            } else {
                commandSender.sendMessage("This command can only be done by the player");
            }

            return false;
        }
    }
    public class ReloadCommand implements CommandExecutor {

        private final SpawnPluginBlitz plugin;

        public ReloadCommand(SpawnPluginBlitz plugin) {
            this.plugin=plugin;
        }
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (player.hasPermission(plugin.reloadPermission)) {
                    reloadConfig();
                    player.sendMessage("§a§lSpawn Plugin Has Been Reloaded");
                } else {
                    player.sendMessage("§c§lYou don't have the permission to do this command");
                }
            } else {
                reloadConfig();
                commandSender.sendMessage("Spawn Plugin Has Been Reloaded.");
            }
            return true;
        }
    }
}
