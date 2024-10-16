package me.rexyiscool.betterSpawns.commands;

import me.rexyiscool.betterSpawns.BetterSpawns;
import me.rexyiscool.betterSpawns.ColorSelectionGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnMenuCommand implements CommandExecutor {

    private final BetterSpawns plugin;

    public SpawnMenuCommand(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Open the color selection GUI
            plugin.getColorSelectionGUI().openColorSelectionGUI(player);
            return true;
        } else {
            sender.sendMessage("Only players can open the spawn menu.");
            return false;
        }
    }
}
