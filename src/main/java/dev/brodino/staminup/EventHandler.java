package dev.brodino.staminup;

import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class EventHandler {

    public static void initialize() {
        StaminUp.LOGGER.info("Initializing EventHandler");

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(((server, resourceManager, success) -> {
            StaminUp.CONFIG.reload();
        }));

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            StaminUp.SERVER = server;
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            StaminUp.SERVER = null;
        });

        ServerTickEvents.END_SERVER_TICK.register((server -> {
            StaminaHandler.tick();
        }));

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            sendPacketsOnJoin(handler.getPlayer());
        }));

        ServerPlayConnectionEvents.DISCONNECT.register(((handler, server) -> {
            StaminaHandler.removePlayer(handler.getPlayer().getUuid());
        }));
    }

    private static void sendPacketsOnJoin(ServerPlayerEntity player) {
        PacketByteBuf maxStamina = StaminaHandler.getBuf(StaminUp.CONFIG.getData().getMaxStamina());
        PacketByteBuf jumpConsumption = StaminaHandler.getBuf(StaminUp.CONFIG.getData().getJumpCost());
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_JUMP_CONSUMPTION, jumpConsumption);
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_MAX_STAMINA, maxStamina);
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_STAMINA, maxStamina);
    }
}
