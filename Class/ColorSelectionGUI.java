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
        Inventory gui = plugin.getServer().createInventory(null, 27, "Select Teleport Message Color");

        // Add colored wool as options for players
        ItemStack redOption = createColorItem(Material.RED_CANDLE, ChatColor.RED + "Red");
        ItemStack blueOption = createColorItem(Material.BLUE_CANDLE, ChatColor.BLUE + "Blue");
        ItemStack greenOption = createColorItem(Material.GREEN_CANDLE, ChatColor.GREEN + "Green");
        ItemStack blackOption = createColorItem(Material.BLACK_CANDLE,ChatColor.BLACK+"Black");
        ItemStack goldOption = createColorItem(Material.ORANGE_CANDLE,ChatColor.GOLD+"Gold");
        ItemStack cyanOption = createColorItem(Material.CYAN_CANDLE,ChatColor.AQUA+"Cyan");
        ItemStack purpleOption = createColorItem(Material.PURPLE_CANDLE,ChatColor.DARK_PURPLE+"Purple");
        ItemStack yellowOption = createColorItem(Material.YELLOW_CANDLE,ChatColor.YELLOW+"Yellow");
        ItemStack grayOption = createColorItem(Material.GRAY_CANDLE,ChatColor.DARK_GRAY+"Gray");


        // Set items in the GUI
        gui.setItem(2, redOption);
        gui.setItem(4, blueOption);
        gui.setItem(6, greenOption);
        gui.setItem(11, blackOption);
        gui.setItem(13, goldOption);
        gui.setItem(15, cyanOption);
        gui.setItem(20, purpleOption);
        gui.setItem(22, yellowOption);
        gui.setItem(24, grayOption);


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
            } else if (displayName.contains("Black")) {
                plugin.setPlayerColor(player, ChatColor.BLACK);
                player.sendMessage(ChatColor.BLACK + "You selected Black!");
            } else if (displayName.contains("Gold")) {
                plugin.setPlayerColor(player, ChatColor.GOLD);
                player.sendMessage(ChatColor.GOLD + "You selected Orange!");
            } else if (displayName.contains("Cyan")) {
                plugin.setPlayerColor(player, ChatColor.AQUA);
                player.sendMessage(ChatColor.AQUA + "You selected Cyan!");
            } else if (displayName.contains("Purple")) {
                plugin.setPlayerColor(player, ChatColor.DARK_PURPLE);
                player.sendMessage(ChatColor.DARK_PURPLE + "You selected Purple!");
            } else if (displayName.contains("Yellow")) {
                plugin.setPlayerColor(player, ChatColor.YELLOW);
                player.sendMessage(ChatColor.YELLOW + "You selected Yellow!");
            } else if (displayName.contains("Gray")) {
                plugin.setPlayerColor(player, ChatColor.DARK_GRAY);
                player.sendMessage(ChatColor.DARK_GRAY + "You selected Gray!");
            }


            player.closeInventory(); // Close the GUI after selection
        }
    }
}
