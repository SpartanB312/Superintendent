package net.spartanb312.superintendent.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.spartanb312.superintendent.event.events.render.Render2DEvent;
import net.spartanb312.superintendent.event.events.render.Render3DEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V", shift = At.Shift.AFTER))
    public void renderGameOverlayPost(float partialTicks, long nanoTime, CallbackInfo ci) {
        new Render2DEvent(new ScaledResolution(Minecraft.getMinecraft()), partialTicks).post();
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE_STRING", target = "net/minecraft/profiler/Profiler.endStartSection(Ljava/lang/String;)V", args = "ldc=hand", shift = At.Shift.BEFORE))
    public void onStartHand(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        new Render3DEvent(pass, partialTicks).post();
    }

}
