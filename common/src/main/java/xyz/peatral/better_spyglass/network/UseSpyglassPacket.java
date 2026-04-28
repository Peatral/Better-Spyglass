package xyz.peatral.better_spyglass.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import xyz.peatral.better_spyglass.BetterSpyglass;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassPlayer;

import java.util.UUID;

public record UseSpyglassPacket(boolean using, UUID uuid) implements CustomPacketPayload {
    public static final ResourceLocation USE_SPYGLASS_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(BetterSpyglass.MOD_ID, "use_spyglass");
    public static final CustomPacketPayload.Type<UseSpyglassPacket> TYPE =  new CustomPacketPayload.Type<>(USE_SPYGLASS_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, UseSpyglassPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            UseSpyglassPacket::using,
            UUIDUtil.STREAM_CODEC,
            UseSpyglassPacket::uuid,
            UseSpyglassPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(Networking.PayloadContext context) {
        Player sender = context.player();
        if (context.isServerbound()) {
            if (sender != null) {
                Level level = sender.level();
                if (sender instanceof ISpyglassPlayer spyglassPlayer) {
                    spyglassPlayer.better_spyglass$setUsingSpyglass(using);
                }
                for (Player player : level.players()) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        Networking.sendToPlayer(serverPlayer, new UseSpyglassPacket(using, uuid));
                    }
                }
            }
        } else {
            ClientLevel level = Minecraft.getInstance().level;
            if (level == null) {
                return;
            }

            Player player = level.getPlayerByUUID(uuid);
            if (player instanceof ISpyglassPlayer spyglassPlayer) {
                spyglassPlayer.better_spyglass$setUsingSpyglass(using);
            }
        }
    }
}
