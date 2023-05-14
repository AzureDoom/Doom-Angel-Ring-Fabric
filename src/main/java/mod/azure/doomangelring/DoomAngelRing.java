package mod.azure.doomangelring;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.format.ConfigFormats;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class DoomAngelRing implements ModInitializer {

	public static DoomAngelRingConfig config;
	public static final String MODID = "doomangelring";
	public static final Item ANGEL_RING = new AngelRingItem();

	public static final TagKey<Item> RING_REPAIR = TagKey.create(Registries.ITEM,
			new ResourceLocation(MODID, "doomangelring_repair"));

	@Override
	public void onInitialize() {
		config = Configuration.registerConfig(DoomAngelRingConfig.class, ConfigFormats.json()).getConfigInstance();
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MODID, "angelring"), ANGEL_RING);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> entries.accept(ANGEL_RING));
	}
}