package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Villager implements Listener {

    private static List<ItemStack> items = new ArrayList<>();
    private final JavaPlugin plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 5 * 1000;

    public Villager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void initializeTradeItems() {
        items.add(new ItemStack(Material.ARROW, generateRandomNumber(2, 4)));

        items.add(new ItemStack(Material.APPLE, generateRandomNumber(2, 4)));
        items.add(new ItemStack(Material.CARROT, generateRandomNumber(2, 4)));
        items.add(new ItemStack(Material.BREAD, generateRandomNumber(4, 8)));
        items.add(new ItemStack(Material.BRICK, generateRandomNumber(4, 8)));

        items.add(new ItemStack(Material.TROPICAL_FISH_BUCKET, 1));

        items.add(new ItemStack(Material.ENDER_PEARL, generateRandomNumber(1, 2)));
        items.add(new ItemStack(Material.REDSTONE, generateRandomNumber(1, 2)));
        items.add(new ItemStack(Material.BEETROOT, generateRandomNumber(1, 2)));
        items.add(new ItemStack(Material.EMERALD, generateRandomNumber(1, 2)));
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static ItemStack randomTradeItem() {
        if (items.isEmpty()) {
            initializeTradeItems();
        }
        Random random = new Random();
        int index = random.nextInt(items.size());
        return items.get(index);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.VILLAGER_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (player.getInventory().firstEmpty() == -1) {
                    player.sendMessage(nameColor + "[Köylü] " + ChatColor.RED + "Envanter dolu. ");
                    return;
                }

                if (player.getInventory().contains(Material.EMERALD, 1)) {

                    if (cooldowns.containsKey(player.getUniqueId())) {
                        long secondsLeft = ((cooldowns.get(player.getUniqueId()) + cooldownTime) - System.currentTimeMillis()) / 1000;
                        if (secondsLeft > 0) {
                            if (secondsLeft < (cooldownTime - 2)) {
                                player.sendMessage(nameColor + "[Köylü] " + ChatColor.RED + "Yeteneği " + secondsLeft + " saniye sonra kullanabilirsin.");
                            }
                            return;
                        }
                    }

                    player.getWorld().dropItemNaturally(player.getLocation(), randomTradeItem());
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD, 1));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                    player.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 3);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            cooldowns.remove(player.getUniqueId());
                        }
                    }.runTaskLater(plugin, cooldownTime);
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    player.sendMessage(nameColor + "[Köylü] " + ChatColor.RED + "Envanterde yeterli zümrüt yok.");
                }

            }
        }
    }

}

