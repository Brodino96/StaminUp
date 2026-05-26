package dev.brodino.staminup;

import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaminaHandler {

    private static final HashMap<UUID, PlayerStamina> PLAYERS = new HashMap<>();
    private static int tickCount = 0;

    private static PlayerStamina getPlayerStamina(UUID uuid) {
        return PLAYERS.computeIfAbsent(uuid, k -> new PlayerStamina(
            StaminUp.CONFIG.getData().getMaxStamina(),
            StaminUp.CONFIG.getData().getMaxStamina(),
            StaminUp.CONFIG.getData().getStaminaRegen()
        ));
    }

    public static void removePlayer(UUID uuid) { PLAYERS.remove(uuid); }

    public static boolean tryJump(ServerPlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) {
            return true;
        }

        PlayerStamina playerStamina = getPlayerStamina(player.getUuid());

        if (playerStamina.getStamina() < StaminUp.CONFIG.getData().getJumpCost()) {
            return false;
        }

        updateStamina(player, playerStamina, playerStamina.getStamina() - StaminUp.CONFIG.getData().getJumpCost());
        return true;
    }

    public static void fillStamina(ServerPlayerEntity player, int percentage) {
        PlayerStamina playerStamina = getPlayerStamina(player.getUuid());
        float value = (percentage * playerStamina.getMaxStamina()) / 100;
        updateStamina(player, playerStamina, value);
    }
    private static void updateStamina(ServerPlayerEntity player, PlayerStamina playerStamina, float newStamina) {
        if (newStamina > playerStamina.getMaxStamina()) {
            newStamina = playerStamina.getMaxStamina();
        }
        playerStamina.setStamina(newStamina);
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_STAMINA, getBuf(newStamina));
    }

    public static void resetMaxStamina(ServerPlayerEntity player) { updateMaxStamina(player, StaminUp.CONFIG.getData().getMaxStamina()); }
    public static void updateMaxStamina(ServerPlayerEntity player, float maxStamina) {
        PlayerStamina playerStamina = getPlayerStamina(player.getUuid());
        playerStamina.setMaxStamina(maxStamina);

        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_MAX_STAMINA, getBuf(maxStamina));

        if (playerStamina.getStamina() > maxStamina) {
            updateStamina(player, playerStamina, maxStamina);
        }
    }

    public static void resetStaminaRegen(ServerPlayerEntity player) { updateStaminaRegen(player, StaminUp.CONFIG.getData().getStaminaRegen()); }
    public static void updateStaminaRegen(ServerPlayerEntity player, float regen) {
        PlayerStamina playerStamina = getPlayerStamina(player.getUuid());
        playerStamina.setRegen(regen);
        // Doesn't require client sync since packets are sent on regen
    }

    public static void tick() {
        tickCount++;
        if (tickCount % 20 != 0) { return; }
        tickCount = 0;

        if (StaminUp.SERVER == null) {
            return;
        }

        PlayerManager playerManager = StaminUp.SERVER.getPlayerManager();

        for (Map.Entry<UUID, PlayerStamina> entry : PLAYERS.entrySet()) {
            PlayerStamina playerStamina = entry.getValue();
            if (playerStamina.getStamina() >= playerStamina.getMaxStamina()) {
                continue;
            }

            float newStamina = Math.min(playerStamina.getStamina() + playerStamina.getRegen(), playerStamina.getMaxStamina());
            ServerPlayerEntity player = playerManager.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }

            updateStamina(player, playerStamina, newStamina);
        }
    }

    private static class PlayerStamina {
        private float maxStamina;
        private float stamina;
        private float regen;

        public PlayerStamina(float maxStamina, float stamina, float regen) {
            this.maxStamina = maxStamina;
            this.stamina = stamina;
            this.regen = regen;
        }

        public void setMaxStamina(float maxStamina) { this.maxStamina = maxStamina; }
        public void setStamina(float stamina) { this.stamina = stamina; }
        public void setRegen(float regen) { this.regen = regen; }

        public float getMaxStamina() { return this.maxStamina; }
        public float getStamina() { return this.stamina; }
        public float getRegen() { return this.regen; }

    }

    public static PacketByteBuf getBuf(float value) {
        var buf = PacketByteBufs.create();
        buf.writeFloat(value);
        return buf;
    }
}
