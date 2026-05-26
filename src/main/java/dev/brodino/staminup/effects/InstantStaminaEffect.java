package dev.brodino.staminup.effects;

import dev.brodino.staminup.StaminaHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

public class InstantStaminaEffect extends StatusEffect {

	public InstantStaminaEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		if (!(entity instanceof ServerPlayerEntity player)) { return; }
		StaminaHandler.fillStamina(player, Math.min(amplifier + 1, 10));
	}

	@Override
	public boolean isInstant() {
		return true;
	}
}
