package com.goktugv.mobpowers.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {
    private HashMap<UUID, HashMap<String, Long>> cooldowns;
    private long defaultCooldown;

    public Cooldown(long defaultCooldown) {
        this.defaultCooldown = defaultCooldown;
        this.cooldowns = new HashMap<>();
    }

    public void set(Player player, String cooldownType, long cooldown) {
        HashMap<String, Long> playerCooldowns = cooldowns.getOrDefault(player.getUniqueId(), new HashMap<>());
        playerCooldowns.put(cooldownType, System.currentTimeMillis() + cooldown);
        cooldowns.put(player.getUniqueId(), playerCooldowns);
    }

    public boolean has(Player player, String cooldownType) {
        if (!cooldowns.containsKey(player.getUniqueId())) return false;
        HashMap<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (!playerCooldowns.containsKey(cooldownType)) return false;
        return playerCooldowns.get(cooldownType) > System.currentTimeMillis();
    }

    public long remaining(Player player, String cooldownType) {
        if (!cooldowns.containsKey(player.getUniqueId())) return 0;
        HashMap<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (!playerCooldowns.containsKey(cooldownType)) return 0;
        long remainingTime = playerCooldowns.get(cooldownType) - System.currentTimeMillis();
        return Math.max(0, remainingTime);
    }


}
