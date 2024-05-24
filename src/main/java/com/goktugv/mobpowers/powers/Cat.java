package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;


public class Cat implements Listener {

    private final JavaPlugin plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = getTimeout("cat.cooldown") * 1000L;
    long effectTime = getTimeout("cat.speed");
    private boolean canShoot = true;

    public Cat(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.CAT_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (cooldowns.containsKey(player.getUniqueId())) {
                    long secondsLeft = ((cooldowns.get(player.getUniqueId()) + cooldownTime) - System.currentTimeMillis()) / 1000;
                    if (secondsLeft > 0) {
                        if (secondsLeft < (cooldownTime - 2)) {
                            player.sendMessage(nameColor + "[Kedi] " + ChatColor.RED + "Yeteneği " + secondsLeft + " saniye sonra kullanabilirsin.");
                        }
                        return;
                    }
                }

                if (canShoot) {
                    player.sendMessage(nameColor + "[Kedi] " + ChatColor.GREEN + effectTime + " saniye boyunca hız yeteneğin aktif.");
                    canShoot = false;

                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_PURREOW, 1, 1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (effectTime * 20L), 2));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            cooldowns.put(player.getUniqueId(), System.currentTimeMillis());

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    cooldowns.remove(player.getUniqueId());
                                }
                            }.runTaskLater(plugin, cooldownTime);

                            player.setAllowFlight(false);
                            player.sendMessage(nameColor + "[Kedi] " + ChatColor.RED + "Hız yeteneğini " + cooldownTime + " saniye sonra tekrar kullanabilirsin.");
                            canShoot = true;
                        }
                    }.runTaskLater(plugin, effectTime * 20L);

                }

            }
        }
    }


}

