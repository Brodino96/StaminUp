package dev.brodino.staminup.client;

import dev.brodino.staminup.StaminUp;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class StaminUpClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        StaminUp.LOGGER.info("Initializing StaminUp Client");
        ClientEventHandler.initialize();
    }
}
