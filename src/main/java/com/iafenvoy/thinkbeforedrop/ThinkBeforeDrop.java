package com.iafenvoy.thinkbeforedrop;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class ThinkBeforeDrop implements ClientModInitializer {
    public static final String MOD_ID = "thinkbeforedrop";
    public static final String MOD_NAME = "Think Before Drop";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
    }
}