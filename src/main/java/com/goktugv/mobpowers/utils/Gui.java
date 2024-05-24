package com.goktugv.mobpowers.utils;

import com.goktugv.mobpowers.PowerItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Gui implements Listener {

    static String title = "Canlı Yetenekleri";
    private final JavaPlugin plugin;

    public Gui(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void open(Player player) {
        int count = 0;
        Inventory gui = Bukkit.createInventory(player, 54, title);

        for (PowerItem powerItem : PowerItem.values()) {
            gui.setItem(count, powerItem.generate());
            count += 1;
        }
        player.openInventory(gui);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() != Material.AIR && event.getView().getTitle().equals(title)) {
                ItemMeta meta = clickedItem.getItemMeta();
                if (meta != null && meta.hasCustomModelData() && meta.getCustomModelData() == 1) {
                    event.setCancelled(true);
                    if (!Utils.playerInventoryContainsItem(player, clickedItem)) {
                        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        player.getInventory().addItem(clickedItem);
                    }
                }
            }
        }
    }


}
