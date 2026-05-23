package dev.brodino.staminup.mixin;

import dev.brodino.staminup.StaminUp;
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
        PlayerEntity player = (PlayerEntity) (Object) this;

        UUID uuid = player.getUuid();
        if (!StaminUp.canJump(uuid)) {
            StaminUp.LOGGER.info("{} can't jump", player.getName().getString());
            ci.cancel();
            return;
        }

        StaminUp.LOGGER.info("{} just jumped", player.getName().getString());
        int newStamina = StaminUp.PLAYERS.getOrDefault(player.getUuid(), StaminUp.CONFIG.getData().getMaxStamina()) - StaminUp.CONFIG.getData().getJumpCost();
        StaminUp.updateStamina((ServerPlayerEntity) (Object) player, newStamina);
    }
}
