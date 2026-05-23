package dev.brodino.staminup;

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

    public static final HashMap<UUID, Integer> PLAYERS = new HashMap<>();
    private static int tickCount = 0;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing StaminUp");

        EventHandler.initialize();
        CommandHandler.initialize();
    }

    public static boolean canJump(UUID uuid) {
        if (!PLAYERS.containsKey(uuid)) {
            PLAYERS.put(uuid, CONFIG.getData().getMaxStamina());
            return true;
        }
        return PLAYERS.get(uuid) >= CONFIG.getData().getJumpCost();
    }

    public static void updateStamina(ServerPlayerEntity player, int newStamina) {
        PLAYERS.put(player.getUuid(), newStamina);
        var buf = PacketByteBufs.create();
        buf.writeInt(newStamina);
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_STAMINA, buf);
    }

    public static void tick() {
        tickCount++;

        if (PLAYERS.isEmpty() || SERVER == null) {
            return;
        }

        if (tickCount % 20 != 0) {
            return;
        }

        tickCount = 0;

        int maxStamina = CONFIG.getData().getMaxStamina();

        for (Map.Entry<UUID, Integer> entry : PLAYERS.entrySet()) {
            int stamina = entry.getValue();
            if (stamina == maxStamina) {
                continue;
            }

            UUID playerUuid = entry.getKey();

            int newStamina = Math.min(stamina + CONFIG.getData().getStaminaRecovery(), maxStamina);
            ServerPlayerEntity player = SERVER.getPlayerManager().getPlayer(playerUuid);
            if (player == null) {
                continue;
            }
            updateStamina(player, newStamina);
        }
    }
}
