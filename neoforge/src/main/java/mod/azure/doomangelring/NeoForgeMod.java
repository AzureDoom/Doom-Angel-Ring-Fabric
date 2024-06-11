package mod.azure.doomangelring;

import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.config.format.ConfigFormats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(CommonMod.MOD_ID)
public final class NeoForgeMod {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM,
            CommonMod.MOD_ID);
    public static final Supplier<Item> ANGEL_RING = ITEMS.register("angelring", AngelRingItem::new);
    public static NeoForgeMod instance;

    public NeoForgeMod(IEventBus modEventBus) {
        instance = this;
        CommonMod.config = AzureLibMod.registerConfig(DoomAngelRingConfig.class,
                ConfigFormats.json()).getConfigInstance();
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) event.accept(ANGEL_RING.get());
    }
}
