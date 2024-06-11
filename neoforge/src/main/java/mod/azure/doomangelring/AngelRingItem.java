package mod.azure.doomangelring;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class AngelRingItem extends Item implements ICurioItem {

    private int damageTicks;

    public AngelRingItem() {
        super(new Properties().stacksTo(1).durability(CommonMod.config.max_ring_durability));
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
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) startFlying(player);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) stopFlying(player);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (!player.getAbilities().flying && !player.onGround() && stack.getDamageValue() > 1) startFlying(player);
            if (player instanceof ServerPlayer serverplayer && !serverplayer.onGround()) {
                damageTicks++;
                if (damageTicks >= CommonMod.config.ticks_until_damage) {
                    stack.hurtAndBreak(CommonMod.config.ring_damage_on_tick, serverplayer, LivingEntity.getEquipmentSlotForItem(stack));
                    damageTicks = 0;
                }
            }
            if (stack.getDamageValue() <= 1) stopFlying(player);
        }
    }

    @NotNull
    @Override
    public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        return CommonMod.config.keep_ring_on_death ? ICurio.DropRule.ALWAYS_KEEP : ICurio.DropRule.ALWAYS_DROP;
    }

    private void startFlying(Player player) {
        if (!player.isCreative() && !player.isSpectator() && !player.onGround()) {
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