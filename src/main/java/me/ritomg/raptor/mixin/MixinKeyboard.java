package me.ritomg.raptor.mixin;

import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.module.ModuleManager;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.ritomg.raptor.RaptorClient;
import me.ritomg.raptor.event.events.KeyinputEvent;
import net.minecraft.client.Keyboard;

@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKeyEvent(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        if (key >= 0) {
            KeyinputEvent.Global event = new KeyinputEvent.Global(key, scanCode, action, modifiers);
            RaptorClient.eventBus.post(event);

            if (event.isCancelled()) {
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
    private void onKeyEvent_1(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        if (key != GLFW.GLFW_KEY_UNKNOWN) {
            for (Module module : ModuleManager.getModules()) {
                if (module.getBind() != key) continue;
                module.toggle();
            }
        }

        if (key >= 0) {
            KeyinputEvent.InWorld event = new KeyinputEvent.InWorld(key, scanCode);
            RaptorClient.eventBus.post(event);

            if (event.isCancelled()) {
                callbackInfo.cancel();
            }
        }
    }

}
