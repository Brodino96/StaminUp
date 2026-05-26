package dev.brodino.staminup;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandHandler {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, ra, e) -> {
            registerCommand(dispatcher);
        }));
    }

    private static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("staminup")
                .requires(src -> src.hasPermissionLevel(2))
                .then(getReloadCommand())
        );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> getReloadCommand() {
        return CommandManager.literal("reloadConfig")
            .executes(context -> {
                if (StaminUp.CONFIG.reload()) {
                    EventHandler.refreshClientConfigs();
                    return 1;
                } else {
                    return 0;
                }
            });
    }
}
