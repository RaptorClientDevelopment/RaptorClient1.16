package me.ritomg.raptor.module.modules.client;

import me.ritomg.raptor.manager.managers.DiscordManager;
import me.ritomg.raptor.module.Category;
import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.setting.values.StringSetting;

@Module.info(name = "DiscordRPC", category = Category.Client, Description = "Add Custom Message to Discord RPC")
public class DiscordRPCModule extends Module{

    public StringSetting message = registerString("Message", "Playing in {server}");

    protected void onEnable() {
        DiscordManager.startRPC();
    }
}
