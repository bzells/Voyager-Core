package com.jzells.voyagercore.util;

public class VoyagerConstants {

    public static float HUNGRY_HELPER_EUT_LEVEL_UP_MULT = 1.1f;
    public static float HUNGRY_HELPER_EAT_LEVEL_UP_MULT = 1.2f;

    public static float MIN_HELPER_SPEED = 0.01f;
    public static float MIN_HELPER_EUT = 0.01f;

    public static float PARAMOUNT_HELPER_LEVEL_UP_XP_MULTIPLIER(int level) {
        if (level < 12) {
            return 4f;
        }
        if (level < 14) {
            return 3f;
        }
        if (level < 16) {
            return 2f;
        }
        return 1.05f;
    }

    public static long PARAMOUNT_XP_FORMULA(float recipeTime, int recipeTier) {
        return (long) ((Math.pow(recipeTime, 1.8) * Math.pow(recipeTier, 1.2)) / 3200) + (recipeTier);
    }
}
