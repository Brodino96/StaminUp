package dev.brodino.staminup;

import dev.brodino.staminup.effects.EffectHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
