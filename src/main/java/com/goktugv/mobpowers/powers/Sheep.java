package com.goktugv.mobpowers.powers;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import static com.goktugv.mobpowers.utils.Utils.getTimeout;

public class Sheep implements Listener {

    private final JavaPlugin plugin;

    public Sheep(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (item != null && item.getType() == Material.SHEEP_SPAWN_EGG) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getCustomModelData() == 1) {
                Player player = event.getPlayer();
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock != null && clickedBlock.getType() == Material.GRASS_BLOCK) {
                    clickedBlock.setType(Material.DIRT);
                    player.playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 1);
                    float currentHunger = player.getFoodLevel();

                    if (currentHunger < 20) {
                        player.setFoodLevel((int) Math.min(currentHunger + getTimeout("sheep.addSaturation"), 20));
                    }
                }
            }
        }
    }


}

