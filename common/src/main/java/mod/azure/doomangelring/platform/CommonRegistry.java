package mod.azure.doomangelring.platform;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface CommonRegistry {
    <T extends Item> Supplier<T> registerItem(String modID, String itemName, Supplier<T> item);
}
