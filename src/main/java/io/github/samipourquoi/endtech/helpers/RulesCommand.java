package io.github.samipourquoi.endtech.helpers;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.Map;

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
                                executes((commandContext) -> RuleSettings.setScoreboardTotal(commandContext.getSource(), false)))).
                        then(literal("customScoreboards").
                                then(literal("generate").
                                    executes((commandContext) -> generateCustomScoreboards(commandContext.getSource()))).
                                then(literal("remove").
                                        executes((commandContext) -> removeCustomScoreboards(commandContext.getSource())))));
    }

    public static int sendScoreboardTotalValue(ServerCommandSource source) {
        source.sendFeedback(new TranslatableText("scoreboardTotal is currently set to " + RuleSettings.scoreboardTotals),true);
        return 1;
    }

    public static int generateCustomScoreboards(ServerCommandSource source) {
        Scoreboard scoreboard = source.getMinecraftServer().getScoreboard();

        for (Map.Entry<String, Identifier> value : StatsAccessor.CUSTOM_TAGS.entrySet()) {
            String objectiveName = value.getKey();
            //Currently doesn't automatically generate scoreboards with tags > 16 characters
            if (objectiveName.length() > 16) continue;

            ScoreboardObjective scoreboardObjective = scoreboard.getObjective(objectiveName);
            if (scoreboardObjective != null) continue;

            String criterionName = "minecraft.custom:minecraft." + value.getKey();
            ScoreboardCriterion criterion = ScoreboardCriterion.createStatCriterion(criterionName).orElse(null);
            if (criterion == null) continue;

            scoreboard.addObjective(objectiveName, criterion, new LiteralText(objectiveName), criterion.getCriterionType());
        }

        source.sendFeedback(new TranslatableText("Created custom scoreboards from et-additions"), true);
        return 1;
    }

    public static int removeCustomScoreboards(ServerCommandSource source) {
        Scoreboard scoreboard = source.getMinecraftServer().getScoreboard();

        for (Map.Entry<String, Identifier> value : StatsAccessor.CUSTOM_TAGS.entrySet()) {
            String objectiveName = value.getKey();
            ScoreboardObjective scoreboardObjective = scoreboard.getObjective(objectiveName);
            if (scoreboardObjective == null) continue;

            scoreboard.removeObjective(scoreboardObjective);
        }

        source.sendFeedback(new TranslatableText("Removed all scoreboards from et-additions"), true);
        return 1;
    }
}

