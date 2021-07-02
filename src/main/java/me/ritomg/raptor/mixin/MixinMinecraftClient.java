package me.ritomg.raptor.mixin;

import me.ritomg.raptor.filemanager.SaveConfig;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;printCrashReport(Lnet/minecraft/util/crash/CrashReport;)V"))
    public void displayCrashReport(CallbackInfo info) {
        SaveConfig.init();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void shutdown(CallbackInfo info) {
        SaveConfig.init();
    }

    @Inject(method = "close", at = @At("HEAD"))
    public void close(CallbackInfo callbackInfo) {
        SaveConfig.init();
    }
}
