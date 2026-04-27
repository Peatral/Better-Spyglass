package xyz.peatral.better_spyglass.client.neoforge;

import dev.architectury.platform.Platform;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import xyz.peatral.better_spyglass.BetterSpyglass;

import xyz.peatral.better_spyglass.BetterSpyglassConfig;
import xyz.peatral.better_spyglass.client.BetterSpyglassClient;
import xyz.peatral.better_spyglass.compat.neoforge.curios.BetterSpyglassCuriosRenderer;

@EventBusSubscriber(modid = BetterSpyglass.MOD_ID, value = Dist.CLIENT)
public class BetterSpyglassClientImpl {

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        BetterSpyglassClient.init();
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (minecraft, parent) -> AutoConfig.getConfigScreen(BetterSpyglassConfig.class, parent).get()
        );
    }

    @SubscribeEvent
    public static void clientSetup(final RegisterKeyMappingsEvent event) {
        BetterSpyglassKeyMappingsImpl.register(event);
    }

    public static void registerRenderer() {
        if (Platform.isModLoaded("curios")) {
            BetterSpyglassCuriosRenderer.register();
        }
    }
}
