package dev.brodino.staminup.mixin.client;

import dev.brodino.staminup.client.StaminaHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void jump(CallbackInfo ci) {
        if (!((Object) this instanceof ClientPlayerEntity player)) {
            return;
        }
        
        if (!MinecraftClient.getInstance().options.jumpKey.isPressed() || player.isClimbing()) {
            return;
        }

        if (StaminaHandler.getMovementCheck() && !player.isSprinting()) {
            return;
        }

        if (!StaminaHandler.tryJump()) {
            ci.cancel();
            return;
        }
    }
}
