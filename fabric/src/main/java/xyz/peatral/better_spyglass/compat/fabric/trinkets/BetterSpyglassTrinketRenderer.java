package xyz.peatral.better_spyglass.compat.fabric.trinkets;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.peatral.better_spyglass.client.SpyglassRenderer;

public class BetterSpyglassTrinketRenderer extends SpyglassRenderer implements TrinketRenderer {
    public static void register() {
        TrinketRendererRegistry.registerRenderer(Items.SPYGLASS, new BetterSpyglassTrinketRenderer());
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, LivingEntity livingEntity, float v, float v1, float v2, float v3, float v4, float v5) {
        this.renderSpyglass(stack, poseStack, multiBufferSource, light, livingEntity);
    }
}
