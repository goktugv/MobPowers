package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Cooldown;
import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static com.goktugv.mobpowers.Main.fly;
import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Bee implements Listener {

    private final JavaPlugin plugin;
    Cooldown cd = new Cooldown(30000);
    long flytime = getTimeout("bee.fly");
    long cooldownTime = getTimeout("bee.cooldown");
    private boolean canShoot = true;
    private BukkitTask flyTask;

    public Bee(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity target = (LivingEntity) event.getEntity();

            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            ItemStack offHandItem = player.getInventory().getItemInOffHand();

            if ((mainHandItem.getType() == Material.BEE_SPAWN_EGG && mainHandItem.getItemMeta().getCustomModelData() == 1) ||
                    (offHandItem.getType() == Material.BEE_SPAWN_EGG && offHandItem.getItemMeta().getCustomModelData() == 1)) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2 * 20, 1));
            }

        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item;

        item = player.getInventory().getItemInMainHand();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) { // yaptım
            if (item != null && item.getType() == Material.BEE_SPAWN_EGG) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.getCustomModelData() == 1) {
                    String nameColor = Utils.getItemNameColor(item);

                    if (cd.has(player, "ari_fly")) {
                        long remainingTime = cd.remaining(player, "ari_fly");
                        player.sendMessage(nameColor + "[Arı] " + ChatColor.RED + "Yeteneği " + remainingTime / 1000 + " saniye sonra kullanabilirsin.");
                        return;
                    }

                    cd.set(player, "ari_fly", (cooldownTime) * 1000L);
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    fly.put(player, Boolean.valueOf(true));
                    flying(player, item);
                }
            }
        }
    }

    private void flying(Player player, ItemStack item) {
        String nameColor = Utils.getItemNameColor(item);
        player.sendMessage(nameColor + "[Arı] " + ChatColor.GREEN + flytime + " saniye boyunca uçma yeteneğin aktif.");
        flyTask = new BukkitRunnable() {
            @Override
            public void run() {
                fly.remove(player);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(nameColor + "[Arı] " + ChatColor.RED + "Uçma yeteneğini " + cooldownTime + " saniye sonra tekrar kullanabilirsin.");
            }
        }.runTaskLater(plugin, flytime * 20);
    }

}

