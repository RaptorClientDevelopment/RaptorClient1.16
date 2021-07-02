package me.ritomg.raptor.manager.managers;

import club.minnced.discord.rpc.DiscordRPC;
import me.ritomg.raptor.RaptorClient;
import me.ritomg.raptor.gui.RaptorClientGui;
import me.ritomg.raptor.module.modules.client.DiscordRPCModule;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.ritomg.raptor.module.ModuleManager;
import net.minecraft.client.MinecraftClient;


public class DiscordManager {

    private static String discordID = "833714199124246588";
    private static DiscordRichPresence discordRichPresence;
    private static DiscordRPC discordRPC;
    private static Thread thread;

    private static String clientVersion = RaptorClient.ModVersion;

    private static int index;

    static {
        index = 1;
        discordRPC = DiscordRPC.INSTANCE;
        discordRichPresence = new DiscordRichPresence();
    }

    private static DiscordRPCModule rpc = ModuleManager.getModule(DiscordRPCModule.class);

    public static void startRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRPC.INSTANCE.Discord_Initialize(discordID, handlers, true, "");
        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.details = "Join discord.gg/hzzEmtke2M";
        discordRichPresence.state = todiscord(rpc.message.getValue());
        discordRichPresence.largeImageKey = "big";
        discordRichPresence.largeImageText = "RaptorClient" + clientVersion;
        discordRichPresence.smallImageKey = "me";
        discordRichPresence.smallImageText = "RitomG";
        DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordRPC.INSTANCE.Discord_RunCallbacks();
                discordRichPresence.details = todiscord(rpc.message.getValue());
                discordRichPresence.state = "Join discord.gg/hzzEmtke2M";
                DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException interruptedException) {
                }
            }
        }, "RPC-Callback-Handler");
        thread.start();
    }

    public static void stopRPC() {
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
        DiscordRPC.INSTANCE.Discord_Shutdown();
    }

    private static MinecraftClient mc = MinecraftClient.getInstance();

    private static String todiscord(String message) {
        return message
                .replace("{name}", mc.getSession().getUsername())
                .replace("{uuid}", mc.getSession().getSessionId())
                .replace("{rc}", "RaptorClient")
                .replace("{version}", RaptorClient.ModVersion)
                .replace("{mcversion}", mc.getGameVersion())
                .replace("{server}", mc.getServer() != null ? mc.getServer().getServerIp() : "SinglePlayer")
                .replace("{gui}", mc.currentScreen instanceof RaptorClientGui ? "In the advanced hacking console" : "in other menu");
    }

}