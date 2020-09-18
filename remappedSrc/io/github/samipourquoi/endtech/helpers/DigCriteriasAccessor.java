package io.github.samipourquoi.endtech.helpers;

import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;

public class DigCriteriasAccessor {
    private static StatType<Identifier> DIG;
    private static Identifier ALL;
    private static Identifier PICKS;
    private static Identifier SHOVELS;
    private static Identifier AXES;

    /*==============================*/

    public static void setDig(StatType<Identifier> newDig) {
        DIG = newDig;
    }

    public static StatType<Identifier> getDig() {
        return DIG;
    }

    /*==============================*/

    public static void setAll(Identifier newAll) {
        ALL = newAll;
    }

    public static Identifier getAll() {
        return ALL;
    }

    public static void setPicks(Identifier newPicks) {
        PICKS = newPicks;
    }

    public static Identifier getPicks() {
        return PICKS;
    }

    public static void setShovels(Identifier newShovels) {
        SHOVELS = newShovels;
    }

    public static Identifier getShovels() {
        return SHOVELS;
    }

    public static void setAxes(Identifier newAxes) {
        AXES = newAxes;
    }

    public static Identifier getAxes() {
        return AXES;
    }
}
