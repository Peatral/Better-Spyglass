package xyz.peatral.better_spyglass.network;

import dev.architectury.networking.NetworkChannel;
import net.minecraft.resources.ResourceLocation;
import xyz.peatral.better_spyglass.BetterSpyglass;

public class BetterSpyglassPackets {
    public static final NetworkChannel CHANNEL = NetworkChannel.create(new ResourceLocation(BetterSpyglass.MOD_ID, "main"));

    public static void init() {
        CHANNEL.register(UseSpyglassPacket.class, UseSpyglassPacket::encode, UseSpyglassPacket::new, UseSpyglassPacket::handle);
    }
}
