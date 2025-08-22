package xyz.peatral.better_spyglass.compat.forge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.peatral.better_spyglass.compat.forge.curios.CuriosIntegration;

import java.util.Optional;

public class AccessorySlotsImpl {
    public static Optional<ItemStack> getSpyglass(LivingEntity livingEntity) {
        return CuriosIntegration.getCuriosSpyglass(livingEntity);
    }
}
