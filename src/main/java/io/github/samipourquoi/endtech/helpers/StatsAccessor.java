package io.github.samipourquoi.endtech.helpers;

import io.github.samipourquoi.endtech.mixin.MixinStats;
import net.minecraft.util.Identifier;

import java.util.HashMap;

/**
 * We can't access a static field/method added via a Mixin.
 * However we do need to access to {@link MixinStats} DIG static
 * field (for instance). <p>
 * To solve that issue, we use that class to make a ‘bridge',
 * allowing us to get the needed static fields.
 *
 * @see MixinStats
 * @author samipourquoi
 */
public class StatsAccessor {
    public static Identifier DIG;
    public static Identifier PICKS;
    public static Identifier SHOVELS;
    public static Identifier AXES;
    public static Identifier HOES;

    public static HashMap<String, Identifier> CUSTOM_TAGS;
}
