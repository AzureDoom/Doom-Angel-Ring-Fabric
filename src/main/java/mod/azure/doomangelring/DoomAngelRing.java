package mod.azure.doomangelring;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(DoomAngelRing.MODID)
public class DoomAngelRing {
	public static final String MODID = "doomangelring";
	public static final TagKey<Item> RING_REPAIR = TagKey.create(Registries.ITEM, new ResourceLocation(MODID, "doomangelring_repair"));
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final RegistryObject<Item> ANGEL_RING = ITEMS.register("angelring", () -> new AngelRingItem());

	public DoomAngelRing() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SERVER_SPEC, "doomangelring.toml");
		Config.loadConfig(Config.SERVER_SPEC, FMLPaths.CONFIGDIR.get().resolve("doomangelring.toml").toString());

		ITEMS.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::addCreative);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT)
			event.accept(ANGEL_RING);
	}
}
