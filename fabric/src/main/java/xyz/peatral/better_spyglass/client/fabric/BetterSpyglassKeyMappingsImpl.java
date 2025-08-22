package xyz.peatral.better_spyglass.client.fabric;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import xyz.peatral.better_spyglass.client.BetterSpyglassKeyMappings;

public class BetterSpyglassKeyMappingsImpl {
    public static void register() {
        KeyBindingHelper.registerKeyBinding(BetterSpyglassKeyMappings.USE_SPYGLASS_MAPPING);
    }
}
