package com.goktugv.mobpowers.powers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Dragon implements Listener {

    private final JavaPlugin plugin;

    public Dragon(JavaPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.ENDER_DRAGON_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                Location initialLocation = player.getLocation();
                Vector direction = initialLocation.getDirection();
                Location targetLocation = initialLocation.clone();

                for (int i = 0; i < 3; i++) {
                    targetLocation.add(direction.multiply(10));
                    player.getLocation().getWorld().createExplosion(targetLocation, 5.0f, false);
                    player.playSound(targetLocation, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1, 1);
                    player.spawnParticle(Particle.DRAGON_BREATH, targetLocation, 100);
                }

            }
        }
    }


}