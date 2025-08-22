package xyz.peatral.better_spyglass.compat.forge.curios;

import dev.architectury.platform.Platform;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class CuriosIntegration {
    public static Optional<ItemStack> getCuriosSpyglass(LivingEntity livingEntity) {
        if (!Platform.isModLoaded("curios")) {
            return Optional.empty();
        }
        return CuriosApi.getCuriosInventory(livingEntity)
                .map(iCuriosItemHandler -> iCuriosItemHandler.findFirstCurio(Items.SPYGLASS))
                .flatMap(slotResult -> slotResult.map(SlotResult::stack));
    }
}
