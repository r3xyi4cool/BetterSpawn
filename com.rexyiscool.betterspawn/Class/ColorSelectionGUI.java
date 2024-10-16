package me.rexyiscool.betterSpawns;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorSelectionGUI implements Listener {

    private final BetterSpawns plugin;

    public ColorSelectionGUI(BetterSpawns plugin) {
        this.plugin = plugin;
    }

    // Method to open the color selection GUI
    public void openColorSelectionGUI(Player player) {
        Inventory gui = plugin.getServer().createInventory(null, 9, "Select Teleport Message Color");

        // Add colored wool as options for players
        ItemStack redOption = createColorItem(Material.RED_WOOL, ChatColor.RED + "Red");
        ItemStack blueOption = createColorItem(Material.BLUE_WOOL, ChatColor.BLUE + "Blue");
        ItemStack greenOption = createColorItem(Material.GREEN_WOOL, ChatColor.GREEN + "Green");

        // Set items in the GUI
        gui.setItem(2, redOption);
        gui.setItem(4, blueOption);
        gui.setItem(6, greenOption);

        // Open the inventory for the player
        player.openInventory(gui);
    }

    // Helper method to create a colored item
    private ItemStack createColorItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // Event listener to handle clicks in the GUI
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Select Teleport Message Color")) {
            event.setCancelled(true);  // Prevent moving items

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String displayName = clickedItem.getItemMeta().getDisplayName();

            // Set the player's color based on the clicked item
            if (displayName.contains("Red")) {
                plugin.setPlayerColor(player, ChatColor.RED);
                player.sendMessage(ChatColor.RED + "You selected Red!");
            } else if (displayName.contains("Blue")) {
                plugin.setPlayerColor(player, ChatColor.BLUE);
                player.sendMessage(ChatColor.BLUE + "You selected Blue!");
            } else if (displayName.contains("Green")) {
                plugin.setPlayerColor(player, ChatColor.GREEN);
                player.sendMessage(ChatColor.GREEN + "You selected Green!");
            }

            player.closeInventory(); // Close the GUI after selection
        }
    }
}
