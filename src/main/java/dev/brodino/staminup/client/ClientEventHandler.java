package dev.brodino.staminup.client;

import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@Environment(EnvType.CLIENT)
public class ClientEventHandler {

    public static void initialize() {
        StaminUp.LOGGER.info("Initializing ClientEventHandler");

        // Current stamina value — sent on join and when effects directly alter stamina
        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.UPDATE_STAMINA, (client, handler, buf, sender) -> {
            float value = buf.readFloat();
            client.execute(() -> StaminaHandler.setStamina(value));
        });

        // Effective max stamina — sent on join and when max-altering effects apply/remove
        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.UPDATE_MAX_STAMINA, (client, handler, buf, sender) -> {
            float value = buf.readFloat();
            client.execute(() -> {
                StaminaHandler.setBaseMaxStamina(value);
                StaminaHandler.setMaxStamina(value);
            });
        });

        // Jump cost — sent on join only (config-driven, not affected by effects)
        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.UPDATE_JUMP_CONSUMPTION, (client, handler, buf, sender) -> {
            float value = buf.readFloat();
            client.execute(() -> {
                StaminaHandler.setBaseJumpCost(value);
                StaminaHandler.setJumpCost(value);
            });
        });

        // Stamina regen rate — sent on join and when regen-altering effects apply/remove
        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.UPDATE_STAMINA_REGEN, (client, handler, buf, sender) -> {
            float value = buf.readFloat();
            client.execute(() -> {
                StaminaHandler.setBaseStaminaRegen(value);
                StaminaHandler.setStaminaRegen(value);
            });
        });

        // Instant stamina fill — sent by InstantStaminaEffect; payload is fill percentage (1–10)
        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.FILL_STAMINA, (client, handler, buf, sender) -> {
            float percentage = buf.readFloat();
            client.execute(() -> StaminaHandler.fillStamina(percentage));
        });

        ClientPlayNetworking.registerGlobalReceiver(StaminUpPackets.MOVEMENT_CHECK, (client, handler, buf, sender) -> {{
            float movementCheck = buf.readFloat();
            client.execute(() -> StaminaHandler.setMovementCheck(movementCheck));
        }});

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) { return; }
            if (client.player.isCreative() || client.player.isSpectator()) { return; }
            StaminaHandler.tick();
        });

        HudRenderCallback.EVENT.register(StaminaHud::render);
    }

}
