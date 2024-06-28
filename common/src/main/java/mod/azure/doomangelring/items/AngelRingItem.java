package mod.azure.doomangelring.items;

import mod.azure.doomangelring.CommonMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AngelRingItem extends Item {

    public AngelRingItem() {
        super(new Item.Properties().stacksTo(1).durability(CommonMod.config.max_ring_durability));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack stack, ItemStack ingredient) {
        return ingredient.is(CommonMod.RING_REPAIR);
    }
}
