package xyz.peatral.better_spyglass.compat.fabric;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.peatral.better_spyglass.compat.fabric.trinkets.TrinketsIntegration;

import java.util.Optional;

public class AccessorySlotsImpl {
    public static Optional<ItemStack> getSpyglass(LivingEntity livingEntity) {
        return TrinketsIntegration.getTrinketsSpyglass(livingEntity);
    }
}
