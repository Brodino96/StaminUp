package dev.brodino.staminup.network;

import dev.brodino.staminup.StaminUp;
import net.minecraft.util.Identifier;

public class StaminUpPackets {
    public static final Identifier UPDATE_STAMINA = new Identifier(StaminUp.MOD_ID, "update_stamina");
    public static final Identifier UPDATE_JUMP_CONSUMPTION = new Identifier(StaminUp.MOD_ID, "update_jump_consumption");
    public static final Identifier UPDATE_MAX_STAMINA = new Identifier(StaminUp.MOD_ID, "update_max_stamina");
}
