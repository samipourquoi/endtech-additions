package io.github.samipourquoi.endtech.helpers;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StatsRegisterHelper {

    public static Identifier register(String id, StatFormatter statFormatter, final StatType<Identifier> statType) {
        Identifier identifier = new Identifier(id);
        Registry.register(DigStatRegistryAccessor.getDigStatRegistry(), (String) id, identifier);
        statType.getOrCreateStat(identifier, statFormatter);
        return identifier;
    }
}
