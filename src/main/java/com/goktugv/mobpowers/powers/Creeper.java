package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Creeper implements Listener {

    private final JavaPlugin plugin;

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    long cooldownTime = getTimeout("creeper.cooldown") * 1000L;

    public Creeper(JavaPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.CREEPER_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (cooldowns.containsKey(player.getUniqueId())) {
                    long secondsLeft = ((cooldowns.get(player.getUniqueId()) + cooldownTime) - System.currentTimeMillis()) / 1000;
                    if (secondsLeft > 0) {
                        if (secondsLeft < cooldownTime - 2) {
                            player.sendMessage(nameColor + "[Creeper] " + ChatColor.RED + "Yeteneği " + secondsLeft + " saniye sonra kullanabilirsin.");
                        }
                        return;
                    }
                }

                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int) getTimeout("creeper.resistance") * 20, 99));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) getTimeout("creeper.regeneration") * 20, 99));

                Vector currentVelocity = player.getVelocity();
                player.getWorld().createExplosion(player.getLocation(), getTimeout("creeper.explosion"), false, true);
                player.setVelocity(new Vector(currentVelocity.getX(), getTimeout("creeper.launchspeed"), currentVelocity.getZ()));
                player.getWorld().createExplosion(player.getLocation(), 3.0f, false, false);

                event.setCancelled(true);
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

}

