package xyz.peatral.better_spyglass.network.neoforge;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import xyz.peatral.better_spyglass.network.Networking;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@EventBusSubscriber
public class PacketRegistry {
    private static final List<Consumer<PayloadRegistrar>> REGISTRATION_TASKS = new ArrayList<>();

    public static <T extends CustomPacketPayload> void registerBidirectional(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> reader,
            Networking.IPayloadHandler<T> handler
    ) {
        REGISTRATION_TASKS.add(registrar -> {
            registrar.playBidirectional(
                    type,
                    reader,
                    (packet, context) -> handler.handle(packet, new Networking.PayloadContext(context.player(), context.flow().isServerbound()))
            );
        });
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        for (Consumer<PayloadRegistrar> task : REGISTRATION_TASKS) {
            task.accept(registrar);
        }
    }
}
