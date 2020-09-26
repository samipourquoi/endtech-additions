package io.github.samipourquoi.endtech.endbot;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.command.argument.EntityArgumentType;

import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

@Environment(EnvType.CLIENT)
public class ScoreboardEndbotCommand {
    public static void register(CommandDispatcher<Object> dispatcher) {
        dispatcher.register(
            literal("scoreboard")
                    .then(argument("objective", EveryScoreboardArgumentType.everyObjective())
                            .then(literal("list"))
                            .then(literal("total"))
                            .then(literal("query")
                                    .then(argument("player", EntityArgumentType.player()))))
                    //.then(literal("clear"))
        );
    }
}
