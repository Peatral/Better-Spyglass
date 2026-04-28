package xyz.peatral.better_spyglass.network.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import xyz.peatral.better_spyglass.network.Networking;

public class NetworkingImpl {
    public static <T extends CustomPacketPayload> void registerBidirectional(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> reader,
            Networking.IPayloadHandler<T> handler
    ) {
        registerC2S(type, reader, handler);
        registerS2C(type, reader, handler);
    }

    public static <T extends CustomPacketPayload> void registerC2S(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> reader,
            Networking.IPayloadHandler<T> handler
    ) {
        PayloadTypeRegistry.playC2S().register(
                type,
                reader
        );
        ServerPlayNetworking.registerGlobalReceiver(
                type,
                (packet, context) -> handler.handle(packet, new Networking.PayloadContext(context.player(), true))
        );
    }

    public static <T extends CustomPacketPayload> void registerS2C(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> reader,
            Networking.IPayloadHandler<T> handler
    ) {
        PayloadTypeRegistry.playS2C().register(
                type,
                reader
        );
        ClientPlayNetworking.registerGlobalReceiver(
                type,
                (packet, context) -> handler.handle(packet, new Networking.PayloadContext(context.player(), false))
        );
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload packet) {
        ServerPlayNetworking.send(player, packet);
    }

    public static void sendToServer(CustomPacketPayload packet) {
        ClientPlayNetworking.send(packet);
    }
}
