package io.github.samipourquoi.endtech.endbot;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EndbotCommandDispatcher {
    public static final CommandDispatcher INSTANCE = new CommandDispatcher();

    static {
        ScoreboardEndbotCommand.register(INSTANCE);
    }
}
