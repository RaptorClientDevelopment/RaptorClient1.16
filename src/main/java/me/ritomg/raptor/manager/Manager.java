package me.ritomg.raptor.manager;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;

public interface Manager {

    default MinecraftClient getMinecraft() {
        return MinecraftClient.getInstance();
    }

    default ClientPlayerEntity getPlayer() {
        return getMinecraft().player;
    }

    default ClientWorld getWorld() {
        return getMinecraft().world;
    }

    default Profiler getProfiler() {
        return getMinecraft().getProfiler();
    }
}