package com.jzells.voyagercore.common.data.recipe.recipes;

import com.gregtechceu.gtceu.common.data.GTItems;

import net.minecraft.data.recipes.FinishedRecipe;

import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;

import java.util.function.Consumer;

public class PulverizerRecipes {

    public static final void init(Consumer<FinishedRecipe> provider) {
        pulverizerRecipes(provider);
    }

    public static void pulverizerRecipes(Consumer<FinishedRecipe> provider) {
        VoyagerRecipeTypes.PULVERIZING.recipeBuilder("test")
                .inputItems(GTItems.NAQUADAH_BOULE)
                .outputItems(GTItems.WETWARE_PROCESSOR_ASSEMBLY_ZPM)
                .EUt(480)
                .duration(8 * 20)
                .addData("crushing_wheel_tier", 2)
                .save(provider);
    }
}
