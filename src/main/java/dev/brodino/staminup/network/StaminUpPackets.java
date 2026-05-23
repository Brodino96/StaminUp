package dev.brodino.staminup.network;

import dev.brodino.staminup.StaminUp;
import net.minecraft.util.Identifier;

public class StaminUpPackets {
    public static final Identifier UPDATE_STAMINA = new Identifier(StaminUp.MOD_ID, "update_stamina");
    public static final Identifier JUMP_CONSUMPTION = new Identifier(StaminUp.MOD_ID, "set_jump_consumption");
    public static final Identifier MAX_STAMINA = new Identifier(StaminUp.MOD_ID, "set_max_stamina");
}
