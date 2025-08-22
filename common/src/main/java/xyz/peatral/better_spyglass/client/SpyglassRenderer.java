package xyz.peatral.better_spyglass.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.peatral.better_spyglass.player.extensions.ISpyglassPlayer;

public class SpyglassRenderer {
    public void renderSpyglass(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, LivingEntity livingEntity) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if (livingEntity instanceof ISpyglassPlayer spyglassPlayer
                && spyglassPlayer.better_spyglass$isUsingSpyglass()) {
            // I think we can naively assume the player is using the spyglass from the belt
            // There is some odd stuff happening so that a simple comparison of item stacks doesn't work but at this point I'm fine with this simpler workaround
            return;
        }
        BakedModel spyglassModel = itemRenderer.getModel(
                stack,
                livingEntity.level(),
                livingEntity,
                1
        );

        poseStack.pushPose();

        if (livingEntity.isCrouching()) {
            poseStack.translate(0.0F, 0.15F, 0.32F);
        }

        double xOffset = 0.16;
        if ((livingEntity instanceof AbstractClientPlayer player) && player.getMainArm() == HumanoidArm.RIGHT) {
            xOffset *= -1;
        }
        poseStack.translate(xOffset, 0.6, 0.16);
        poseStack.mulPose(Direction.DOWN.getRotation());
        poseStack.scale(0.7f, 0.7f, 0.7f);

        itemRenderer.render(
                stack,
                ItemDisplayContext.NONE,
                true,
                poseStack,
                multiBufferSource,
                light,
                OverlayTexture.NO_OVERLAY,
                spyglassModel
        );

        poseStack.popPose();
    }
}
