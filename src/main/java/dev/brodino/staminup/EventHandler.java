package dev.brodino.staminup;

import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class EventHandler {

    public static void initialize() {
        StaminUp.LOGGER.info("Initializing EventHandler");

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(((server, resourceManager, success) -> {
            StaminUp.CONFIG.reload();
        }));

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            StaminUp.SERVER = server;
        });

        ServerTickEvents.END_SERVER_TICK.register((server -> {
            StaminUp.tick();
        }));

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            var jumpConsumption = PacketByteBufs.create();
            jumpConsumption.writeInt(StaminUp.CONFIG.getData().getJumpCost());
            ServerPlayNetworking.send(handler.getPlayer(), StaminUpPackets.JUMP_CONSUMPTION, jumpConsumption);

            var maxStamina = PacketByteBufs.create();
            maxStamina.writeInt(StaminUp.CONFIG.getData().getMaxStamina());
            ServerPlayNetworking.send(handler.getPlayer(), StaminUpPackets.UPDATE_STAMINA, maxStamina);
            ServerPlayNetworking.send(handler.getPlayer(), StaminUpPackets.MAX_STAMINA, maxStamina);
        }));
    }
}
