package com.goktugv.mobpowers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.goktugv.mobpowers.Main.*;

public class ItemChecker implements Runnable {

    private final JavaPlugin plugin;

    public ItemChecker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Material mainHandItem = player.getInventory().getItemInMainHand().getType();
            Material offHandItem = player.getInventory().getItemInOffHand().getType();
            if (mainHandItem != null && offHandItem != null) {

                if (mainHandItem == Material.FROG_SPAWN_EGG || offHandItem == Material.FROG_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2 * 20, 2));
                }

                if (mainHandItem == Material.RABBIT_SPAWN_EGG || offHandItem == Material.RABBIT_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2 * 20, 4));
                }

                if (mainHandItem == Material.HORSE_SPAWN_EGG || offHandItem == Material.HORSE_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2 * 20, 0));
                }

                if (mainHandItem == Material.DOLPHIN_SPAWN_EGG || offHandItem == Material.DOLPHIN_SPAWN_EGG || mainHandItem == Material.COD_SPAWN_EGG || offHandItem == Material.COD_SPAWN_EGG || mainHandItem == Material.SALMON_SPAWN_EGG || offHandItem == Material.SALMON_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2 * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 2 * 20, 1));
                }

                if (mainHandItem == Material.GLOW_SQUID_SPAWN_EGG || offHandItem == Material.GLOW_SQUID_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 2 * 20, 0));
                }

                if (mainHandItem == Material.CHICKEN_SPAWN_EGG || offHandItem == Material.CHICKEN_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 2 * 20, 0));
                }

                if (mainHandItem == Material.MAGMA_CUBE_SPAWN_EGG || offHandItem == Material.MAGMA_CUBE_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2 * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2 * 20, 0));
                }

                if (mainHandItem == Material.SQUID_SPAWN_EGG || offHandItem == Material.SQUID_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2 * 20, 0));
                }

                if (mainHandItem == Material.STRIDER_SPAWN_EGG || offHandItem == Material.STRIDER_SPAWN_EGG || mainHandItem == Material.BLAZE_SPAWN_EGG || offHandItem == Material.BLAZE_SPAWN_EGG || mainHandItem == Material.GHAST_SPAWN_EGG || offHandItem == Material.GHAST_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2 * 20, 0));
                }

                if (mainHandItem == Material.IRON_GOLEM_SPAWN_EGG || offHandItem == Material.IRON_GOLEM_SPAWN_EGG) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 0));
                }

                if (mainHandItem == Material.SPIDER_SPAWN_EGG || offHandItem == Material.SPIDER_SPAWN_EGG) {
                    spider.put(player, Boolean.valueOf(true));
                } else {
                    spider.put(player, Boolean.valueOf(false));
                }

                if (mainHandItem == Material.STRIDER_SPAWN_EGG || offHandItem == Material.STRIDER_SPAWN_EGG) {
                    strider.put(player, Boolean.valueOf(true));
                } else {
                    strider.put(player, Boolean.valueOf(false));
                }

                if (mainHandItem == Material.MAGMA_CUBE_SPAWN_EGG || offHandItem == Material.MAGMA_CUBE_SPAWN_EGG || mainHandItem == Material.CHICKEN_SPAWN_EGG || offHandItem == Material.CHICKEN_SPAWN_EGG || mainHandItem == Material.FROG_SPAWN_EGG || offHandItem == Material.FROG_SPAWN_EGG || mainHandItem == Material.CAT_SPAWN_EGG || offHandItem == Material.CAT_SPAWN_EGG || mainHandItem == Material.IRON_GOLEM_SPAWN_EGG || offHandItem == Material.IRON_GOLEM_SPAWN_EGG || mainHandItem == Material.WARDEN_SPAWN_EGG || offHandItem == Material.WARDEN_SPAWN_EGG) {
                    falldamage.put(player, Boolean.valueOf(true));
                } else {
                    falldamage.put(player, Boolean.valueOf(false));
                }

                if (mainHandItem == Material.CAT_SPAWN_EGG || offHandItem == Material.CAT_SPAWN_EGG) {
                    cat.put(player, Boolean.valueOf(true));
                } else {
                    cat.put(player, Boolean.valueOf(false));
                }

                if (mainHandItem == Material.GHAST_SPAWN_EGG || offHandItem == Material.GHAST_SPAWN_EGG || mainHandItem == Material.PARROT_SPAWN_EGG || offHandItem == Material.PARROT_SPAWN_EGG) {
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                    }
                } else {
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        if (fly.get(player) == null) {
                            player.setAllowFlight(false);
                            player.setFlying(false);
                        }
                    }
                }


            }
        }
    }


}
