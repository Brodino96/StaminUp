package dev.brodino.staminup.client;

import dev.brodino.staminup.StaminUp;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class StaminaHandler {

    // Base values
    private static float baseMaxStamina = 0f;
    private static float baseStaminaRegen = 0f;
    private static float baseJumpCost = 0f;
    private static float movementCheck = 0f;

    // Player values
    private static float maxStamina = 0f;
    private static float staminaRegen = 0f;
    private static float jumpCost = 0f;
    private static float stamina = 0f;

    public static float getBaseMaxStamina() { return baseMaxStamina; }
    public static void setBaseMaxStamina(float value) { baseMaxStamina = value; }

    public static float getBaseStaminaRegen() { return baseStaminaRegen; }
    public static void setBaseStaminaRegen(float value) { baseStaminaRegen = value; }

    public static float getBaseJumpCost() { return baseJumpCost; }
    public static void setBaseJumpCost(float value) { baseJumpCost = value; }

    public static boolean getMovementCheck() { return movementCheck > 0f; }
    public static void setMovementCheck(float value) { movementCheck = value; }

    public static float getMaxStamina() { return maxStamina; }
    public static void setMaxStamina(float value) {
        maxStamina = value;
        if (stamina > maxStamina) {
            stamina = maxStamina;
        }
    }

    public static float getStaminaRegen() { return staminaRegen; }
    public static void setStaminaRegen(float value) { staminaRegen = value; }

    public static float getJumpCost() { return jumpCost; }
    public static void setJumpCost(float value) { jumpCost = value; }

    public static float getStamina() { return stamina; }
    public static void setStamina(float value) { stamina = Math.max(0f, Math.min(value, maxStamina)); }

    public static boolean tryJump() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || player.isCreative() || player.isSpectator()) {
            return true;
        }

        if (stamina < jumpCost) {
            return false;
        }

        stamina = Math.max(0f, stamina - jumpCost);
        return true;
    }

    public static void fillStamina(float percentage) {
        float fill = (percentage / 10f) * maxStamina;
        stamina = Math.min(stamina + fill, maxStamina);
    }

    public static void tick() {
        if (stamina >= maxStamina) return;
        stamina = Math.min(stamina + (staminaRegen / 20f), maxStamina);
    }
}
