package com.jzells.voyagercore.common.item.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;

@Getter
public class HelperRecipeModuleItemComponent extends HelperModuleItemComponent {

    public HelperRecipeModuleItemComponent(int gt_tier, String recipeType, int recipeCount, boolean specialized) {
        super(gt_tier, null, specialized, 0, null);
        this.recipeType = recipeType;
        RECIPE_COUNT = recipeCount;
    }

    // public HelperRecipeModuleItemComponent(int gt_tier, String recipeType, @Nullable String moduleData, int
    // recipeCount) {
    // super();
    // this.ModuleData = moduleData;
    // this.recipeType = recipeType;
    // this.GT_TIER = gt_tier;
    // this.RECIPE_COUNT = recipeCount;
    // }
    //
    @Getter
    private final String TAG_ELEMENT = "recipes";
    //
    // public final CompoundTag tag(ItemStack stack) {
    // return stack.getOrCreateTagElement(TAG_ELEMENT);
    // }
    //
    private final String recipeType;
    private final int RECIPE_COUNT;

    //
    @Override
    public void apply(ItemStack stack) {
        super.apply(stack);
        CompoundTag tag = stack.getOrCreateTagElement(TAG_ELEMENT);

        tag.putString(recipeType, recipeType);
    }

    //
    @Override
    public boolean canApply(ItemStack stack, HelperItemComponent helperItemComponent) {
        int helperTier = helperItemComponent.getTier();
        int currentModuleCount;

        currentModuleCount = stack.getOrCreateTagElement("recipes").getAllKeys().size();

        if (!this.isSpecialized() && helperItemComponent.isSpecialized()) {
            return false;
        }

        // System.out.println(this.getGT_TIER() <= helperTier && currentModuleCount <
        // helperItemComponent.getRecipeCount());
        // System.out.println(currentModuleCount);
        // System.out.println(helperItemComponent.getRecipeCount());

        return ((this.getGT_TIER() <= helperTier && currentModuleCount < helperItemComponent.getRecipeCount()));
    }
}
