package com.goktugv.mobpowers;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public enum PowerItem {
    CHICKEN(EntityType.CHICKEN, Material.CHICKEN_SPAWN_EGG, 1, ChatColor.WHITE + "Tavuk Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Yüksekten düşünce hasar almama",
            ChatColor.GRAY + "- Yavaş Düşüş efekti"
    }),

    ENDER_DRAGON(EntityType.ENDER_DRAGON, Material.ENDER_DRAGON_SPAWN_EGG, 1, ChatColor.LIGHT_PURPLE + "Ejderha Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Ejderha saldırısı",
            "",
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Uçabilrsin"
    }),

    BEE(EntityType.BEE, Material.BEE_SPAWN_EGG, 1, ChatColor.YELLOW + "Arı Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler:",
            //ChatColor.GRAY + " - 5 saniyelik uçabilirsin",
            ChatColor.GRAY + " - Vurduğun canlıları zehirleyebilirsin",
    }),

    COW(EntityType.COW, Material.COW_SPAWN_EGG, 1, ChatColor.GREEN + "İnek Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler:",
            ChatColor.GRAY + " - Tüm aktif iksir efektlerini siler"
    }),

    SPIDER(EntityType.SPIDER, Material.SPIDER_SPAWN_EGG, 1, ChatColor.DARK_GRAY + "Örümcek Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip yetenekler: ",
            ChatColor.GRAY + "- Duvarlara tırmanabilirsin",
    }),
    ENDERMAN(EntityType.ENDERMAN, Material.ENDERMAN_SPAWN_EGG, 1, ChatColor.DARK_PURPLE + "Enderman Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Işınlanabilirsin"
    }),

    CREEPER(EntityType.CREEPER, Material.CREEPER_SPAWN_EGG, 1, ChatColor.DARK_GREEN + "Creeper Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Dev creeper patlaması yaratabilirsin"
    }),

    BLAZE(EntityType.BLAZE, Material.BLAZE_SPAWN_EGG, 1, ChatColor.GOLD + "Blaze Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Ateş direnci efekti"
    }),

    IRON_GOLEM(EntityType.IRON_GOLEM, Material.IRON_GOLEM_SPAWN_EGG, 1, ChatColor.GRAY + "Demir Golem Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- 10 saniyelik Kuvvet efekti",
            ChatColor.GRAY + "- 10 saniyelik Yavaşlık efekti",
            "",
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler:",
            ChatColor.GRAY + "- Düşme hasarını engeller"
    }),

    FROG(EntityType.RABBIT, Material.FROG_SPAWN_EGG, 1, ChatColor.GOLD + "Kurbağa Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- 3. seviye Zıplama desteği efekti",
            ChatColor.GRAY + "- Düşme hasarını engeller"
    }),

    MAGMA_CUBE(EntityType.MAGMA_CUBE, Material.MAGMA_CUBE_SPAWN_EGG, 1, ChatColor.GREEN + "Magma Küp Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Zıplama Desteği efekti",
            ChatColor.GRAY + "- Ateş Direnci efekti",
            ChatColor.GRAY + "- Düşme hasarını engeller"
    }),

    CAT(EntityType.CAT, Material.CAT_SPAWN_EGG, 1, ChatColor.GREEN + "Kedi Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- 1 saniyelik 10. seviye Hız 5 efekti",
            ChatColor.GRAY + "- Düşme hasarını engeller"
    }),

    HORSE(EntityType.HORSE, Material.HORSE_SPAWN_EGG, 1, ChatColor.of("#964B00") + "At Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Canlıları kafana bindirebilirsin",
            "",
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Hız efekti"
    }),

    RABBIT(EntityType.RABBIT, Material.RABBIT_SPAWN_EGG, 1, ChatColor.GREEN + "Tavşan Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- 5. seviye Zıplama desteği efekti",
            ChatColor.GRAY + "- Düşme hasarını engeller"
    }),

    SNOWMAN(EntityType.SNOWMAN, Material.SNOW_GOLEM_SPAWN_EGG, 1, ChatColor.GREEN + "Kar Golemi Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Kar topu fırlatabilirsin",
    }),

    GHAST(EntityType.GHAST, Material.GHAST_SPAWN_EGG, 1, ChatColor.WHITE + "Ghast Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Ateş topu fırlatabilirsin",
            "",
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Uçabilirsin"
    }),


    SHEEP(EntityType.SHEEP, Material.SHEEP_SPAWN_EGG, 1, ChatColor.WHITE + "Koyun Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Çimen bloğuyla doygunluk efekti alabilirsin"
    }),

    WARDEN(EntityType.WARDEN, Material.WARDEN_SPAWN_EGG, 1, ChatColor.DARK_BLUE + "Warden Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Etrafı sculklar kaplar",
            ChatColor.GRAY + "- Rastgele WARDEN spawnlanır",
            "",
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Düşme hasarını engeller"
    }),

    DOLPHIN(EntityType.DOLPHIN, Material.DOLPHIN_SPAWN_EGG, 1, ChatColor.AQUA + "Yunus Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "-  Yunusun Lütfu efekti",
            "",
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "-  Su Altında Nefes Alma efekti",
    }),

    GLOW_SQUID(EntityType.GLOW_SQUID, Material.GLOW_SQUID_SPAWN_EGG, 1, ChatColor.DARK_BLUE + "Parlayan Mürekkep Balığı Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "-   ",
            "",
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "-   ",
    }),

    HOGLIN(EntityType.HOGLIN, Material.HOGLIN_SPAWN_EGG, 1, ChatColor.YELLOW + "Hoglin Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Vurduğun canlı ekstra itilir",
    }),

    SQUID(EntityType.SQUID, Material.SQUID_SPAWN_EGG, 1, ChatColor.BLUE + "Mürekkep Balığı Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- :)",
    }),

    DONKEY(EntityType.DONKEY, Material.DONKEY_SPAWN_EGG, 1, ChatColor.YELLOW + "Eşek Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Canlıları kafana bindirebilirsin",
    }),

    COD(EntityType.COD, Material.COD_SPAWN_EGG, 1, ChatColor.YELLOW + "Morina Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "-  Su Altında Nefes Alma efekti ",
    }),

    LLAMA(EntityType.LLAMA, Material.LLAMA_SPAWN_EGG, 1, ChatColor.WHITE + "LLama Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "-  Canlılara tükürebilirsin",
    }),

    TRADER_LLAMA(EntityType.TRADER_LLAMA, Material.LLAMA_SPAWN_EGG, 1, ChatColor.WHITE + "LLama Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "-  Canlılara tükürebilirsin",
    }),

    SALMON(EntityType.SALMON, Material.SALMON_SPAWN_EGG, 1, ChatColor.RED + "Salmon Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "-  Su Altında Nefes Alma efekti",
    }),
    STRIDER(EntityType.STRIDER, Material.STRIDER_SPAWN_EGG, 1, ChatColor.RED + "Lavgezer Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Canlıları kafana bindirebilirsin",
            "",
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Lav üzerinde yürüyebilirsin",
            ChatColor.GRAY + "- Ateş Direnci efekti",
    }),

    PARROT(EntityType.PARROT, Material.PARROT_SPAWN_EGG, 1, ChatColor.RED + "Papağan Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Uçabilirsin",
    }),

    GOAT(EntityType.GOAT, Material.GOAT_SPAWN_EGG, 1, ChatColor.RED + "Keçi Yeteneği", new String[]{}),

    SKELETON(EntityType.SKELETON, Material.SKELETON_SPAWN_EGG, 1, ChatColor.GRAY + "İskelet Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Ok fırlatabilirsin",
    }),

    WOLF(EntityType.WOLF, Material.WOLF_SPAWN_EGG, 1, ChatColor.GRAY + "Kurt Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Evcil kurt çağırabilirsin. ",
            ChatColor.GRAY + "- Kurtlar %80 şans ile saldırdığı canlıyı pişirebilir.",
    }),

    WITHER_SKELETON(EntityType.WITHER_SKELETON, Material.WITHER_SKELETON_SPAWN_EGG, 1, ChatColor.GRAY + "Wither İskeletinin Yeteneği", new String[]{
            ChatColor.YELLOW + "Elinde tutarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Vurduğun canlılar Wither efektiyle zehirlenir",
    }),

    VILLAGER(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG, 1, ChatColor.YELLOW + "Köylü Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Zümrüt ile takas yapabilirsin"
    }),

    PIGLIN(EntityType.PIGLIN, Material.PIGLIN_SPAWN_EGG, 1, ChatColor.YELLOW + "Piglin Yeteneği", new String[]{
            ChatColor.YELLOW + "Sağ tıklayarak sahip olacağın yetenekler: ",
            ChatColor.GRAY + "- Altın ile takas yapabilirsin"
    });

    private final EntityType entityType;
    private final Material material;
    private final int customModelData;
    private final String displayName;
    private final String[] loretext;

    PowerItem(EntityType entity, Material material, int customModelData, String displayName, String[] loretext) {
        this.entityType = entity;
        this.material = material;
        this.customModelData = customModelData;
        this.displayName = displayName;
        this.loretext = loretext;
    }

    public static ItemStack generate(EntityType entityType) {
        try {
            return valueOf(entityType.name()).generate();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Material getSpawnMaterial() {
        return material;
    }

    public ItemStack generate() {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setCustomModelData(customModelData);
            itemMeta.setDisplayName(displayName);
            ArrayList<String> loreList = new ArrayList<String>();
            Collections.addAll(loreList, loretext);
            itemMeta.setLore(loreList);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

}