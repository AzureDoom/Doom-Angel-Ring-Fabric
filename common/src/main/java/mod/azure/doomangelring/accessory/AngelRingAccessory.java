package mod.azure.doomangelring.accessory;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.DropRule;
import io.wispforest.accessories.api.slot.SlotReference;
import mod.azure.doomangelring.CommonMod;
import mod.azure.doomangelring.items.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AngelRingAccessory implements Accessory {
    private int damageTicks;

    public static void init() {
        AccessoriesAPI.registerAccessory(ModItems.ANGEL_RING.get(), new AngelRingAccessory());
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (reference.entity() instanceof Player player) {
            if (!player.getAbilities().flying && !player.onGround() && stack.getDamageValue() > 1) startFlying(player);
            if (player instanceof ServerPlayer serverplayer && !serverplayer.onGround()) {
                damageTicks++;
                if (damageTicks >= CommonMod.config.ticks_until_damage) {
                    stack.hurtAndBreak(CommonMod.config.ring_damage_on_tick, serverplayer, player.getEquipmentSlotForItem(stack));
                    damageTicks = 0;
                }
            }
            if (stack.getDamageValue() <= 1) stopFlying(player);
        }
        Accessory.super.tick(stack, reference);
    }

    @Override
    public DropRule getDropRule(ItemStack stack, SlotReference reference, DamageSource source) {
        return CommonMod.config.keep_ring_on_death ? DropRule.KEEP : DropRule.DROP;
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        if (reference.entity() instanceof Player player) startFlying(player);
        Accessory.super.onEquip(stack, reference);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        if (reference.entity() instanceof Player player) stopFlying(player);
        Accessory.super.onUnequip(stack, reference);
    }

    private void startFlying(Player player) {
        if ((!player.isCreative() || !player.isSpectator()) && !player.onGround()) {
            player.getAbilities().flying = true;
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    private void stopFlying(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().flying = false;
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
        }
    }
}
