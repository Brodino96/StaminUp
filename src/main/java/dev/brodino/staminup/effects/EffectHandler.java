package dev.brodino.staminup.effects;

import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.effects.negative.StaminaDrainEffect;
import dev.brodino.staminup.effects.negative.StaminaFatigueEffect;
import dev.brodino.staminup.effects.positive.StaminaMaxEffect;
import dev.brodino.staminup.effects.positive.StaminaRegenEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectHandler {

    public static final StatusEffect STAMINA_REGEN = new StaminaRegenEffect(StatusEffectCategory.BENEFICIAL, 16485156); // Orange
    public static final StatusEffect STAMINA_MAX = new StaminaMaxEffect(StatusEffectCategory.BENEFICIAL, 1498003); // Green
    public static final StatusEffect STAMINA_FATIGUE = new StaminaFatigueEffect(StatusEffectCategory.HARMFUL, 5074170); // Blue
    public static final StatusEffect STAMINA_DRAIN = new StaminaDrainEffect(StatusEffectCategory.HARMFUL, 4859205); // Purple

    public static void initialize() {
        registerEffect("stamina_regen", STAMINA_REGEN); // Stamina regens faster (10% per amplifier)
        registerEffect("stamina_max", STAMINA_MAX); // More max stamina (10% per amplifier)
        registerEffect("stamina_fatigue", STAMINA_FATIGUE); // Stamina regens slower (10% per amplifier)
        registerEffect("stamina_drain", STAMINA_DRAIN); // Less max stamina (10% per amplifier)
    }

    private static void registerEffect(String id, StatusEffect effect) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(StaminUp.MOD_ID, id), effect);
    }
}
