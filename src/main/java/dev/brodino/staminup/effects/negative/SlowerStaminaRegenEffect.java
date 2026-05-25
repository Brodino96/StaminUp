package dev.brodino.staminup.effects.negative;

import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.StaminaHandler;
import dev.brodino.staminup.effects.StaminaModifyingEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

public class SlowerStaminaRegenEffect extends StaminaModifyingEffect {

    public SlowerStaminaRegenEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity player)) { return; }
        StaminaHandler.updateStaminaRegen(player, StaminUp.CONFIG.getData().getStaminaRegen() / this.getModifier(amplifier));
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity player)) { return; }
        StaminaHandler.resetStaminaRegen(player);
    }
}
