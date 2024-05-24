package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Cooldown;
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
import org.bukkit.util.Vector;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Ghast implements Listener {

    private final JavaPlugin plugin;

    Cooldown cd = new Cooldown(30000);
    long cooldownTime = getTimeout("ghast.cooldown");

    public Ghast(JavaPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.GHAST_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasCustomModelData() && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (cd.has(player, "ghast")) {
                    long remainingTime = cd.remaining(player, "ghast");
                    player.sendMessage(nameColor + "[Ghast] " + ChatColor.RED + "Yeteneği " + remainingTime / 1000 + " saniye sonra kullanabilirsin.");
                    return;
                }

                Location eyeLocation = player.getEyeLocation();
                Vector direction = eyeLocation.getDirection();

                Fireball fireball = player.launchProjectile(Fireball.class);
                fireball.setIsIncendiary(true);
                fireball.setYield(3);

                cd.set(player, "ghast", (cooldownTime) * 1000L);
                player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 1);
            }
        }
    }


}

