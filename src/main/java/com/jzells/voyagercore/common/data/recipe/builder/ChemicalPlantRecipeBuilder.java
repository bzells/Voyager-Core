package com.jzells.voyagercore.common.data.recipe.builder;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraft.resources.ResourceLocation;

public class ChemicalPlantRecipeBuilder extends GTRecipeBuilder {

    public ChemicalPlantRecipeBuilder(GTRecipe toCopy, GTRecipeType recipeType) {
        super(toCopy, recipeType);
    }

    public ChemicalPlantRecipeBuilder(ResourceLocation id, GTRecipeType recipeType) {
        super(id, recipeType);
    }

    public ChemicalPlantRecipeBuilder temperature(int temp) {
        this.data.putInt("ebf_temp", temp);
        return this;
    }
}
