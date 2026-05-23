package dev.brodino.staminup.mixin;

import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.StaminaHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Environment(EnvType.SERVER)
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void jump(CallbackInfo ci) {
        PlayerEntity p = (PlayerEntity) (Object) this;
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) p;

        boolean jumped = StaminaHandler.tryJump(player);
        if (!jumped) {
            ci.cancel();
            return;
        }
    }
}
