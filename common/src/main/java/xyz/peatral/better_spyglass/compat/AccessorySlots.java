package xyz.peatral.better_spyglass.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class AccessorySlots {
    @ExpectPlatform
    public static Optional<ItemStack> getSpyglass(LivingEntity livingEntity) {
        throw new AssertionError();
    }
}
