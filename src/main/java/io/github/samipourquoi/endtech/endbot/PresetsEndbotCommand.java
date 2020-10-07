package io.github.samipourquoi.endtech.endbot;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ObjectiveArgumentType;
import net.minecraft.command.argument.TextArgumentType;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class PresetsEndbotCommand {
    public static void register(CommandDispatcher<Object> dispatcher) {
        dispatcher.register(
            literal("presets")
                .then(literal("display")
                    .then(argument("preset", StringArgumentType.string())))
                .then(literal("set")
                    .then(argument("objectives", TextArgumentType.text())))
                .then(literal("remove")
                    .then(argument("preset", StringArgumentType.string())))
                .then(literal("list"))
                .then(literal("delay")
                    .then(argument("delay", IntegerArgumentType.integer(1, 60*5-1))))
        );
    }
}
