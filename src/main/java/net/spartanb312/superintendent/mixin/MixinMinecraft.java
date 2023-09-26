package net.spartanb312.superintendent.mixin;

import net.minecraft.client.Minecraft;
import net.spartanb312.superintendent.Superintendent;
import net.spartanb312.superintendent.event.events.GameLoopEvent;
import net.spartanb312.superintendent.event.events.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 1, shift = At.Shift.AFTER))
    public void initialize(CallbackInfo ci) {
        Superintendent.INSTANCE.initialize();
    }

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    public void runGameLoopPre(CallbackInfo ci) {
        GameLoopEvent.Pre.INSTANCE.post();
    }

    @Inject(method = "runGameLoop", at = @At("RETURN"))
    public void runGameLoopPost(CallbackInfo ci) {
        GameLoopEvent.Post.INSTANCE.post();
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void runTickPre(CallbackInfo ci) {
        TickEvent.Pre.INSTANCE.post();
    }

    @Inject(method = "runTick", at = @At("RETURN"))
    public void runTickPost(CallbackInfo ci) {
        TickEvent.Post.INSTANCE.post();
    }

    public void dummy () {

    }

}
