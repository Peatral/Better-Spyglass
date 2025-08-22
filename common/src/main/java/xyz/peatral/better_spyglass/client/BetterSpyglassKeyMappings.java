package xyz.peatral.better_spyglass.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import xyz.peatral.better_spyglass.BetterSpyglass;

public class BetterSpyglassKeyMappings {
    public static final KeyMapping USE_SPYGLASS_MAPPING = new KeyMapping(
            "key." + BetterSpyglass.MOD_ID + ".use",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.category." + BetterSpyglass.MOD_ID
    );
}
