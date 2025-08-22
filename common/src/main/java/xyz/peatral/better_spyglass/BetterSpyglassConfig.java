package xyz.peatral.better_spyglass;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = BetterSpyglass.MOD_ID)
public class BetterSpyglassConfig implements ConfigData {

    public boolean enableInventoryAccess = false;
    public boolean requireSpyglass = true;
    public float zoomStep = 0.1f;

    public static void register() {
        AutoConfig.register(BetterSpyglassConfig.class, GsonConfigSerializer::new);
    }

    public static BetterSpyglassConfig get() {
        return AutoConfig.getConfigHolder(BetterSpyglassConfig.class).getConfig();
    }
}
