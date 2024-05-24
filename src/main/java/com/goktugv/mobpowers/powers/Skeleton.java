package com.goktugv.mobpowers.powers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Skeleton implements Listener {

    private final JavaPlugin plugin;
    private boolean canShoot = true;

    public Skeleton(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.SKELETON_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                if (canShoot) {
                    Player player = event.getPlayer();
                    World world = player.getWorld();
                    Location eyeLocation = player.getEyeLocation();
                    Vector direction = eyeLocation.getDirection().normalize();

                    world.spawnArrow(eyeLocation, direction, 2.5F, 2.0F);

                    canShoot = false;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            canShoot = true;
                        }
                    }.runTaskLater(plugin, getTimeout("skeleton.cooldown") * 20);
                }
            }
        }

    }

}