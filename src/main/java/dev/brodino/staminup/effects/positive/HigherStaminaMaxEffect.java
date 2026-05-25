package dev.brodino.staminup.effects.positive;

import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.StaminaHandler;
import dev.brodino.staminup.effects.StaminaModifyingEffect;
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
        StaminaHandler.updateMaxStamina(player, StaminUp.CONFIG.getData().getMaxStamina() * this.getModifier(amplifier));
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity player)) { return; }
        StaminaHandler.resetMaxStamina(player);
    }
}
