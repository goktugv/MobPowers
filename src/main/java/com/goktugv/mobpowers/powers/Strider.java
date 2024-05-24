package com.goktugv.mobpowers.powers;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Strider implements Listener {

    private final JavaPlugin plugin;

    public Strider(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getRightClicked() != null) {
            EntityType entityType = event.getRightClicked().getType();
            ItemStack item = event.getPlayer().getItemInHand();
            if (item != null && item.getType() == Material.STRIDER_SPAWN_EGG) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.getCustomModelData() == 1) {
                    player.addPassenger(event.getRightClicked());
                }
            }
        }
    }

}

