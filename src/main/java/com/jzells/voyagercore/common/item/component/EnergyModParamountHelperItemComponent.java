package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.util.VoyagerConstants;
import lombok.Getter;

@Getter
public class EnergyModParamountHelperItemComponent extends ParamountHelperItemComponent {

    private final float BASE_EUT_MOD;
    private final float BASE_EAT_TIME_MOD;

    public EnergyModParamountHelperItemComponent(GTRecipeType recipeType, int moduleCount, int tier, boolean isHull,
                                                 String paramount_data, int baseLevel, float xpScale,
                                                 float baseEUtMod, float baseEatTimeMod) {
        super(recipeType, moduleCount, tier, isHull, paramount_data, baseLevel, xpScale);
        this.BASE_EAT_TIME_MOD = baseEatTimeMod;
        this.BASE_EUT_MOD = baseEUtMod;
    }

    @Override
    protected void initializeData(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTagElement(DATA_TAG);

        tag.putInt("level", baseLevel);
        tag.putLong("current_xp", 0);
        tag.putLong("level_up_xp", (long) (512 * this.LevelXPScaleFactor));
        tag.putInt("max_module_count", MAX_MODULE_COUNT);

        tag.putFloat("eut_gen_mod", BASE_EUT_MOD);
        tag.putFloat("eat_time_mod", BASE_EAT_TIME_MOD);
    }

    public float getEUtGenMod() {
        return getData().getFloat("eut_gen_mod");
    }

    public float getEatTimeMod() {
        return getData().getFloat("eat_time_mod");
    }

    @Override
    public void levelHelper(float recipeTime, int recipeTier, int pars) {
        CompoundTag tag = getData();

        long currentXP = tag.getLong("current_xp");
        long levelUpXP = tag.getLong("level_up_xp");
        int level = tag.getInt("level");
        int maxModuleCount = tag.getInt("max_module_count");
        currentXP += VoyagerConstants.PARAMOUNT_XP_FORMULA(recipeTime, recipeTier, pars);

        while (currentXP >= levelUpXP) {
            currentXP -= levelUpXP;
            level++;

            levelUpXP = (long) (levelUpXP * VoyagerConstants.PARAMOUNT_HELPER_LEVEL_UP_XP_MULTIPLIER(level));

            maxModuleCount++;
            float oldEUtMod = getEUtGenMod();
            float oldEatMod = getEatTimeMod();

            float newEUtMod = oldEUtMod * VoyagerConstants.HUNGRY_HELPER_EUT_LEVEL_UP_MULT;
            float newEatMod = oldEatMod * VoyagerConstants.HUNGRY_HELPER_EAT_LEVEL_UP_MULT;

            tag.putFloat("eut_gen_mod", newEUtMod);
            tag.putFloat("eat_time_mod", newEatMod);
        }

        tag.putInt("level", level);
        tag.putLong("current_xp", currentXP);
        tag.putLong("level_up_xp", levelUpXP);
        tag.putInt("max_module_count", maxModuleCount);
    }
}
