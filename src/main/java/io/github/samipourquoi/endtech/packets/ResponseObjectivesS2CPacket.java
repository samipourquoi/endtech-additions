package io.github.samipourquoi.endtech.packets;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;

public class ResponseObjectivesS2CPacket {
    public final static Identifier ID = new Identifier("endtech", "response_objectives");

    public static CustomPayloadS2CPacket create(PacketByteBuf data) {
        return new CustomPayloadS2CPacket(ID, data);
    }
}
