package xyz.peatral.better_spyglass.mixin.server;

import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassPlayer;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;
    @Unique
    private boolean better_spyglass$wasUsingSpyglass = false;

    @Inject(method = "handlePlayerAction", at = @At("HEAD"))
    public void handlePlayerActionBefore(ServerboundPlayerActionPacket packet, CallbackInfo ci) {
        if (!(player instanceof ISpyglassPlayer spyglassPlayer)) {
            return;
        }

        better_spyglass$wasUsingSpyglass = spyglassPlayer.better_spyglass$isUsingSpyglass();
        spyglassPlayer.better_spyglass$setUsingSpyglass(false);
    }

    @Inject(method = "handlePlayerAction", at = @At("RETURN"))
    public void handlePlayerActionAfter(ServerboundPlayerActionPacket packet, CallbackInfo ci) {
        if (!(player instanceof ISpyglassPlayer spyglassPlayer)) {
            return;
        }

        spyglassPlayer.better_spyglass$setUsingSpyglass(better_spyglass$wasUsingSpyglass);
    }

}
