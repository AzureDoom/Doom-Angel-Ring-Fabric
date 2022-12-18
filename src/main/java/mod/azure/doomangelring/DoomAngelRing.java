package mod.azure.doomangelring;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DoomAngelRing implements ModInitializer {
	public static final String MODID = "doomangelring";

	public static final Item ANGEL_RING = new AngelRingItem();

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier(MODID, "angelring"), ANGEL_RING);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ANGEL_RING));
	}
}