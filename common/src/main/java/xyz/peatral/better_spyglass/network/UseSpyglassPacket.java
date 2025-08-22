package xyz.peatral.better_spyglass.network;

import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassPlayer;

import java.util.UUID;
import java.util.function.Supplier;

public record UseSpyglassPacket(boolean using, UUID uuid) {
    public UseSpyglassPacket(FriendlyByteBuf friendlyByteBuf) {
        this(friendlyByteBuf.readBoolean(), friendlyByteBuf.readUUID());
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(this.using);
        friendlyByteBuf.writeUUID(this.uuid);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        EnvType direction = contextSupplier.get().getEnv();
        Player sender = contextSupplier.get().getPlayer();
        if (direction == EnvType.SERVER) {
            if (sender != null) {
                Level level = sender.level();
                if (sender instanceof ISpyglassPlayer spyglassPlayer) {
                    spyglassPlayer.better_spyglass$setUsingSpyglass(using);
                }
                for (Player player : level.players()) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        BetterSpyglassPackets.CHANNEL.sendToPlayer(serverPlayer, new UseSpyglassPacket(using, uuid));
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
