package io.github.samipourquoi.endtech.endbot;

import com.mojang.brigadier.CommandDispatcher;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class BackupEndbotCommand {
    public static void register(CommandDispatcher<Object> dispatcher) {
        dispatcher.register(
                literal("backup")
        );
    }
}
