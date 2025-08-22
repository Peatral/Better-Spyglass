package xyz.peatral.better_spyglass.client;

import dev.architectury.injectables.annotations.ExpectPlatform;

public abstract class BetterSpyglassClient {
    public static void init() {
        ClientEvents.init();
        registerRenderer();
    }

    @ExpectPlatform
    public static void registerRenderer() {
        throw new AssertionError();
    }
}
