package mod.azure.doomangelring;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AngelRingItem extends Item {

    private int damageTicks;

    public AngelRingItem() {
        super(new Item.Properties().stacksTo(1).durability(DoomAngelRing.config.max_ring_durability));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(DoomAngelRing.RING_REPAIR);
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag unused) {
        final ICurio curio = new ICurio() {
            @Override
            public boolean canRightClickEquip() {
                return true;
            }

            @Override
            public void onEquip(String identifier, int index, LivingEntity livingEntity) {
                if (livingEntity instanceof Player player) {
                    startPowers(player);
                }
            }

            @Override
            public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
                if (livingEntity instanceof Player player)
                    stopPowers(player);
            }

            private void startPowers(Player player) {
                if (!player.isCreative() && !player.isSpectator() && !player.onGround()) {
                    player.getAbilities().flying = true;
                    player.onUpdateAbilities();
                    if (player instanceof ServerPlayer serverplayer && !serverplayer.onGround()) {
                        damageTicks++;
                        if (damageTicks >= DoomAngelRing.config.ticks_until_damage) {
                            stack.hurt(DoomAngelRing.config.ring_damage_on_tick, serverplayer.getRandom(), serverplayer);
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
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                if (livingEntity instanceof Player player)
                    startPowers(player);
            }

            @Override
            public boolean canEquip(String identifier, LivingEntity entityLivingBase) {
                return !CuriosApi.getCuriosHelper().findFirstCurio(entityLivingBase, DoomAngelRing.ANGEL_RING.get()).isPresent();
            }

            @Override
            public ItemStack getStack() {
                return new ItemStack(DoomAngelRing.ANGEL_RING.get());
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

    public static boolean isRingInCuriosSlot(ItemStack belt, LivingEntity player) {
        return CuriosApi.getCuriosHelper().findFirstCurio(player, belt.getItem()).isPresent();
    }
}