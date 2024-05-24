package com.goktugv.mobpowers.powers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class Snow_Golem implements Listener {

    private final JavaPlugin plugin;
    private boolean canShoot = true;

    public Snow_Golem(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item != null && item.getType() == Material.SNOW_GOLEM_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                if (!Arrays.asList(Material.AIR, Material.SNOW).contains(blockBelow.getType())) {
                    blockBelow.getRelative(0, 1, 0).setType(Material.SNOW);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.SNOW_GOLEM_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                if (canShoot) {
                    Player player = event.getPlayer();
                    Snowball ball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
                    ball.setShooter(player);
                    ball.setVelocity(player.getLocation().getDirection().multiply(1.5));

                    canShoot = false;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            canShoot = true;
                        }
                    }.runTaskLater(plugin, 10);

                }
            }
        }
    }


}

