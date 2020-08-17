package mod.azure.doomangelring;

import nerdhub.cardinal.components.api.event.ItemComponentCallbackV2;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosComponent;
import top.theillusivec4.curios.api.SlotTypeInfo.BuildScheme;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.component.ICurio;

public class DoomAngelRing implements ModInitializer {
	public static final String MODID = "doomangelring";

	public static final Item ANGEL_RING = new Item(
			new Item.Settings().maxCount(1).group(ItemGroup.MISC).maxDamage(500));

	@Override
	public void onInitialize() {
		CuriosApi.enqueueSlotType(BuildScheme.REGISTER, SlotTypePreset.RING.getInfoBuilder().build());
		Registry.register(Registry.ITEM, new Identifier(MODID, "itemdoomangelring"), ANGEL_RING);
		ItemComponentCallbackV2.event(DoomAngelRing.ANGEL_RING).register(
				((item, itemStack, componentContainer) -> componentContainer.put(CuriosComponent.ITEM, new ICurio() {

					@Override
					public boolean canRightClickEquip() {
						return true;
					}

					@Override
					public void onEquip(String identifier, int index, LivingEntity livingEntity) {
						if (livingEntity instanceof PlayerEntity) {
							startFlying((PlayerEntity) livingEntity);
						}
					}

					@Override
					public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
						if (livingEntity instanceof PlayerEntity) {
							stopFlying((PlayerEntity) livingEntity);
						}
					}

					private void startFlying(PlayerEntity player) {
						if (!player.isCreative() && !player.isSpectator()) {
							player.abilities.allowFlying = true;
							player.sendAbilitiesUpdate();
						}
					}

					private void stopFlying(PlayerEntity player) {
						if (!player.isCreative() && !player.isSpectator()) {
							player.abilities.flying = false;
							player.abilities.allowFlying = false;
							player.sendAbilitiesUpdate();
						}
					}

					@Override
					public void curioTick(String identifier, int index, LivingEntity livingEntity) {
						if (livingEntity instanceof PlayerEntity) {
							PlayerEntity player = ((PlayerEntity) livingEntity);
							if (!player.abilities.allowFlying) {
								startFlying(player);
							}
						}
					}

					@Override
					public boolean canEquip(String identifier, LivingEntity entityLivingBase) {
						return !CuriosApi.getCuriosHelper()
								.findEquippedCurio(DoomAngelRing.ANGEL_RING, entityLivingBase).isPresent();
					}

				})));
	}
}