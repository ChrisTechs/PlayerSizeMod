package io.github.christechs.psm.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.christechs.psm.LocationUtils;
import io.github.christechs.psm.config.PSMConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Unique
    private static final Map<Integer, Float> SCALE_CACHE = new HashMap<>();

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void psm$extractAndCalculateScale(Avatar entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
        if (!PSMConfig.general().enabled) {
            SCALE_CACHE.put(state.id, 1.0f);
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        boolean isClientPlayer = (mc.player != null && entity.getId() == mc.player.getId());
        boolean isNPC = false;
        boolean isFkt = false;

        Team team = entity.getTeam();
        if (team != null) {
            String teamName = team.getName();
            if (teamName != null && teamName.toLowerCase().startsWith("fkt")) {
                isFkt = true;
            } else if (team.getNameTagVisibility() == Team.Visibility.NEVER) {
                isNPC = true;
            }
        }

        float scale;
        if (isFkt) {
            scale = 1.0f;
        } else if (isClientPlayer) {
            scale = PSMConfig.general().playerSize;
        } else if (isNPC) {
            scale = PSMConfig.general().npcSize;
        } else {
            scale = PSMConfig.general().otherPlayerSize;
        }

        scale = LocationUtils.clampScale(scale);

        SCALE_CACHE.put(state.id, scale);

        if (scale != 1.0f && state.nameTagAttachment != null) {
            state.nameTagAttachment = new Vec3(
                    state.nameTagAttachment.x,
                    state.nameTagAttachment.y * scale,
                    state.nameTagAttachment.z
            );
        }
    }

    @Inject(method = "scale(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At("TAIL"))
    private void psm$applyPoseStackScale(AvatarRenderState state, PoseStack poseStack, CallbackInfo ci) {
        float scale = SCALE_CACHE.getOrDefault(state.id, 1.0f);

        if (scale != 1.0f) {
            poseStack.scale(scale, scale, scale);
        }
    }
}