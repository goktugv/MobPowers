package com.goktugv.mobpowers;

import com.goktugv.mobpowers.powers.*;
import com.goktugv.mobpowers.utils.Gui;
import com.goktugv.mobpowers.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public final class Main extends JavaPlugin implements CommandExecutor, Listener {

    private String cooldown;
    public static HashMap<Player, Boolean> spider = new HashMap<>();
    public static HashMap<Player, Boolean> strider = new HashMap<>();
    public static HashMap<Player, Boolean> falldamage = new HashMap<>();
    public static HashMap<Player, Boolean> cat = new HashMap<>();
    public static HashMap<Player, Boolean> fly = new HashMap<>();
    public static Map<String, Map<String, Long>> timeoutsData;
    ConsoleCommandSender console = Bukkit.getConsoleSender();
    private String texturePackURL;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        getConfig().options().copyDefaults(true);
        saveConfig();
        texturePackURL = getConfig().getString("texturePackURL");

        loadTimeoutsFromConfig(getConfig());

        this.strider = strider;
        this.spider = spider;
        this.falldamage = falldamage;
        this.cat = cat;
        this.fly = fly;

        ItemChecker itemChecker = new ItemChecker(this);
        Bukkit.getScheduler().runTaskTimer(this, itemChecker, 0, 5);
        //Bukkit.getWorlds().forEach(a -> a.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false));
        getCommand("mobpowers").setExecutor(this);

        getServer().getPluginManager().registerEvents(new Gui(this), this);

        getServer().getPluginManager().registerEvents(new Goat(this), this);
        getServer().getPluginManager().registerEvents(new Cow(this), this);
        getServer().getPluginManager().registerEvents(new Chicken(this), this);
        getServer().getPluginManager().registerEvents(new Snow_Golem(this), this);
        getServer().getPluginManager().registerEvents(new Hoglin(this), this);
        getServer().getPluginManager().registerEvents(new Enderman(this), this);
        getServer().getPluginManager().registerEvents(new Horse(this), this);
        getServer().getPluginManager().registerEvents(new Piglin(this), this);
        getServer().getPluginManager().registerEvents(new Creeper(this), this);
        getServer().getPluginManager().registerEvents(new Iron_Golem(this), this);
        getServer().getPluginManager().registerEvents(new Blaze(this), this);
        getServer().getPluginManager().registerEvents(new Ghast(this), this);
        getServer().getPluginManager().registerEvents(new Cat(this), this);
        getServer().getPluginManager().registerEvents(new Sheep(this), this);
        getServer().getPluginManager().registerEvents(new Strider(this), this);
        getServer().getPluginManager().registerEvents(new Warden(this), this);
        getServer().getPluginManager().registerEvents(new Wolf(this), this);
        getServer().getPluginManager().registerEvents(new Squid(this), this);
        getServer().getPluginManager().registerEvents(new Donkey(this), this);
        getServer().getPluginManager().registerEvents(new Lama(this), this);
        getServer().getPluginManager().registerEvents(new WitherSkeleton(this), this);
        getServer().getPluginManager().registerEvents(new Dragon(this), this);
        getServer().getPluginManager().registerEvents(new Bee(this), this);
        getServer().getPluginManager().registerEvents(new Skeleton(this), this);
        getServer().getPluginManager().registerEvents(new Villager(this), this);

        console.sendMessage(ChatColor.GREEN + "MobPowers hazır.");
    }

    private void loadTimeoutsFromConfig(FileConfiguration config) {
        timeoutsData = new HashMap<>();

        if (config.contains("plugin")) {
            ConfigurationSection timeoutsSection = config.getConfigurationSection("plugin");
            if (timeoutsSection != null) {
                for (String mainKey : timeoutsSection.getKeys(false)) {
                    ConfigurationSection subSection = timeoutsSection.getConfigurationSection(mainKey);
                    if (subSection != null) {
                        Map<String, Long> subMap = new HashMap<>();
                        for (String subKey : subSection.getKeys(false)) {
                            long value = subSection.getLong(subKey);
                            subMap.put(subKey, value);
                            console.sendMessage(ChatColor.GREEN + mainKey + " -> " + subKey + " -> " + value);
                        }
                        timeoutsData.put(mainKey, subMap);
                    }
                }
                console.sendMessage(ChatColor.AQUA + "Tüm veriler başarıyla yüklendi");
            } else {
                console.sendMessage(ChatColor.RED + "plugin verisi tamamen boş. Plugin klasörünü tamamen silip sunucuyu tekrar başlatın.");
            }
        } else {
            console.sendMessage(ChatColor.RED + "HATA! Plugin klasörünü tamamen silip sunucuyu tekrar başlatın.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setResourcePack(texturePackURL);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getAction().name().contains("RIGHT") && event.getItem().getItemMeta().hasCustomModelData() && event.getItem().getType().name().endsWith("_SPAWN_EGG")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();
        if (itemInHand != null && itemInHand.getType().name().endsWith("_SPAWN_EGG")) {
            if (itemInHand.getItemMeta() != null && itemInHand.getItemMeta().hasCustomModelData()) {
                if (itemInHand.getItemMeta().hasCustomModelData() && (itemInHand.getItemMeta().getCustomModelData() == (1))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if ((entity instanceof LivingEntity)) {
            LivingEntity livingEntity = (LivingEntity) entity;
            Player killer = livingEntity.getKiller();
            if (killer != null) {
                EntityType entityType = entity.getType();
                killer.sendMessage(String.valueOf(entityType));
                ItemStack itemStack = PowerItem.generate(entityType);
                if (itemStack != null) {
                    if (!Utils.playerInventoryContainsItem(killer, itemStack)) {
                        entity.getWorld().dropItem(entity.getLocation(), itemStack);
                    }
                }
            }
        }
    }

    @EventHandler
    public void wolf(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();

        if (event.getEntity() != null && damager.getType() == EntityType.WOLF) {
            EntityType entityType = victim.getType();

            ItemStack cookedMeat;
            switch (entityType) {
                case CHICKEN:
                    cookedMeat = new ItemStack(Material.COOKED_CHICKEN);
                    break;
                case COW:
                    cookedMeat = new ItemStack(Material.COOKED_BEEF);
                    break;
                case SHEEP:
                    cookedMeat = new ItemStack(Material.COOKED_MUTTON);
                    break;
                case PIG:
                    cookedMeat = new ItemStack(Material.COOKED_PORKCHOP);
                    break;
                case COD:
                    cookedMeat = new ItemStack(Material.COOKED_COD);
                    break;
                case SALMON:
                    cookedMeat = new ItemStack(Material.COOKED_SALMON);
                    break;
                case RABBIT:
                    cookedMeat = new ItemStack(Material.COOKED_RABBIT);
                    break;
                default:
                    return;
            }

            if (new Random().nextInt(100) < 85) {
                victim.getWorld().dropItem(victim.getLocation(), cookedMeat);
            }
        }

    }


    private boolean isInvalidBlock(Material material) {
        return material != Material.AIR &&
                material != Material.WATER &&
                material != Material.FIRE &&
                material != Material.FLOWER_POT &&
                material != Material.IRON_TRAPDOOR &&
                material != Material.LADDER &&
                material != Material.GRASS &&
                material != Material.TALL_GRASS &&
                material != Material.SEAGRASS &&
                material != Material.TALL_SEAGRASS &&
                material != Material.LAVA &&
                material != Material.LEVER &&
                material != Material.PAINTING &&
                material != Material.ACTIVATOR_RAIL &&
                material != Material.DETECTOR_RAIL &&
                material != Material.POWERED_RAIL &&
                material != Material.RED_MUSHROOM &&
                material != Material.BROWN_MUSHROOM &&
                material != Material.SNOW &&
                material != Material.BLACK_BED &&
                material != Material.WHITE_BED &&
                material != Material.ORANGE_BED &&
                material != Material.MAGENTA_BED &&
                material != Material.LIGHT_BLUE_BED &&
                material != Material.YELLOW_BED &&
                material != Material.LIME_BED &&
                material != Material.PINK_BED &&
                material != Material.GRAY_BED &&
                material != Material.LIGHT_GRAY_BED &&
                material != Material.CYAN_BED &&
                material != Material.PURPLE_BED &&
                material != Material.BLUE_BED &&
                material != Material.BROWN_BED &&
                material != Material.GREEN_BED &&
                material != Material.RED_BED &&
                material != Material.WHITE_CARPET &&
                material != Material.ORANGE_CARPET &&
                material != Material.MAGENTA_CARPET &&
                material != Material.LIGHT_BLUE_CARPET &&
                material != Material.YELLOW_CARPET &&
                material != Material.LIME_CARPET &&
                material != Material.PINK_CARPET &&
                material != Material.GRAY_CARPET &&
                material != Material.LIGHT_GRAY_CARPET &&
                material != Material.CYAN_CARPET &&
                material != Material.PURPLE_CARPET &&
                material != Material.BLUE_CARPET &&
                material != Material.BROWN_CARPET &&
                material != Material.GREEN_CARPET &&
                material != Material.RED_CARPET &&
                material != Material.BLACK_CARPET &&
                material != Material.OAK_SAPLING &&
                material != Material.SPRUCE_SAPLING &&
                material != Material.BIRCH_SAPLING &&
                material != Material.JUNGLE_SAPLING &&
                material != Material.ACACIA_SAPLING &&
                material != Material.DARK_OAK_SAPLING &&
                material != Material.DANDELION &&
                material != Material.POPPY &&
                material != Material.BLUE_ORCHID &&
                material != Material.ALLIUM &&
                material != Material.AZURE_BLUET &&
                material != Material.RED_TULIP &&
                material != Material.ORANGE_TULIP &&
                material != Material.WHITE_TULIP &&
                material != Material.PINK_TULIP &&
                material != Material.OXEYE_DAISY &&
                material != Material.CORNFLOWER &&
                material != Material.LILY_OF_THE_VALLEY &&
                material != Material.SUNFLOWER &&
                material != Material.LILAC &&
                material != Material.ROSE_BUSH &&
                material != Material.PEONY &&
                material != Material.WHEAT &&
                material != Material.CARROTS &&
                material != Material.POTATOES &&
                material != Material.BEETROOTS;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        int intPlayerX = p.getLocation().getBlockX();
        int intPlayerY = p.getLocation().getBlockY();
        int intPlayerZ = p.getLocation().getBlockZ();
        double dubPlayerX = p.getLocation().getX();
        double dubPlayerZ = p.getLocation().getZ();

        if (spider.containsKey(p)) {
            if (spider.get(p)) {
                if (!p.isOnGround()) {
                    Material blockTypeXPlus = p.getWorld().getBlockAt(new Location(p.getWorld(), intPlayerX + 1, intPlayerY, intPlayerZ)).getType();
                    if (dubPlayerX - intPlayerX >= 0.69D && isInvalidBlock(blockTypeXPlus)) {
                        if (Utils.getRandom(1, 11) < 10)
                            p.setVelocity(new Vector(0.0D, 0.3D, 0.0D));
                    } else if (dubPlayerX - intPlayerX <= 0.31D) {
                        Material blockTypeXMinus = p.getWorld().getBlockAt(new Location(p.getWorld(), intPlayerX - 1, intPlayerY, intPlayerZ)).getType();
                        if (isInvalidBlock(blockTypeXMinus)) {
                            if (Utils.getRandom(1, 11) < 10)
                                p.setVelocity(new Vector(0.0D, 0.2D, 0.0D));
                        }
                    } else if (dubPlayerZ - intPlayerZ >= 0.69D) {
                        Material blockTypeZPlus = p.getWorld().getBlockAt(new Location(p.getWorld(), intPlayerX, intPlayerY, intPlayerZ + 1)).getType();
                        if (isInvalidBlock(blockTypeZPlus)) {
                            if (Utils.getRandom(1, 11) < 10)
                                p.setVelocity(new Vector(0.0D, 0.2D, 0.0D));
                        }
                    } else if (dubPlayerZ - intPlayerZ <= 0.31D) {
                        Material blockTypeZMinus = p.getWorld().getBlockAt(new Location(p.getWorld(), intPlayerX, intPlayerY, intPlayerZ - 1)).getType();
                        if (isInvalidBlock(blockTypeZMinus)) {
                            if (Utils.getRandom(1, 11) < 10)
                                p.setVelocity(new Vector(0.0D, 0.2D, 0.0D));
                        }
                    }
                }
            }
        } else {
            spider.put(p, false);
        }
    }

    private boolean isSapling(Material material) {
        switch (material) {
            case ACACIA_SAPLING:
            case BIRCH_SAPLING:
            case DARK_OAK_SAPLING:
            case JUNGLE_SAPLING:
            case OAK_SAPLING:
            case SPRUCE_SAPLING:
                return false;
            default:
                return true;
        }
    }


    public boolean isNonFlowerBed(Material blockType) {
        return blockType != Material.GRASS && blockType != Material.TALL_GRASS && !blockType.name().toLowerCase().contains("_BED");
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            for (Entity passenger : player.getPassengers()) {
                passenger.leaveVehicle();
            }
        }
    }

    @EventHandler
    public void onPlayerFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (falldamage.containsKey(player)) {
                if (falldamage.get(player)) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void Lava(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        final int radius = 2;
        if (strider.containsKey(player)) {
            if (((Boolean) strider.get(player)).booleanValue()) {
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            Location loc = playerLocation.clone().add(x, y, z);
                            Block block = loc.getBlock();
                            if (block.getType() == Material.LAVA) {
                                block.setType(Material.ORANGE_STAINED_GLASS);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (block.getType() == Material.ORANGE_STAINED_GLASS) {
                                            block.setType(Material.LAVA);
                                        }
                                    }
                                }.runTaskLater(this, 60);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Yanlış kullanım: /mobpowers config/reload/items");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "gui":
                Gui.open(player);
                break;
            case "reload":
                reloadConfig();
                loadTimeoutsFromConfig(getConfig());
                player.sendMessage(ChatColor.GREEN + "Veriler yenilendi.");
                break;
            case "config":
                Utils.sendDatas(player, timeoutsData);
                break;
            case "items":
                if (args.length == 1) {
                    for (PowerItem powerItem : PowerItem.values()) {
                        ItemStack itemStack = powerItem.generate();
                        player.getInventory().addItem(itemStack);
                    }
                } else {
                    try {
                        ItemStack itemStack = PowerItem.valueOf(args[1].toUpperCase()).generate();
                        player.getInventory().addItem(itemStack);
                    } catch (Exception e) {

                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("gui");
            completions.add("items");
            completions.add("reload");
            completions.add("config");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("items")) {
            for (PowerItem powerItem : PowerItem.values()) {
                completions.add(String.valueOf(powerItem));
            }
        }
        return completions;
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Boolean isSpider = spider.get(p);
        if (isSpider != null && isSpider.booleanValue()) {
            spider.put(p, false);
        }

        Boolean isFallDamage = falldamage.get(p);
        if (isFallDamage != null && isFallDamage.booleanValue()) {
            falldamage.put(p, false);
        }

        Boolean isStrider = strider.get(p);
        if (isStrider != null && isStrider.booleanValue()) {
            strider.put(p, false);
        }

        Boolean isFly = fly.get(p);
        if (isFly != null && isFly.booleanValue()) {
            fly.put(p, false);
        }

        p.setAllowFlight(false);
        p.setFlying(false);
    }


}
