package xyz.peatral.better_spyglass.client.forge;

import dev.architectury.platform.Platform;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.ModList;
import xyz.peatral.better_spyglass.BetterSpyglass;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.peatral.better_spyglass.BetterSpyglassConfig;
import xyz.peatral.better_spyglass.client.BetterSpyglassClient;
import xyz.peatral.better_spyglass.compat.forge.curios.BetterSpyglassCuriosRenderer;

@Mod.EventBusSubscriber(modid = BetterSpyglass.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BetterSpyglassClientImpl {

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        BetterSpyglassClient.init();
        ModList.get().getModContainerById(BetterSpyglass.MOD_ID).ifPresent(
                container -> container.registerExtensionPoint(
                        ConfigScreenHandler.ConfigScreenFactory.class,
                        () -> new ConfigScreenHandler.ConfigScreenFactory(
                                (minecraft, parent) -> AutoConfig.getConfigScreen(BetterSpyglassConfig.class, parent).get()
                        )
                )
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
