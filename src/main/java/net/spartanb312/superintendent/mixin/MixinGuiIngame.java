package net.spartanb312.superintendent.mixin;

import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    @Inject(method = "renderGameOverlay", at = @At("HEAD"))
    public void renderGameOverlayPre(float partialTicks, CallbackInfo ci) {

    }

    @Inject(method = "renderGameOverlay", at = @At("HEAD"))
    public void renderGameOverlayPost(float partialTicks, CallbackInfo ci) {

    }

}
