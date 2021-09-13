package io.github.samipourquoi.endtech.endbot;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.command.argument.EntityArgumentType;

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
