package io.github.samipourquoi.endtech.helpers;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import static net.minecraft.server.command.CommandManager.literal;

public class RulesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("et-additions").requires((serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).
                then(literal("scoreboardTotals").
                        executes((commandContext) -> sendScoreboardTotalValue(commandContext.getSource())).
                        then(literal("true").
                            executes((commandContext) -> RuleSettings.setScoreboardTotal(commandContext.getSource(), true))).
                        then(literal("false").
                                executes((commandContext) -> RuleSettings.setScoreboardTotal(commandContext.getSource(), false)))));
    }

    public static int sendScoreboardTotalValue(ServerCommandSource source) {
        source.sendFeedback(new TranslatableText("scoreboardTotal is currently set to " + RuleSettings.scoreboardTotals),true);
        return 1;
    }

}

