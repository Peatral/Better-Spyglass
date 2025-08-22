package xyz.peatral.better_spyglass.client.fabric;

import dev.architectury.platform.Platform;
import net.fabricmc.api.ClientModInitializer;
import xyz.peatral.better_spyglass.client.BetterSpyglassClient;
import xyz.peatral.better_spyglass.compat.fabric.trinkets.BetterSpyglassTrinketRenderer;

public final class BetterSpyglassClientImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BetterSpyglassClient.init();
        BetterSpyglassKeyMappingsImpl.register();
    }

    public static void registerRenderer() {
        if (Platform.isModLoaded("trinkets")) {
            BetterSpyglassTrinketRenderer.register();
        }
    }
}
