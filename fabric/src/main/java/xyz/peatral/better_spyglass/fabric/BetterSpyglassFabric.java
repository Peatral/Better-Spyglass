package xyz.peatral.better_spyglass.fabric;

import net.fabricmc.api.ModInitializer;

import xyz.peatral.better_spyglass.BetterSpyglass;

public final class BetterSpyglassFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterSpyglass.init();
    }
}
