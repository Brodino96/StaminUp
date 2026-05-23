package dev.brodino.staminup.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class StaminaHud {

    // Total height of the HUD group:
    // hotbar [22] | xp bar [7] | status rows [10]
    private static final int BAR_HEIGHT = 29;
    private static final int BAR_WIDTH = 3;
    // Gap between the right edge of the hotbar and the stamina bar
    private static final int BAR_GAP = 5;
    // The hotbar extends 91px to the right of the screen center
    private static final int HOTBAR_HALF_WIDTH = 91;

    private static final int COLOR_BACKGROUND = 0xAA888888;
    private static final int COLOR_FILL = 0xFFFFFFFF;

    public static void register() { HudRenderCallback.EVENT.register(StaminaHud::render); }

    private static void render(MatrixStack matrices, float tickDelta) {
        float maxStamina = StaminUpClient.maxStamina;
        if (maxStamina <= 0) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) {
            return;
        }

        int screenW = client.getWindow().getScaledWidth();
        int screenH = client.getWindow().getScaledHeight();

        int barX = (screenW / 2) + HOTBAR_HALF_WIDTH + BAR_GAP;
        int barY = screenH - BAR_HEIGHT;

        DrawableHelper.fill(matrices, barX, barY, barX + BAR_WIDTH, barY + BAR_HEIGHT, COLOR_BACKGROUND);

        float stamina = Math.max(0f, Math.min(StaminUpClient.stamina, maxStamina));
        int fillHeight = Math.round(BAR_HEIGHT * stamina / maxStamina);
        if (fillHeight <= 0) {
            return;
        }
        DrawableHelper.fill(
            matrices, barX,
            barY + (BAR_HEIGHT - fillHeight),
            barX + BAR_WIDTH,
            barY + BAR_HEIGHT,
            COLOR_FILL
        );
    }
}
