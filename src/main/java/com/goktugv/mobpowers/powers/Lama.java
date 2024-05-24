package com.goktugv.mobpowers.powers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Lama implements Listener {

    private final JavaPlugin plugin;

    public Lama(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    int pR = 10;
    private boolean xdd = true;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        ItemStack item = event.getItem();

        if (xdd) {
            if (item != null && item.getType() == Material.LLAMA_SPAWN_EGG && item.getItemMeta().getCustomModelData() == 1) {
                Location startLocation = event.getPlayer().getEyeLocation();
                Vector direction = startLocation.getDirection();
                Location targetLocation = startLocation.clone().add(direction.clone().multiply(50));

                double distance = startLocation.distance(targetLocation);
                int numberOfParticles = (int) Math.ceil(distance);

                for (int i = 0; i < numberOfParticles; i++) {
                    Location particleLocation = startLocation.clone().add(direction.clone().multiply(i));
                    startLocation.getWorld().spawnParticle(Particle.ITEM_CRACK, particleLocation, 5, 0.2, 0.2, 0.2, 0, new ItemStack(Material.COBWEB));
                    startLocation.getWorld().spawnParticle(Particle.ITEM_CRACK, particleLocation, 5, 0.2, 0.2, 0.2, 0, new ItemStack(Material.COBWEB));

                    List<LivingEntity> livingEntities = getLivingEntities(particleLocation, 2);
                    if (!livingEntities.isEmpty()) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (LivingEntity entity : livingEntities) {
                                    if (!entity.getUniqueId().equals(player.getUniqueId())) {
                                        long damage = getTimeout("llama.extraDamage");
                                        entity.damage((double) damage);
                                    }
                                }
                            }
                        }.runTask(plugin);
                    }
                }

                //player.sendMessage(Long.toString(getTimeout("llama.extraDamage")));
                player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SPIT, 1, 1);

                xdd = false;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        xdd = true;
                    }
                }.runTaskLater(plugin, 1 * 20);

            }
        }
    }

    public List<LivingEntity> getLivingEntities(Location location, double radius) {
        List<LivingEntity> livingEntities = new ArrayList<>();
        for (Entity entity : location.getWorld().getNearbyEntities(location, radius, radius, radius)) {
            if (entity instanceof LivingEntity) {
                livingEntities.add((LivingEntity) entity);
            }
        }
        return livingEntities;
    }

}

