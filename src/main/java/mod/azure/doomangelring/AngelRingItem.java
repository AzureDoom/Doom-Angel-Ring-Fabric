package mod.azure.doomangelring;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AngelRingItem extends TrinketItem {

	public AngelRingItem() {
		super(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (entity instanceof PlayerEntity) {
			PlayerEntity player = ((PlayerEntity) entity);
			if (!player.getAbilities().allowFlying) {
				startFlying(player);
			}
		}
		super.tick(stack, slot, entity);
	}

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (entity instanceof PlayerEntity) {
			startFlying((PlayerEntity) entity);
		}
		super.onEquip(stack, slot, entity);
	}

	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (entity instanceof PlayerEntity) {
			stopFlying((PlayerEntity) entity);
		}
		super.onUnequip(stack, slot, entity);
	}

	@Override
	public DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
		return TrinketEnums.DropRule.KEEP;
	}

	private void startFlying(PlayerEntity player) {
		if (!player.isCreative() && !player.isSpectator()) {
			player.getAbilities().allowFlying = true;
			player.sendAbilitiesUpdate();
		}
	}

	private void stopFlying(PlayerEntity player) {
		if (!player.isCreative() && !player.isSpectator()) {
			player.getAbilities().flying = false;
			player.getAbilities().allowFlying = false;
			player.sendAbilitiesUpdate();
		}
	}
}