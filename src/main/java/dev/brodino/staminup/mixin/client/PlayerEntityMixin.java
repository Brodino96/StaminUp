package dev.brodino.staminup.mixin.client;

import dev.brodino.staminup.client.StaminUpClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Environment(EnvType.CLIENT)
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void jump(CallbackInfo ci) {
        if (!StaminUpClient.canJump()) {
            ci.cancel();
            return;
        }

        StaminUpClient.stamina -= StaminUpClient.jumpConsumption;
    }
}
