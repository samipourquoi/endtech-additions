package io.github.samipourquoi.endtech.mixin;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.samipourquoi.endtech.endbot.ScoreboardEndbotCommand;
import io.github.samipourquoi.endtech.packets.QueryObjectivesC2SPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.game.MinecraftGameProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.argument.ObjectiveArgumentType;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.MinecraftServer;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
@Mixin(ObjectiveArgumentType.class)
public class MixinObjectiveArgumentType {
    @Inject(method = "listSuggestions", cancellable = true, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/command/CommandSource;getCompletions(Lcom/mojang/brigadier/context/CommandContext;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;"
    ))
    private void requestObjectivesToServer(CommandContext<CallbackI.S> context, SuggestionsBuilder builder, CallbackInfoReturnable<CompletableFuture<Suggestions>> cir) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (context.getInput().equals("!scoreboard ")) {
            if (!player.getServer().isDedicated()) {
                player.networkHandler.getConnection().send(QueryObjectivesC2SPacket.create());
                ScoreboardEndbotCommand.currentQuery = new CompletableFuture<>();
                ScoreboardEndbotCommand.currentInput = context.getInput();
                ScoreboardEndbotCommand.currentStringRange = StringRange.between(builder.getStart(), context.getRange().getEnd());
                cir.setReturnValue(ScoreboardEndbotCommand.currentQuery);
            } else {
                Collection<Suggestion> suggestions = new ArrayList<>();
                Collection<ScoreboardObjective> objectives = player.networkHandler.getWorld().getScoreboard().getObjectives();
                objectives.forEach(objective -> {
                    StringRange range = context.getRange();
                    suggestions.add(new Suggestion(StringRange.between(builder.getStart(), range.getEnd()), objective.getName()));
                });

                Suggestions finalSuggestions = Suggestions.create(context.getInput(), suggestions);
                cir.setReturnValue(CompletableFuture.completedFuture(finalSuggestions));
            }
        }
    }
}
