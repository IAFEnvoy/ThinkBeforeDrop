package com.iafenvoy.thinkbeforedrop._loader.fabric;

//? fabric {
import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.thinkbeforedrop.TBDConfig;
import net.fabricmc.api.ClientModInitializer;

public final class ThinkBeforeDropFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigManager.getInstance().registerConfigHandler(TBDConfig.INSTANCE);
    }
}
