package xyz.peatral.better_spyglass.mixin.client;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassClientPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player implements ISpyglassClientPlayer {
    @Unique
    private float better_spyglass$fovModifier = 0.1f;

    public AbstractClientPlayerMixin(Level level, BlockPos blockPos, float yRot, GameProfile gameProfile) {
        super(level, blockPos, yRot, gameProfile);
    }

    @Override
    public void better_spyglass$setFOVModifier(float fovModifier) {
        this.better_spyglass$fovModifier = fovModifier;
    }

    @Override
    public float better_spyglass$getFOVModifier() {
        return this.better_spyglass$fovModifier;
    }

    @Inject(method = "getFieldOfViewModifier", at = @At(value = "RETURN"), cancellable = true)
    public void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir){
        if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && isScoping()){
            cir.setReturnValue(better_spyglass$fovModifier);
        }
    }
}
