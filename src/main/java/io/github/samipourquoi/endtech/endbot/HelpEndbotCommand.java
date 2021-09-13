package io.github.samipourquoi.endtech.endbot;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

@Environment(EnvType.CLIENT)
public class HelpEndbotCommand {
    public static void register(CommandDispatcher<Object> dispatcher) {
        dispatcher.register(
                literal("help")
                        .then(literal("backup"))
                        .then(literal("help"))
                        .then(literal("pos"))
                        .then(literal("scoreboard"))
        );
    }
}
