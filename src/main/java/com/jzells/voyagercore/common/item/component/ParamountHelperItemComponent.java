package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;

@Getter
public class ParamountHelperItemComponent extends HelperItemComponent {

    private static final String DATA_TAG = "paramount_helper_data";

    private final String PARAMOUNT_DATA;
    private final int baseLevel;
    private final float LevelXPScaleFactor;

    /*
     * This is the ItemStack that owns this component's persistent nbt data.
     */
    private ItemStack owner;

    public ParamountHelperItemComponent(
                                        GTRecipeType recipeType,
                                        int moduleCount,
                                        int tier,
                                        boolean isHull,
                                        String paramount_data,
                                        int baseLevel,
                                        float xpScale) {
        super(recipeType, paramount_data, moduleCount, tier, isHull, 0, false);

        PARAMOUNT_DATA = paramount_data;
        this.baseLevel = baseLevel;
        this.LevelXPScaleFactor = xpScale;
    }

    /**
     * Assign the ItemStack that owns this component.
     * Call this once when the component is attached to/created for the stack.
     */
    public void setOwner(ItemStack stack) {
        this.owner = stack;

        if (!stack.hasTag() || !stack.getTag().contains(DATA_TAG)) {
            initializeData(stack);
        }
    }

    private CompoundTag getData() {
        if (owner == null) {
            throw new IllegalStateException(
                    "ParamountHelperItemComponent has no ItemStack owner");
        }

        return owner.getOrCreateTagElement(DATA_TAG);
    }

    private void initializeData(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTagElement(DATA_TAG);

        tag.putInt("level", baseLevel);
        tag.putLong("current_xp", 0);
        tag.putLong("level_up_xp", 512);
        tag.putInt("max_module_count", MAX_MODULE_COUNT);
    }

    public int getLevel() {
        return getData().getInt("level");
    }

    public long getCurrentXP() {
        return getData().getLong("current_xp");
    }

    public long getLevelUpXP() {
        return getData().getLong("level_up_xp");
    }

    @Override
    public int getMAX_MODULE_COUNT() {
        return getData().getInt("max_module_count");
    }

    public void levelHelper(float recipeTime, int recipeTier) {
        CompoundTag tag = getData();

        long currentXP = tag.getLong("current_xp");
        long levelUpXP = tag.getLong("level_up_xp");
        int level = tag.getInt("level");
        int maxModuleCount = tag.getInt("max_module_count");

        currentXP += (long) ((Math.pow(recipeTime, 1.5) * Math.pow(recipeTier, 1.2)) / 3200) + recipeTier;

        while (currentXP >= levelUpXP) {
            currentXP -= levelUpXP;
            level++;

            levelUpXP = (long) (levelUpXP * LevelXPScaleFactor * 4);

            maxModuleCount++;
        }

        tag.putInt("level", level);
        tag.putLong("current_xp", currentXP);
        tag.putLong("level_up_xp", levelUpXP);
        tag.putInt("max_module_count", maxModuleCount);
    }
}
