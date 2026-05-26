package dev.brodino.staminup;

import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class EventHandler {

    public static void initialize() {
        StaminUp.LOGGER.info("Initializing EventHandler");

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(((server, resourceManager, success) -> {
            StaminUp.CONFIG.reload();
            refreshClientConfigs();
        }));

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            sendConfigToClient(handler.getPlayer());
        }));

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            StaminUp.SERVER = server;
        });
    }

    public static void refreshClientConfigs() {
        for (ServerPlayerEntity player : StaminUp.SERVER.getPlayerManager().getPlayerList()) {
            sendConfigToClient(player);
        }
    }

    public static void sendConfigToClient(ServerPlayerEntity player) {
        Config.Type data = StaminUp.CONFIG.getData();
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_JUMP_CONSUMPTION, getBuf(data.getJumpCost()));
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_MAX_STAMINA, getBuf(data.getMaxStamina()));
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_STAMINA, getBuf(data.getMaxStamina()));
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_STAMINA_REGEN, getBuf(data.getStaminaRegen()));
    }

    public static PacketByteBuf getBuf(float value) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeFloat(value);
        return buf;
    }
}
