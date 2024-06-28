package mod.azure.doomangelring;

import mod.azure.doomangelring.platform.CommonRegistry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class NeoForgeCommonRegistry implements CommonRegistry {

    @Override
    public <T extends Item> Supplier<T> registerItem(String modID, String itemName, Supplier<T> item) {
        return NeoForgeMod.ITEMS.register(itemName, item);
    }
}
