package dev.brodino.staminup.mixin;

import dev.brodino.staminup.StaminaHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void jump(CallbackInfo ci) {
        if (!((Object) this instanceof ServerPlayerEntity player)) {
            return;
        }

        if (!StaminaHandler.tryJump(player)) {
            ci.cancel();
        }
    }
}
