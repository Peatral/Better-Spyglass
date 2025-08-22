package xyz.peatral.better_spyglass.player.extensions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ISpyglassClientPlayer extends ISpyglassPlayer {
    void better_spyglass$setFOVModifier(float fovModifier);
    float better_spyglass$getFOVModifier();
}
