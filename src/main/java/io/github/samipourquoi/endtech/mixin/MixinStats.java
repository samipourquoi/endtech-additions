package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.helpers.StatsAccessor;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Registers the custom stats. Current list is:
 * <ul>
 *     <li><strong>minecraft.custom:minecraft.dig</strong> total of blocks mined, exclude ice</li>
 *     <li><strong>minecraft.custom:minecraft.picks</strong> total of blocks mined with a pickaxe</li>
 *     <li><strong>minecraft.custom:minecraft.shovels</strong> total of blocks mined with a shovel</li>
 *     <li><strong>minecraft.custom:minecraft.axes</strong> total of blocks mined with a axe</li>
 *     <li><strong>minecraft.custom:minecraft.hoes</strong> total of blocks mined with a hoe</li>
 * </ul>
 */
@Mixin(Stats.class)
public abstract class MixinStats {
    @Shadow
    private static Identifier register(String string, StatFormatter statFormatter) {
        return null;
    }

    private static final Identifier DIG;
    private static final Identifier PICKS;
    private static final Identifier SHOVELS;
    private static final Identifier AXES;
    private static final Identifier HOES;

    static {
        // Registers the custom stats
        DIG = register("dig", StatFormatter.DEFAULT);
        PICKS = register("picks", StatFormatter.DEFAULT);
        SHOVELS = register("shovels", StatFormatter.DEFAULT);
        AXES = register("axes", StatFormatter.DEFAULT);
        HOES = register("hoes", StatFormatter.DEFAULT);

        // Pass through the custom stats to a class, allowing
        // us to retrieve these fields later on.
        StatsAccessor.DIG = DIG;
        StatsAccessor.PICKS = PICKS;
        StatsAccessor.SHOVELS = SHOVELS;
        StatsAccessor.AXES = AXES;
        StatsAccessor.HOES = HOES;
    }
}
