package me.rexyiscool.betterSpawns;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private final BetterSpawns plugin;

    public ReloadCommand(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission(plugin.reloadPermission)) {
                plugin.reloadConfig();
                player.sendMessage("§a§lSpawn Plugin Has Been Reloaded");
            } else {
                player.sendMessage("§c§lYou don't have the permission to do this command");
            }
        } else {
            plugin.reloadConfig();
            commandSender.sendMessage("Spawn Plugin Has Been Reloaded.");
        }
        return true;
    }
}
