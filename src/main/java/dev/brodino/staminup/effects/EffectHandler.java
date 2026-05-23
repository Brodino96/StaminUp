package dev.brodino.staminup.effects;

import dev.brodino.staminup.StaminUp;
import dev.brodino.staminup.effects.negative.LowerStaminaMaxEffect;
import dev.brodino.staminup.effects.negative.SlowerStaminaRegenEffect;
import dev.brodino.staminup.effects.positive.HigherStaminaMaxEffect;
import dev.brodino.staminup.effects.positive.FasterStaminaRegenEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectHandler {

    public static final StatusEffect FASTER_STAMINA_REGEN = new FasterStaminaRegenEffect(StatusEffectCategory.BENEFICIAL, 16485156); // Orange
    public static final StatusEffect HIGHER_STAMINA_MAX = new HigherStaminaMaxEffect(StatusEffectCategory.BENEFICIAL, 1498003); // Green
    public static final StatusEffect SLOWER_STAMINA_REGEN = new SlowerStaminaRegenEffect(StatusEffectCategory.HARMFUL, 5074170); // Blue
    public static final StatusEffect LOWER_STAMINA_MAX = new LowerStaminaMaxEffect(StatusEffectCategory.HARMFUL, 4859205); // Purple

    public static void initialize() {
        registerEffect("faster_stamina_regen", FASTER_STAMINA_REGEN);
        registerEffect("higher_stamina_max", HIGHER_STAMINA_MAX);
        registerEffect("slower_stamina_regen", SLOWER_STAMINA_REGEN);
        registerEffect("lower_stamina_max", LOWER_STAMINA_MAX);
    }

    private static void registerEffect(String id, StatusEffect effect) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(StaminUp.MOD_ID, id), effect);
    }
}
