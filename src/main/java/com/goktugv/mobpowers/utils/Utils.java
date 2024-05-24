package com.goktugv.mobpowers.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Random;

import static com.goktugv.mobpowers.Main.timeoutsData;

public class Utils {

    public static String getItemNameColor(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            String displayName = meta.getDisplayName();
            String colorCode = ChatColor.getLastColors(displayName);
            return colorCode.isEmpty() ? null : colorCode;
        }
        return null;
    }

    public static int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt(upper - lower + 1) + lower;
    }

    public static boolean playerInventoryContainsItem(Player player, ItemStack itemStack) {
        return player.getInventory().contains(itemStack);
    }

    public static String translateColorCodes(String text) {
        String[] texts = text.split(String.format("((?<=%1$s)|(?=%1$s))", "&"));
        StringBuilder finalText = new StringBuilder();
        for (int i = 0; i < texts.length; i++) {
            if (texts[i].equalsIgnoreCase("&")) {
                i++;
                if (texts[i].charAt(0) == '#') {
                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)) + texts[i].substring(7));
                } else {
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            } else {
                finalText.append(texts[i]);
            }
        }
        return finalText.toString();
    }

    public static long getTimeout(String key) {
        if (timeoutsData == null) {
            throw new IllegalStateException("Timeouts data not loaded yet.");
        }

        String[] keys = key.split("\\.");
        if (keys.length != 2) {
            throw new IllegalArgumentException("Key format is not valid. Use 'mainKey.subKey' format.");
        }
        String mainKey = keys[0];
        String subKey = keys[1];

        Map<String, Long> subMap = timeoutsData.get(mainKey);
        if (subMap == null) {
            throw new IllegalArgumentException("Main key not found: " + mainKey);
        }

        Long value = subMap.get(subKey);
        if (value == null) {
            throw new IllegalArgumentException("Sub key not found: " + subKey);
        }

        return value;
    }

    public static void sendDatas(Player player, Map<String, Map<String, Long>> timeoutsData) {
        if (timeoutsData == null) {
            throw new IllegalStateException("Timeouts data not loaded yet.");
        }
        for (Map.Entry<String, Map<String, Long>> entry : timeoutsData.entrySet()) {
            String mainKey = entry.getKey();
            Map<String, Long> subMap = entry.getValue();
            player.sendMessage(ChatColor.AQUA + mainKey + ":");
            for (Map.Entry<String, Long> subEntry : subMap.entrySet()) {
                String subKey = subEntry.getKey();
                long value = subEntry.getValue();

                player.sendMessage(ChatColor.GRAY + " • " + subKey + " -> " + value);
            }
        }
    }

    public static Location generateRandomLocation() {
        Random random = new Random();
        double x = -100 + (200 * random.nextDouble()); // X koordinatı -100 ile 100 arasında rastgele
        double z = -100 + (200 * random.nextDouble()); // Z koordinatı -100 ile 100 arasında rastgele
        double y = Bukkit.getWorlds().get(0).getHighestBlockYAt((int) x, (int) z); // Y koordinatını en yüksek blok yüksekliği yap
        return new Location(Bukkit.getWorlds().get(0), x, y, z);
    }

    public void playSoundToAll(Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }

}
