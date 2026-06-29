package com.jzells.voyagercore.common.data.recipe.recipes;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class VoyagerRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        ChemicalPlantRecipes.init(provider);
    }
}
