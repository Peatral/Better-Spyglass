package xyz.peatral.better_spyglass.compat.fabric.trinkets;

import dev.architectury.platform.Platform;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class TrinketsIntegration {
    public static Optional<ItemStack> getTrinketsSpyglass(LivingEntity livingEntity) {
        if (!Platform.isModLoaded("trinkets")) {
            return Optional.empty();
        }
        return TrinketsApi.getTrinketComponent(livingEntity)
                .flatMap(component -> component.getEquipped(Items.SPYGLASS).stream().findFirst())
                .map(Tuple::getB);
    }
}
