package com.goktugv.mobpowers.powers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Chicken implements Listener {

    private final JavaPlugin plugin;

    public Chicken(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.CHICKEN_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && item.getItemMeta().hasCustomModelData() && meta.getCustomModelData() == 1) {
                Location playerLocation = event.getPlayer().getLocation();
                World world = playerLocation.getWorld();
                world.spawnEntity(playerLocation.add(0, 1, 0), EntityType.EGG);
            }
        }
    }

    public static String locx(Player player) {
        Location originalLocation = player.getLocation();
        Location newLocation = originalLocation.clone().add(0, 1, 0);
        return newLocation.getBlockX() + " " + newLocation.getBlockY() + " " + newLocation.getBlockZ();
    }


}

