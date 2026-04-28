package xyz.peatral.better_spyglass.network;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class Networking {

    @ExpectPlatform
    public static <T extends CustomPacketPayload> void registerBidirectional(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> reader,
            IPayloadHandler<T> handler
    ) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload packet) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void sendToServer(CustomPacketPayload packet) {
        throw new AssertionError();
    }

    public record PayloadContext(Player player, boolean isServerbound) {
    }

    @FunctionalInterface
    public interface IPayloadHandler<T extends CustomPacketPayload> {
        void handle(T payload, PayloadContext context);
    }
}
