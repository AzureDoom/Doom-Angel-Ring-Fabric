package mod.azure.doomangelring;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AngelRingItem extends Item {

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
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag unused) {
        final ICurio curio = new ICurio() {
            @Override
            public boolean canEquipFromUse(SlotContext slotContext) {
                return true;
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                if (slotContext.entity() instanceof Player player) startPowers(player);
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                if (slotContext.entity() instanceof Player player) stopPowers(player);
            }

            private void startPowers(Player player) {
                if (!player.isCreative() && !player.isSpectator() && !player.onGround()) {
                    player.getAbilities().flying = true;
                    player.onUpdateAbilities();
                    if (player instanceof ServerPlayer serverplayer && !serverplayer.onGround()) {
                        damageTicks++;
                        if (damageTicks >= CommonMod.config.ticks_until_damage) {
                            stack.hurtAndBreak(CommonMod.config.ring_damage_on_tick, serverplayer,
                                    s -> CuriosApi.getCuriosHelper().setBrokenCurioConsumer(context -> {
                                    }));
                            damageTicks = 0;
                        }
                    }
                }
            }

            private void stopPowers(Player player) {
                if (!player.isCreative() && !player.isSpectator()) {
                    player.getAbilities().flying = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
            }

            @Override
            public void curioTick(SlotContext slotContext) {
                if (slotContext.entity() instanceof Player player) startPowers(player);
            }

            @Override
            public boolean canEquip(SlotContext slotContext) {
                return CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(),
                        NeoForgeMod.ANGEL_RING.get()).isEmpty();
            }

            @Override
            public ItemStack getStack() {
                return new ItemStack(NeoForgeMod.ANGEL_RING.get());
            }
        };

        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
            }
        };
    }
}