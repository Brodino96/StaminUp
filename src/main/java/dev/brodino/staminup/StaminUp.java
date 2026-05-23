package dev.brodino.staminup;

import dev.brodino.staminup.effects.EffectHandler;
import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaminUp implements ModInitializer {

    public static final String MOD_ID = "staminup";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Config CONFIG = new Config(MOD_ID, LOGGER);
    public static MinecraftServer SERVER;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing StaminUp");

        EventHandler.initialize();
        CommandHandler.initialize();
        EffectHandler.initialize();
    }
}
