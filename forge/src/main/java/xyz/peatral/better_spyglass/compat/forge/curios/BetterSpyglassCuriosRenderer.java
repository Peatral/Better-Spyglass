package xyz.peatral.better_spyglass.compat.forge.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import xyz.peatral.better_spyglass.client.SpyglassRenderer;

public class BetterSpyglassCuriosRenderer extends SpyglassRenderer implements ICurioRenderer {

    public static void register() {
        CuriosRendererRegistry.register(Items.SPYGLASS, BetterSpyglassCuriosRenderer::new);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.renderSpyglass(stack, poseStack, multiBufferSource, light, slotContext.entity());
    }
}
