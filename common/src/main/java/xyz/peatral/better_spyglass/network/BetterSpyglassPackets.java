package xyz.peatral.better_spyglass.network;

public class BetterSpyglassPackets {
    public static void init() {
        Networking.registerBidirectional(
                UseSpyglassPacket.TYPE,
                UseSpyglassPacket.STREAM_CODEC,
                UseSpyglassPacket::handle
        );
    }
}
