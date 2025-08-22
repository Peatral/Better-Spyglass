package xyz.peatral.better_spyglass.mixin.client;

import xyz.peatral.better_spyglass.player.extensions.ISpyglassPlayer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    public LocalPlayer player;
    @Final
    @Shadow
    public Options options;

    @Redirect(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isDown()Z"))
    public boolean handleInput(KeyMapping instance) {
        return instance.isDown() || instance == options.keyUse && player instanceof ISpyglassPlayer spyglassPlayer && spyglassPlayer.better_spyglass$isUsingSpyglass();
    }
}
