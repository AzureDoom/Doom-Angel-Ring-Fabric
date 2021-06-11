package mod.azure.doomangelring;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DoomAngelRing implements ModInitializer {
	public static final String MODID = "doomangelring";

	public static final Item ANGEL_RING = new AngelRingItem();

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(MODID, "angelring"), ANGEL_RING);
	}
}