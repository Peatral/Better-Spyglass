package xyz.peatral.better_spyglass;


import xyz.peatral.better_spyglass.network.BetterSpyglassPackets;

public class BetterSpyglass {
    public static final String MOD_ID = "better_spyglass";

    public static void init() {
        BetterSpyglassPackets.init();
        BetterSpyglassConfig.register();
    }
}
