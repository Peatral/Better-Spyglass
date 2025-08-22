package xyz.peatral.better_spyglass.mixin.client;

import com.mojang.blaze3d.Blaze3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassClientPlayer;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow
    private double accumulatedDX;
    @Shadow
    private double accumulatedDY;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private double lastMouseEventTime;

    @Shadow
    @Final
    private SmoothDouble smoothTurnX;
    @Shadow
    @Final
    private SmoothDouble smoothTurnY;

    @Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
    public void turnPlayer(CallbackInfo ci) {
        if (minecraft.player instanceof ISpyglassClientPlayer spyglassClientPlayer && minecraft.options.getCameraType().isFirstPerson() && minecraft.player.isScoping()) {
            this.lastMouseEventTime = Blaze3D.getTime();
            double displacementX,displacementY;

            double sensitivity = minecraft.options.sensitivity().get() * 0.6 + 0.2;
            double baseSensitivity = (sensitivity * sensitivity * sensitivity) * 8.0;
            double spyglassSensitivity = baseSensitivity * spyglassClientPlayer.better_spyglass$getFOVModifier();

            smoothTurnX.reset();
            smoothTurnY.reset();
            displacementX = accumulatedDX * spyglassSensitivity;
            displacementY = accumulatedDY * spyglassSensitivity;

            accumulatedDX = 0.0;
            accumulatedDY = 0.0;
            int mouseDirection = minecraft.options.invertYMouse().get() ? -1 : 1;
            minecraft.getTutorial().onMouse(displacementX, displacementY);
            if (minecraft.player != null) {
                minecraft.player.turn(displacementX, displacementY * mouseDirection);
            }
            ci.cancel();
        }
    }
}
