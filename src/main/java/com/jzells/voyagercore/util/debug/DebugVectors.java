package com.jzells.voyagercore.util.debug;

import com.jzells.voyagercore.VoyagerCore;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = VoyagerCore.MOD_ID)
public class DebugVectors {

    public static final Map<String, DebugVector> VECTORS = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (!event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS)) return;
        Vec3 cam = event.getCamera().getPosition();

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(-cam.x, -cam.y, -cam.z);

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        Matrix4f pose = poseStack.last().pose();

        for (DebugVector v : VECTORS.values()) {
            Vec3 start = v.start();
            Vec3 end = v.end();


            Color color = v.color();
            float r = color.getRed() / 255F;
            float g = color.getGreen() / 255F;
            float b = color.getBlue() / 255F;
            float a = color.getAlpha() / 255F;

            bufferBuilder.vertex(pose, (float) start.x, (float) start.y, (float) start.z)
                    .color(r, g, b, a)
                    .endVertex();

            bufferBuilder.vertex(pose, (float) end.x, (float) end.y, (float) end.z)
                    .color(r, g, b, a)
                    .endVertex();


            if (v.drawArrow()) {
                Vec3 dir = end.subtract(start);
                Vec3 n = dir.normalize();

                double l = Math.min(0.5, dir.length() * 0.2);
                double w = l * 0.5;

                Vec3 right = n.cross(new Vec3(0, 1, 0)).normalize();
                Vec3 up = right.cross(n).normalize();

                Vec3 base = end.subtract(n.scale(l));

                Vec3[] prongs = {
                        base.add(right.scale(w)),
                        base.subtract(right.scale(w)),
                        base.add(up.scale(w)),
                        base.subtract(up.scale(w))
                };

                for (Vec3 prong : prongs) {
                    bufferBuilder.vertex(pose, (float) end.x, (float) end.y, (float) end.z).color(r, g, b, a).endVertex();
                    bufferBuilder.vertex(pose, (float) prong.x, (float) prong.y, (float) prong.z).color(r, g, b, a).endVertex();
                }
            }
        }



        tesselator.end();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();

        poseStack.popPose();
    }
}

