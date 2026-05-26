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

public class HigherStaminaMaxEffect extends StaminaModifyingEffect {

    public HigherStaminaMaxEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity player)) { return; }
        float newMax = StaminUp.CONFIG.getData().getMaxStamina() * this.getModifier(amplifier);
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_MAX_STAMINA, EventHandler.getBuf(newMax));
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity player)) { return; }
        float baseMax = StaminUp.CONFIG.getData().getMaxStamina();
        ServerPlayNetworking.send(player, StaminUpPackets.UPDATE_MAX_STAMINA, EventHandler.getBuf(baseMax));
    }
}
