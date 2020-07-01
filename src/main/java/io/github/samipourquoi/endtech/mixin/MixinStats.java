package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.helpers.DigCriteriasAccessor;
import io.github.samipourquoi.endtech.helpers.DigStatRegistryAccessor;
import io.github.samipourquoi.endtech.helpers.StatsRegisterHelper;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Stats.class)
public class MixinStats {
    @Mutable @Final private static StatType<Identifier> DIG;
    /*====================================================*/
    @Mutable @Final private static Identifier ALL;
    @Mutable @Final private static Identifier PICKS;
    @Mutable @Final private static Identifier SHOVELS;
    @Mutable @Final private static Identifier AXES;

    @Shadow private static <T> StatType<T> registerType(String string, Registry<Identifier> registry) {
        return null;
    }

    @Shadow private static Identifier register(String string, StatFormatter statFormatter) {
        return null;
    };

    static {
        DIG = registerType("endtech:dig", DigStatRegistryAccessor.getDigStat());
        /*==================================================*/
        ALL = StatsRegisterHelper.register("endtech:all", StatFormatter.DEFAULT, DIG);
        PICKS = StatsRegisterHelper.register("endtech:picks", StatFormatter.DEFAULT, DIG);
        SHOVELS = StatsRegisterHelper.register("endtech:shovels", StatFormatter.DEFAULT, DIG);
        AXES = StatsRegisterHelper.register("endtech:axes", StatFormatter.DEFAULT, DIG);
        /*===================================================================================*/
        DigCriteriasAccessor.setDig(DIG);
        DigCriteriasAccessor.setAll(ALL);
        DigCriteriasAccessor.setPicks(PICKS);
        DigCriteriasAccessor.setShovels(SHOVELS);
        DigCriteriasAccessor.setAxes(AXES);
    }
}
