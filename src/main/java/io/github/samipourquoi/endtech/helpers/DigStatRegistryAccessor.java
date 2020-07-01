package io.github.samipourquoi.endtech.helpers;

import com.mojang.serialization.Lifecycle;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

public class DigStatRegistryAccessor {
    private static Registry<Identifier> DIG_STAT = new SimpleRegistry<Identifier>(
        RegistryKey.ofRegistry(new Identifier("endtech","dig")),
        Lifecycle.stable()
    );

    public static Registry<Identifier> getDigStat() {
        return DIG_STAT;
    }
}
