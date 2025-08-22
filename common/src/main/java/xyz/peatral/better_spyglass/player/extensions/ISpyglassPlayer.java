package xyz.peatral.better_spyglass.player.extensions;

import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface ISpyglassPlayer {
    void better_spyglass$setUsingSpyglass(boolean usingSpyglass);
    boolean better_spyglass$isUsingSpyglass();

    Optional<ItemStack> better_spyglass$getSpyglass();
}
