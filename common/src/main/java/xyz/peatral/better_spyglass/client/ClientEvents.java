package xyz.peatral.better_spyglass.client;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import xyz.peatral.better_spyglass.BetterSpyglassConfig;
import xyz.peatral.better_spyglass.network.BetterSpyglassPackets;
import xyz.peatral.better_spyglass.network.UseSpyglassPacket;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassClientPlayer;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassPlayer;

public class ClientEvents {
    public static void init() {
        ClientRawInputEvent.MOUSE_SCROLLED.register((client, amount) -> {
            LocalPlayer player = client.player;

            if (!(player instanceof ISpyglassClientPlayer spyglassPlayer)) {
                return EventResult.pass();
            }

            if (player.isScoping()) {
                if (client.options.getCameraType().isFirstPerson()) {
                    float prevModifier = spyglassPlayer.better_spyglass$getFOVModifier();
                    float modifier = (float) (prevModifier - (amount * BetterSpyglassConfig.get().zoomStep));
                    modifier = Mth.clamp(modifier, 0.1f, 0.8f);
                    if (prevModifier != modifier) {
                        spyglassPlayer.better_spyglass$setFOVModifier(modifier);
                        player.playSound(
                                SoundEvents.SPYGLASS_STOP_USING,
                                1.0f,
                                1.0f + (1 * (1 - modifier) * (1 - modifier))
                        );
                    }
                }
                return EventResult.interruptFalse();
            }
            return EventResult.pass();
        });

        ClientTickEvent.CLIENT_PRE.register(client -> {
            LocalPlayer player = client.player;

            if (!(player instanceof ISpyglassPlayer spyglassPlayer)) {
                return;
            }

            boolean keyDown = BetterSpyglassKeyMappings.USE_SPYGLASS_MAPPING.isDown();


            if (keyDown) {
                if (!spyglassPlayer.better_spyglass$isUsingSpyglass()) {
                    boolean canUse = !player.isUsingItem() && spyglassPlayer.better_spyglass$getSpyglass().isPresent();
                    if (canUse) {
                        BetterSpyglassPackets.CHANNEL.sendToServer(new UseSpyglassPacket(true, player.getUUID()));
                    }
                }
            } else {
                if (spyglassPlayer.better_spyglass$isUsingSpyglass()) {
                    BetterSpyglassPackets.CHANNEL.sendToServer(new UseSpyglassPacket(false, player.getUUID()));
                }
            }
        });
    }
}
