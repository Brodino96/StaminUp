package dev.brodino.staminup.client;

import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class StaminUpClient implements ClientModInitializer {

    public static float stamina = 0;
    public static float jumpConsumption = 0;
    public static float maxStamina;

    @Override
    public void onInitializeClient() {
        StaminUp.LOGGER.info("Initializing StaminUp Client");
        this.registerEvents();
        StaminaHud.register();
    }

    private void registerEvents() {
        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.UPDATE_STAMINA, (c, cpnh, buf, ps) -> {
            stamina = buf.readFloat();
        });

        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.UPDATE_JUMP_CONSUMPTION, (c, cpnh, buf, ps) -> {
            jumpConsumption = buf.readFloat();
        });

        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.UPDATE_MAX_STAMINA, (c, cpnh, buf, ps) -> {
            maxStamina = buf.readFloat();
        });
    }

    public static boolean canJump() {
        return stamina >= jumpConsumption;
    }
}
