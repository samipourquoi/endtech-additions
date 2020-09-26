package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.helpers.CustomPayloadC2SPacketHelper;
import io.github.samipourquoi.endtech.packets.ResponseObjectivesS2CPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {
    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onCustomPayload", at = @At("HEAD"))
    private void onPackets(CustomPayloadC2SPacket packet, CallbackInfo ci) {
        NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler)(Object) this, this.player.getServerWorld());
        Identifier id = ((CustomPayloadC2SPacketHelper) packet).getChannel();
        PacketByteBuf data = ((CustomPayloadC2SPacketHelper) packet).getData();

        if (id.toString().equals("endtech:query_objectives")) {
            PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());

            // Writes to a buffer the list of all objectives
            Collection<String> objectives = this.player.getServerWorld().getScoreboard().getObjectiveNames();
            objectives.forEach(buffer::writeString);

            // Sends that buffer to the client
            this.player.networkHandler.connection.send(ResponseObjectivesS2CPacket.create(buffer));
        }
    }
}
