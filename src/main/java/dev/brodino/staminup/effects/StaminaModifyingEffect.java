package dev.brodino.staminup.effects;

import dev.brodino.staminup.StaminUp;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class StaminaModifyingEffect extends StatusEffect {

    public StaminaModifyingEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    public float getModifier(int amplifier) {
        return 1.0f + 0.1f * (amplifier + 1);
    }

    public float getNegativeModifier(int amplifier) {
        return Math.max(0.0f, 1.0f - 0.1f * (amplifier + 1));
    }
}
