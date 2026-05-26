package dev.brodino.staminup.effects;

import dev.brodino.staminup.EventHandler;
import dev.brodino.staminup.network.StaminUpPackets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
        float percentage = Math.min(amplifier + 1, 10);
        ServerPlayNetworking.send(player, StaminUpPackets.FILL_STAMINA, EventHandler.getBuf(percentage));
    }

    @Override
    public boolean isInstant() { return true; }
}
