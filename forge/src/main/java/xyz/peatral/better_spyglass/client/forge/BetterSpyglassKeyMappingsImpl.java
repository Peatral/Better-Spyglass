package xyz.peatral.better_spyglass.client.forge;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import xyz.peatral.better_spyglass.client.BetterSpyglassKeyMappings;

public class BetterSpyglassKeyMappingsImpl {
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(BetterSpyglassKeyMappings.USE_SPYGLASS_MAPPING);
    }
}
