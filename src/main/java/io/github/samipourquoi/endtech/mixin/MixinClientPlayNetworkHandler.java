package io.github.samipourquoi.endtech.mixin;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import io.github.samipourquoi.endtech.endbot.ScoreboardEndbotCommand;
import io.github.samipourquoi.endtech.helpers.CustomPayloadC2SPacketHelper;
import io.github.samipourquoi.endtech.packets.ResponseObjectivesS2CPacket;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Shadow private MinecraftClient client;

    @Inject(method = "onCustomPayload", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/packet/s2c/play/CustomPayloadS2CPacket;getChannel()Lnet/minecraft/util/Identifier;"
    ))
    private void onPacket(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        System.out.println("im here");

        Identifier id = packet.getChannel();
        PacketByteBuf data = packet.getData();

        if (id.equals(ResponseObjectivesS2CPacket.ID)) {
//            Collection<Suggestion> objectives = new ArrayList<>();
//            while (data.readableBytes() > 0) {
//                Suggestion suggestion = new Suggestion(ScoreboardEndbotCommand.currentStringRange, data.readString());
//                objectives.add(suggestion);
//            }
//            System.out.println(objectives);
//            Suggestions suggestions = Suggestions.create(ScoreboardEndbotCommand.currentInput, objectives);
//            ScoreboardEndbotCommand.currentQuery.complete(suggestions);
//            ScoreboardEndbotCommand.currentQuery.complete(new Suggestions(StringRange.at(0), new ArrayList<>()));
        }
    }
}
