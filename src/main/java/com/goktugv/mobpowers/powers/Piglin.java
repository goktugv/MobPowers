package com.goktugv.mobpowers.powers;

import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Piglin implements Listener {

    private final JavaPlugin plugin;
    private static List<ItemStack> piglinTradeItems = new ArrayList<>();

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 5 * 1000;

    public Piglin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void initializePiglinTradeItems() {
        piglinTradeItems.add(new ItemStack(Material.ENDER_PEARL, generateRandomNumber(2, 4)));
        piglinTradeItems.add(new ItemStack(Material.OBSIDIAN, generateRandomNumber(2, 4)));
        piglinTradeItems.add(new ItemStack(Material.CRYING_OBSIDIAN, 2));
        piglinTradeItems.add(new ItemStack(Material.IRON_NUGGET, generateRandomNumber(8, 16)));
        piglinTradeItems.add(new ItemStack(Material.STRING, generateRandomNumber(8, 16)));
        piglinTradeItems.add(new ItemStack(Material.FIRE_CHARGE, generateRandomNumber(1, 2)));
        piglinTradeItems.add(new ItemStack(Material.GRAVEL, 8));
        piglinTradeItems.add(new ItemStack(Material.NETHER_BRICK, generateRandomNumber(8, 16)));
        piglinTradeItems.add(new ItemStack(Material.LEATHER, generateRandomNumber(4, 8)));
        piglinTradeItems.add(new ItemStack(Material.SOUL_SAND, generateRandomNumber(6, 12)));

        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, true, false));
        potion.setItemMeta(meta);
        piglinTradeItems.add(potion);

        ItemStack soulSpeedBoots = new ItemStack(Material.IRON_BOOTS);
        soulSpeedBoots.addEnchantment(Enchantment.SOUL_SPEED, 1);
        piglinTradeItems.add(soulSpeedBoots);
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static ItemStack getRandomPiglinTradeItem() {
        if (piglinTradeItems.isEmpty()) {
            initializePiglinTradeItems();
        }
        Random random = new Random();
        int index = random.nextInt(piglinTradeItems.size());
        return piglinTradeItems.get(index);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.PIGLIN_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                String nameColor = Utils.getItemNameColor(item);

                if (player.getInventory().firstEmpty() == -1) {
                    player.sendMessage(nameColor + "[Piglin] " + ChatColor.RED + "Envanter dolu. ");
                    return;
                }

                if (player.getInventory().contains(Material.GOLD_INGOT, 1)) {

                    if (cooldowns.containsKey(player.getUniqueId())) {
                        long secondsLeft = ((cooldowns.get(player.getUniqueId()) + cooldownTime) - System.currentTimeMillis()) / 1000;
                        if (secondsLeft > 0) {
                            if (secondsLeft < (cooldownTime - 2)) {
                                player.sendMessage(nameColor + "[Piglin] " + ChatColor.RED + "Yeteneği " + secondsLeft + " saniye sonra kullanabilirsin.");
                            }
                            return;
                        }
                    }

                    player.getWorld().dropItemNaturally(player.getLocation(), getRandomPiglinTradeItem());
                    player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 1));
                    player.playSound(player.getLocation(), Sound.ENTITY_PIGLIN_JEALOUS, 1, 1);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            cooldowns.remove(player.getUniqueId());
                        }
                    }.runTaskLater(plugin, cooldownTime);
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_PIGLIN_ANGRY, 1, 1);
                    player.sendMessage(nameColor + "[Piglin] " + ChatColor.RED + "Envanterde yeterli altın yok.");

                }

            }
        }
    }

}

