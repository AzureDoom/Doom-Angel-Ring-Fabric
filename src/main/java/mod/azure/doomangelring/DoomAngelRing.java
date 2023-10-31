package mod.azure.doomangelring;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(DoomAngelRing.MODID)
public class DoomAngelRing {
	public static final String MODID = "doomangelring";
	public static DoomAngelRingConfig config;
	public static final TagKey<Item> RING_REPAIR = TagKey.create(Registries.ITEM, new ResourceLocation(MODID, "doomangelring_repair"));
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final RegistryObject<Item> ANGEL_RING = ITEMS.register("angelring", () -> new AngelRingItem());

	public DoomAngelRing() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		config = AzureLibMod.registerConfig(DoomAngelRingConfig.class, ConfigFormats.json()).getConfigInstance();

		ITEMS.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::addCreative);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT)
			event.accept(ANGEL_RING);
	}
}
