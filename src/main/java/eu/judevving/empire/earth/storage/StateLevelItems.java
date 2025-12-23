package eu.judevving.empire.earth.storage;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.language.Text;
import org.bukkit.Material;

public enum StateLevelItems {

    ARMOR_TRIMS(Text.MENU_LEVELING_PAYMENT_DESCRIPTION_ARMOR_TRIMS,
            Material.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE
    ),

    FLOWERS(Text.MENU_LEVELING_PAYMENT_DESCRIPTION_FLOWERS,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.BLUE_ORCHID,
            Material.CACTUS_FLOWER,
            Material.CHORUS_FLOWER,
            Material.CLOSED_EYEBLOSSOM,
            Material.CORNFLOWER,
            Material.DANDELION,
            Material.FLOWERING_AZALEA,
            Material.FLOWERING_AZALEA_LEAVES,
            Material.LILAC,
            Material.LILY_OF_THE_VALLEY,
            Material.OPEN_EYEBLOSSOM,
            Material.ORANGE_TULIP,
            Material.OXEYE_DAISY,
            Material.PEONY,
            Material.PINK_PETALS,
            Material.PINK_TULIP,
            Material.PITCHER_PLANT,
            Material.POPPY,
            Material.RED_TULIP,
            Material.ROSE_BUSH,
            Material.SPORE_BLOSSOM,
            Material.SUNFLOWER,
            Material.TORCHFLOWER,
            Material.WHITE_TULIP,
            Material.WILDFLOWERS,
            Material.WITHER_ROSE
    ),

    HEADS(Text.MENU_LEVELING_PAYMENT_DESCRIPTION_HEADS,
            Material.CREEPER_HEAD,
            Material.DRAGON_HEAD,
            Material.PIGLIN_HEAD,
            Material.PLAYER_HEAD,
            Material.SKELETON_SKULL,
            Material.WITHER_SKELETON_SKULL,
            Material.ZOMBIE_HEAD
    ),

    MUSIC_DISCS(Text.MENU_LEVELING_PAYMENT_DESCRIPTION_MUSIC_DISCS,
            Material.MUSIC_DISC_5,
            Material.MUSIC_DISC_11,
            Material.MUSIC_DISC_13,
            Material.MUSIC_DISC_BLOCKS,
            Material.MUSIC_DISC_CAT,
            Material.MUSIC_DISC_CHIRP,
            Material.MUSIC_DISC_CREATOR,
            Material.MUSIC_DISC_CREATOR_MUSIC_BOX,
            Material.MUSIC_DISC_FAR,
            Material.MUSIC_DISC_LAVA_CHICKEN,
            Material.MUSIC_DISC_MALL,
            Material.MUSIC_DISC_MELLOHI,
            Material.MUSIC_DISC_OTHERSIDE,
            Material.MUSIC_DISC_PIGSTEP,
            Material.MUSIC_DISC_PRECIPICE,
            Material.MUSIC_DISC_RELIC,
            Material.MUSIC_DISC_STAL,
            Material.MUSIC_DISC_STRAD,
            Material.MUSIC_DISC_TEARS,
            Material.MUSIC_DISC_WAIT,
            Material.MUSIC_DISC_WARD
            ),

    POTTERY_SHERDS(Text.MENU_LEVELING_PAYMENT_DESCRIPTION_ARMOR_SHERDS,
            Material.ANGLER_POTTERY_SHERD,
            Material.ARCHER_POTTERY_SHERD,
            Material.ARMS_UP_POTTERY_SHERD,
            Material.BLADE_POTTERY_SHERD,
            Material.BREWER_POTTERY_SHERD,
            Material.BURN_POTTERY_SHERD,
            Material.DANGER_POTTERY_SHERD,
            Material.EXPLORER_POTTERY_SHERD,
            Material.FLOW_POTTERY_SHERD,
            Material.FRIEND_POTTERY_SHERD,
            Material.GUSTER_POTTERY_SHERD,
            Material.HEART_POTTERY_SHERD,
            Material.HEARTBREAK_POTTERY_SHERD,
            Material.HOWL_POTTERY_SHERD,
            Material.MINER_POTTERY_SHERD,
            Material.MOURNER_POTTERY_SHERD,
            Material.PLENTY_POTTERY_SHERD,
            Material.PRIZE_POTTERY_SHERD,
            Material.SCRAPE_POTTERY_SHERD,
            Material.SHEAF_POTTERY_SHERD,
            Material.SHELTER_POTTERY_SHERD,
            Material.SKULL_POTTERY_SHERD,
            Material.SNORT_POTTERY_SHERD
            );

    private final Text paymentDescription;
    private final Material[] materials;

    StateLevelItems(Text paymentDescription, Material... materials) {
        this.paymentDescription = paymentDescription;
        this.materials = materials;
    }

    public int tryPay(Human h) {
        int re = 0;
        for (Material material : materials) {
            if (h.countMaterial(material) > 0) re++;
        }
        if (re == materials.length) {
            for (Material material : materials) {
                h.removeMaterial(material, 1);
            }
        }
        return re;
    }

    public int size() {
        return materials.length;
    }

    public Text getPaymentDescription() {
        return paymentDescription;
    }
}
