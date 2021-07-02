//package com.raptordev.raptor.api.manager.managers;
//
//import club.minnced.discord.rpc.DiscordEventHandlers;
//import club.minnced.discord.rpc.DiscordRPC;
//import club.minnced.discord.rpc.DiscordRichPresence;
//import com.raptordev.raptor.api.util.player.PlayerUtil;
//import com.raptordev.raptor.client.RaptorClient;
//import com.raptordev.raptor.client.module.ModuleManager;
//import com.raptordev.raptor.client.module.modules.combat.RCCrystalAura;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiMainMenu;
//
//
//public class DiscordManager {
//
//    private static String discordID = "833714199124246588";
//    private static DiscordRichPresence discordRichPresence;
//    private static DiscordRPC discordRPC;
//    private static Thread thread;
//
//    private static String clientVersion = RaptorClient.MODVER;
//
//    private static int index;
//
//    static {
//        index = 1;
//        discordRPC = DiscordRPC.INSTANCE;
//        discordRichPresence = new DiscordRichPresence();
//    }
//
//    public static void startRPC() {
//        DiscordEventHandlers handlers = new DiscordEventHandlers();
//        DiscordRPC.INSTANCE.Discord_Initialize(discordID, handlers, true, "");
//        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
//        discordRichPresence.details = Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu ? "In the main menu." : "Playing " + (Minecraft.getMinecraft().currentServerData != null ? ("on " + Minecraft.getMinecraft().currentServerData.serverIP + ".") : " singleplayer.");
//        discordRichPresence.state = ModuleManager.getModule(RCCrystalAura.class).isAttacking ? "Using the godly CA" + "at health of " + PlayerUtil.getHealth(): "Join https://discord.gg/hzzEmtke2M";
//        discordRichPresence.largeImageKey = "big";
//        discordRichPresence.largeImageText = "RaptorClient" + clientVersion;
//        discordRichPresence.smallImageKey = "me";
//        discordRichPresence.smallImageText = "RitomG";
//        DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
//        thread = new Thread(() -> {
//            while (!Thread.currentThread().isInterrupted()) {
//                DiscordRPC.INSTANCE.Discord_RunCallbacks();
//                discordRichPresence.details = Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu ? "In the main menu." : "Playing " + (Minecraft.getMinecraft().currentServerData != null ? ("on " + Minecraft.getMinecraft().currentServerData.serverIP + ".") : " singleplayer.");
//                discordRichPresence.state = ModuleManager.getModule(RCCrystalAura.class).isAttacking ? "Using the godly CA" + "at health of " + PlayerUtil.getHealth() : "Join discord.gg/hzzEmtke2M";
//                DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
//                try {
//                    Thread.sleep(2000L);
//                } catch (InterruptedException interruptedException) {
//                }
//            }
//        }, "RPC-Callback-Handler");
//        thread.start();
//    }
//
//    public static void stopRPC() {
//        if (thread != null && !thread.isInterrupted()) {
//            thread.interrupt();
//        }
//        DiscordRPC.INSTANCE.Discord_Shutdown();
//    }
//}