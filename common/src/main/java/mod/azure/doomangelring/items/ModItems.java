package mod.azure.doomangelring.items;

import mod.azure.doomangelring.CommonMod;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record ModItems() implements CommonItemRegistryInterface {
    public static final Supplier<Item> ANGEL_RING = CommonItemRegistryInterface.registerItem(CommonMod.MOD_ID, "angelring",
            AngelRingItem::new);

    public static void init() {
    }
}
