package xyz.peatral.better_spyglass.mixin;

import org.jetbrains.annotations.NotNull;
import xyz.peatral.better_spyglass.BetterSpyglassConfig;
import xyz.peatral.better_spyglass.compat.AccessorySlots;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements ISpyglassPlayer {
    @Unique
    private static final ItemStack better_spyglass$SPYGLASS = new ItemStack(Items.SPYGLASS);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean isCreative();
    @Shadow
    @Final
    private Inventory inventory;

    @Shadow
    public abstract @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot);

    @Unique
    private boolean better_spyglass$isUsingSpyglass = false;

    @Inject(method = "getItemBySlot", at = @At("RETURN"), cancellable = true)
    public void getItemBySlot(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir){
        if (better_spyglass$isUsingSpyglass && slot == EquipmentSlot.MAINHAND) {
            better_spyglass$getSpyglass().ifPresent(stack -> {
                if (getItemBySlot(EquipmentSlot.OFFHAND) != stack) {
                    cir.setReturnValue(stack);
                }
            });
        }
    }

    @Override
    public boolean better_spyglass$isUsingSpyglass() {
        return better_spyglass$isUsingSpyglass;
    }

    @Override
    public void better_spyglass$setUsingSpyglass(boolean usingSpyglass) {
        better_spyglass$isUsingSpyglass = usingSpyglass;
    }

    @Override
    public Optional<ItemStack> better_spyglass$getSpyglass() {
        BetterSpyglassConfig config = BetterSpyglassConfig.get();
        boolean enableInventoryAccess = config.enableInventoryAccess;
        boolean requireSpyglass = config.requireSpyglass;

        Stream<ItemStack> accessibleInventory = enableInventoryAccess
                ? Stream.of(inventory.items, inventory.offhand, inventory.armor).flatMap(Collection::stream)
                : Stream.of(List.of(inventory.getSelected()), inventory.offhand, inventory.armor).flatMap(Collection::stream);
        return accessibleInventory
                .filter(stack -> stack.is(Items.SPYGLASS))
                .findFirst()
                .or(() -> AccessorySlots.getSpyglass(this))
                .or(() -> Optional.ofNullable(isCreative() || !requireSpyglass ? better_spyglass$SPYGLASS : null));
    }
}
