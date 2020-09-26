package io.github.samipourquoi.endtech.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;

public class QueryObjectivesC2SPacket {
    public static final Identifier ID = new Identifier("endtech", "query_objectives");

    public static CustomPayloadC2SPacket create() {
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        return new CustomPayloadC2SPacket(ID, data);
    }
}
