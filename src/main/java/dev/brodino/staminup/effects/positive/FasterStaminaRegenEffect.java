package dev.brodino.staminup.effects.positive;

import dev.brodino.staminup.EventHandler;
import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.effects.StaminaModifyingEffect;
import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

public class FasterStaminaRegenEffect extends StaminaModifyingEffect {

    public FasterStaminaRegenEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity player)) { return; }
        float newRegen = StaminUp.CONFIG.getData().getStaminaRegen() * this.getModifier(amplifier);
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_STAMINA_REGEN, EventHandler.getBuf(newRegen));
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity player)) { return; }
        float baseRegen = StaminUp.CONFIG.getData().getStaminaRegen();
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_STAMINA_REGEN, EventHandler.getBuf(baseRegen));
    }
}
