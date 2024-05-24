package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Cooldown;
import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;


public class Warden implements Listener {
    private final JavaPlugin plugin;

    Cooldown cd = new Cooldown(30000);
    long cooldownTime = getTimeout("warden.cooldown");

    public Warden(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.WARDEN_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (cd.has(player, "warden")) {
                    long remainingTime = cd.remaining(player, "warden");
                    player.sendMessage(nameColor + "[Warden] " + ChatColor.RED + "Yeteneği " + remainingTime / 1000 + " saniye sonra kullanabilirsin.");
                    return;
                }

                Block block = player.getLocation().getBlock();

                cd.set(player, "warden", (cooldownTime) * 1000L);
                player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);

                new BukkitRunnable() {
                    double currentRadius = 5.0;
                    @Override
                    public void run() {
                        if (currentRadius >= 20.0) {
                            cancel();
                            return;
                        }

                        if (new Random().nextDouble() < 0.55) {
                            World world = player.getLocation().getWorld();
                            if (world != null) {
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 60, 1));
                                }

                                player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_ANGRY, 1, 1);
                                world.spawnEntity(player.getLocation(), EntityType.WARDEN);
                            }
                        }

                        infectBlocks(player.getLocation().clone(), currentRadius);
                        currentRadius += 1.0;
                    }
                }.runTaskTimer(plugin, 0, 10);

            }
        }
        return;
    }

    private void infectBlocks(Location startLocation, double radius) {
        World world = startLocation.getWorld();
        for (double x = -radius; x <= radius; x++) {
            for (double y = -radius; y <= radius; y++) {
                for (double z = -radius; z <= radius; z++) {
                    Location loc = startLocation.clone().add(x, y, z);
                    if (startLocation.distance(loc) <= radius) {
                        Block block = loc.getBlock();

                        if (block.getType().name().toLowerCase().contains("leaves") || block.getType().name().toLowerCase().contains("log")) {
                            block.setType(Material.AIR);
                        }

                        if (block.getType() == Material.GRASS || block.getType() == Material.TALL_GRASS) {
                            block.setType(Material.AIR);
                        }

                        if (block.getType() != Material.AIR && block.getType() != Material.SCULK) {
                            block.setType(Material.SCULK);
                        }

                    }
                }
            }
        }
    }


}

