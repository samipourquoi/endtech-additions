package io.github.samipourquoi.endtech.helpers;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface CustomPayloadC2SPacketHelper {
    Identifier getChannel();
    PacketByteBuf getData();
}
