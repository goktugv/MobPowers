package com.goktugv.mobpowers.powers;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class WitherSkeleton implements Listener {

    private final JavaPlugin plugin;

    public WitherSkeleton(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity target = (LivingEntity) event.getEntity();

            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            ItemStack offHandItem = player.getInventory().getItemInOffHand();

            if ((mainHandItem.getType() == Material.WITHER_SKELETON_SPAWN_EGG && mainHandItem.getItemMeta().getCustomModelData() == 1) || (offHandItem.getType() == Material.WITHER_SKELETON_SPAWN_EGG && offHandItem.getItemMeta().getCustomModelData() == 1)) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) (getTimeout("wither_skeleton.damageEffectSeconds") * 20), 1));
            }

        }
    }


}

