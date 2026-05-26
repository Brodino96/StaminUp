package dev.brodino.staminup.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.brodino.staminup.StaminUp;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StaminaHud {

    private static final int ICON_SIZE = 8;
    private static final int ICON_COUNT = 15;

    private static final int ICON_BOTTOM_OFFSET = 37;

    private static final String ICON_PATH = "textures/hud/status/";

    public static void render(MatrixStack matrices, float tickDelta) {
        float maxStamina = StaminaHandler.getMaxStamina();
        if (maxStamina <= 0) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null || client.options.hudHidden || player.isCreative() || player.isSpectator()) {
            return;
        }

        float stamina = Math.max(0f, Math.min(StaminaHandler.getStamina(), maxStamina));
        float ratio = stamina / maxStamina;

        int iconIndex = Math.round(ratio * (ICON_COUNT - 1)) + 1;
        iconIndex = Math.max(1, Math.min(ICON_COUNT, iconIndex));

        int screenW = client.getWindow().getScaledWidth();
        int screenH = client.getWindow().getScaledHeight();

        int x = (screenW / 2) - (ICON_SIZE / 2);
        int y = screenH - ICON_BOTTOM_OFFSET - ICON_SIZE;

        RenderSystem.setShaderTexture(0, getIcon(iconIndex));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
        RenderSystem.disableBlend();
    }

    private static Identifier getIcon(int index) {
        return new Identifier(StaminUp.MOD_ID, ICON_PATH + index + ".png");
    }
}
