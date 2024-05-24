package com.goktugv.mobpowers.powers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Squid implements Listener {

    private final JavaPlugin plugin;

    public Squid(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.SQUID_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "effect give " + player.getName() + " blindness 10 0");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "effect give " + player.getName() + " water_breathing 10 0");
            }
        }
    }


}

