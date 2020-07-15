package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.helpers.DigCriteriasAccessor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.network.packet.s2c.play.StatisticsS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.Set;

@Mixin(ServerStatHandler.class)
public abstract class MixinServerStatHandler extends StatHandler {
    @Shadow @Final private MinecraftServer server;

    @Shadow private int lastStatsUpdate;

    @Shadow protected abstract Set<Stat<?>> takePendingStats();

    /**
     * @author samipourquoi
     * @reason Prevent the server from sending packets of the new scoreboards, causing a client-crash.
     */
    @Overwrite
    public void sendStats(ServerPlayerEntity player) {
        int i = this.server.getTicks();
        Object2IntMap<Stat<?>> object2IntMap = new Object2IntOpenHashMap();
        if (i - this.lastStatsUpdate > 300) {
            this.lastStatsUpdate = i;
            Iterator var4 = this.takePendingStats().iterator();

            while(var4.hasNext()) {
                Stat<?> stat = (Stat)var4.next();
                if (!stat.getType().equals(DigCriteriasAccessor.getDig()))
                    object2IntMap.put(stat, this.getStat(stat));
            }
        }

        player.networkHandler.sendPacket(new StatisticsS2CPacket(object2IntMap));
    }
}
