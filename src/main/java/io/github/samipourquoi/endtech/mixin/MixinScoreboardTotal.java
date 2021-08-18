package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.ETAdditionsSettings;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;

@Mixin(ServerScoreboard.class)
public class MixinScoreboardTotal extends Scoreboard {
    @Shadow @Final private MinecraftServer server;

    @Inject(method = "updateScore", at = @At("TAIL"))
    public void updateScoreboardTotal(ScoreboardPlayerScore score, CallbackInfo ci) {
        if (ETAdditionsSettings.scoreboardTotals)
            this.server.getPlayerManager().sendToAll(this.scoreboardTotalPacket(score.getObjective()));
    }

    @Inject(method = "createChangePackets", at = @At("TAIL"))
    public void initializeTotals(ScoreboardObjective objective, CallbackInfoReturnable<List<Packet<?>>> cir) {
        if (ETAdditionsSettings.scoreboardTotals) {
            cir.getReturnValue().add(this.scoreboardTotalPacket(objective));
            this.server.getPlayerManager().sendToAll(this.scoreboardTotalPacket(objective));
        }
    }

    public ScoreboardPlayerUpdateS2CPacket scoreboardTotalPacket(ScoreboardObjective objective) {
        int totalScore = 0;
        Iterator<ScoreboardPlayerScore> scores = this.getAllPlayerScores(objective).iterator();

        while(scores.hasNext()) {
            totalScore += scores.next().getScore();
        }

        return new ScoreboardPlayerUpdateS2CPacket(ServerScoreboard.UpdateMode.CHANGE, objective.getName(), "Total", totalScore);
    }
}
