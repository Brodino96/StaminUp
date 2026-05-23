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
            var jumpConsumptionBuf = PacketByteBufs.create();
            jumpConsumptionBuf.writeInt(StaminUp.CONFIG.getData().getJumpCost());
            ServerPlayNetworking.send(handler.getPlayer(), StaminUpPackets.JUMP_CONSUMPTION, jumpConsumptionBuf);

            var staminaBuf = PacketByteBufs.create();
            staminaBuf.writeInt(StaminUp.CONFIG.getData().getMaxStamina());
            ServerPlayNetworking.send(handler.getPlayer(), StaminUpPackets.UPDATE_STAMINA, staminaBuf);
        }));
    }
}
