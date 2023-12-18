package mod.azure.doomangelring;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AngelRingItem extends TrinketItem {

    private int damageTicks;

    public AngelRingItem() {
        super(new Properties().stacksTo(1).durability(FabricLibMod.config.max_ring_durability));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack stack, ItemStack ingredient) {
        return ingredient.is(CommonMod.RING_REPAIR);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!player.getAbilities().flying && !player.onGround() && stack.getDamageValue() > 1) startFlying(player);
            if (player instanceof ServerPlayer serverplayer && !serverplayer.onGround()) {
                damageTicks++;
                if (damageTicks >= FabricLibMod.config.ticks_until_damage) {
                    stack.hurtAndBreak(FabricLibMod.config.ring_damage_on_tick, serverplayer, s -> {
                    });
                    damageTicks = 0;
                }
            }
            if (stack.getDamageValue() <= 1) stopFlying(player);
        }
        super.tick(stack, slot, entity);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof Player player) startFlying(player);
        super.onEquip(stack, slot, entity);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof Player player) stopFlying(player);
        super.onUnequip(stack, slot, entity);
    }

    @Override
    public DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return FabricLibMod.config.keep_ring_on_death ? DropRule.KEEP : DropRule.DROP;
    }

    private void startFlying(Player player) {
        if (!player.isCreative() && !player.isSpectator() && !player.onGround()) {
            player.getAbilities().flying = true;
            player.onUpdateAbilities();
        }
    }

    private void stopFlying(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().flying = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    }
}