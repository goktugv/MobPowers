package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Cooldown;
import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Wolf implements Listener {

    private final JavaPlugin plugin;
    Cooldown cd = new Cooldown(30000);
    long cooldownTime = getTimeout("wolf.cooldown");

    public Wolf(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.WOLF_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (cd.has(player, "wolf")) {
                    long remainingTime = cd.remaining(player, "wolf");
                    player.sendMessage(nameColor + "[Kurt] " + ChatColor.RED + "Yeteneği " + remainingTime / 1000 + " saniye sonra kullanabilirsin.");
                    return;
                }

                for (int i = 0; i < getTimeout("wolf.count"); i++) {
                    org.bukkit.entity.Wolf wolf = (org.bukkit.entity.Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
                    wolf.setTamed(true);
                    wolf.setGlowing(true);
                    wolf.setOwner(player);
                    wolf.setAdult();
                }

                player.playSound(player, Sound.ENTITY_WOLF_AMBIENT, 1, 1);
                cd.set(player, "wolf", (cooldownTime) * 1000L);
            }
        }
    }


}

