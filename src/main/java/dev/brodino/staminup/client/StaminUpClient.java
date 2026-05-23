package dev.brodino.staminup.client;

import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class StaminUpClient implements ClientModInitializer {

    public static int stamina = 0;
    public static int jumpConsumption = 0;
    public static int maxStamina;

    @Override
    public void onInitializeClient() {
        StaminUp.LOGGER.info("Initializing StaminUp Client");
        this.registerEvents();
        StaminaHud.register();
    }

    private void registerEvents() {
        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.UPDATE_STAMINA, (c, cpnh, buf, ps) -> {
            stamina = buf.readInt();
            StaminUp.LOGGER.info("New stamina {}", stamina);
        });

        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.JUMP_CONSUMPTION, (c, cpnh, buf, ps) -> {
            jumpConsumption = buf.readInt();
            StaminUp.LOGGER.info("New jumpConsumption {}", jumpConsumption);
        });

        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.MAX_STAMINA, (c, cpnh, buf, ps) -> {
            maxStamina = buf.readInt();
            StaminUp.LOGGER.info("New maxStamina {}", maxStamina);
        });
    }

    public static boolean canJump() {
        return stamina >= jumpConsumption;
    }
}
