package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Enderman implements Listener {

    private final JavaPlugin plugin;

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = getTimeout("enderman.cooldown") * 1000L;

    public Enderman(JavaPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.ENDERMAN_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (cooldowns.containsKey(player.getUniqueId())) {
                    long secondsLeft = ((cooldowns.get(player.getUniqueId()) + cooldownTime) - System.currentTimeMillis()) / 1000;
                    if (secondsLeft > 0) {
                        if (secondsLeft < (cooldownTime - 2)) {
                            player.sendMessage(nameColor + "[Enderman] " + ChatColor.RED + "Yeteneği " + secondsLeft + " saniye sonra kullanabilirsin.");
                        }
                        return;
                    }
                }

                if (player.getWorld().getName().equals("world_nether")) {
                    Location randomLocation = getRandomLocationInNether(player.getLocation(), 40, 80);
                    player.teleport(randomLocation);
                    player.getWorld().spawnParticle(Particle.PORTAL, randomLocation, 30);
                    player.playSound(randomLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                } else {
                    Location randomLocation = getRandomLocation(player.getLocation(), 15);
                    player.teleport(randomLocation);
                    player.getWorld().spawnParticle(Particle.PORTAL, randomLocation, 30);
                    player.playSound(randomLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                }

                cooldowns.put(player.getUniqueId(), System.currentTimeMillis());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        cooldowns.remove(player.getUniqueId());
                    }
                }.runTaskLater(plugin, cooldownTime);
            }
        }
        return;
    }

    private Location getRandomLocationInNether(Location center, int minY, int maxY) {
        Random random = new Random();
        int x = center.getBlockX() + random.nextInt(64) - 32;
        int z = center.getBlockZ() + random.nextInt(64) - 32;
        int y = minY + random.nextInt(maxY - minY + 1);
        return new Location(center.getWorld(), x, y, z);
    }


    private Location getRandomLocation(Location center, int radius) {
        Random random = new Random();
        int x = center.getBlockX() + random.nextInt(radius * 2) - radius;
        int z = center.getBlockZ() + random.nextInt(radius * 2) - radius;
        int y = center.getWorld().getHighestBlockYAt(x, z);
        return new Location(center.getWorld(), x, y + 2, z);
    }

}

