package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Blaze implements Listener {

    private final JavaPlugin plugin;
    private final Map<UUID, Integer> shot = new HashMap<>();
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 2 * 60 * 1000;
    long shotCooldown = getTimeout("blaze.shoot");
    private boolean canShoot = true;

    public Blaze(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.BLAZE_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (canShoot) {
                    player.sendMessage(nameColor + "[Blaze] " + ChatColor.GREEN + "Ateş topu yeteneğini tekrar kullanabilmek için " + shotCooldown + " saniye bekle.");

                    Location eyeLocation = player.getEyeLocation();
                    Vector direction = eyeLocation.getDirection();
                    Fireball fireball = player.launchProjectile(Fireball.class);
                    fireball.setIsIncendiary(false);
                    fireball.setYield(1);
                    player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setAllowFlight(false);
                            player.sendMessage(nameColor + "[Blaze] " + ChatColor.GRAY + "Ateş topu yeteneğini tekrar kullanabilirsin.");
                            canShoot = true;

                            cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
                        }
                    }.runTaskLater(plugin, shotCooldown * 20L);

                    canShoot = false;
                }
            }
        }
    }
}
