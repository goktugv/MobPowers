package com.goktugv.mobpowers.powers;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Hoglin implements Listener {

    private final JavaPlugin plugin;

    public Hoglin(JavaPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity target = (LivingEntity) event.getEntity();

            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            ItemStack offHandItem = player.getInventory().getItemInOffHand();

            if ((mainHandItem.getType() == Material.HOGLIN_SPAWN_EGG && mainHandItem.getItemMeta().getCustomModelData() == 1) ||
                    (offHandItem.getType() == Material.HOGLIN_SPAWN_EGG && offHandItem.getItemMeta().getCustomModelData() == 1)) {

                double knockbackStrength = getTimeout("hoglin.knockback");
                Vector direction = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                direction.multiply(knockbackStrength);
                target.setVelocity(direction);

                double extraDamage = getTimeout("hoglin.extraDamage");
                double originalDamage = event.getDamage();
                double newDamage = originalDamage + extraDamage;

                event.setDamage(newDamage);
            }
        }
    }


}

