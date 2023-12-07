package mod.azure.doomangelring;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CommonMod {
    public static DoomAngelRingConfig config;
    public static final String MOD_ID = "doomangelring";
    public static final TagKey<Item> RING_REPAIR = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "doomangelring_repair"));

    public static final ResourceLocation modResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
