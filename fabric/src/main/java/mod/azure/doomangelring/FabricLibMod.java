package mod.azure.doomangelring;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public final class FabricLibMod implements ModInitializer {
    public static final DoomAngelRingConfig config = AzureLibMod.registerConfig(DoomAngelRingConfig.class, ConfigFormats.json()).getConfigInstance();

    public static final Item ANGEL_RING = new AngelRingItem();

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.ITEM, CommonMod.modResource("angelring"), ANGEL_RING);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> entries.accept(ANGEL_RING));
    }
}
