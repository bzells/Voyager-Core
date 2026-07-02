package com.jzells.voyagercore.common.data.recipe.builder;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraft.resources.ResourceLocation;

public class PulverizerRecipeBuilder extends GTRecipeBuilder {

    public PulverizerRecipeBuilder(GTRecipe toCopy, GTRecipeType recipeType) {
        super(toCopy, recipeType);
    }

    public PulverizerRecipeBuilder(ResourceLocation id, GTRecipeType recipeType) {
        super(id, recipeType);
    }

    public PulverizerRecipeBuilder tier(int tier) {
        this.data.putInt("crushing_wheel_tier", tier);
        return this;
    }
}
