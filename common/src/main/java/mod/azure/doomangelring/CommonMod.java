package mod.azure.doomangelring;

import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.config.format.ConfigFormats;
import mod.azure.doomangelring.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CommonMod {
    public static DoomAngelRingConfig config;
    public static final String MOD_ID = "doomangelring";
    public static final TagKey<Item> RING_REPAIR = TagKey.create(Registries.ITEM, modResource("doomangelring_repair"));

    public static void init() {
        CommonMod.config = AzureLibMod.registerConfig(DoomAngelRingConfig.class, ConfigFormats.json()).getConfigInstance();
        ModItems.init();
    }
    public static ResourceLocation modResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}