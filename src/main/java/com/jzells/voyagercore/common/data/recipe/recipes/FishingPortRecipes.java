package com.jzells.voyagercore.common.data.recipe.recipes;

import com.gregtechceu.gtceu.api.GTValues;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import com.jzells.voyagercore.util.VoyagerKJSIntegration;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class FishingPortRecipes {
    public static final void init(Consumer<FinishedRecipe> provider) {
        fishingPortRecipes(provider);
    }

    public static void fishingPortRecipes(Consumer<FinishedRecipe> provider) {
        VoyagerRecipeTypes.FISH_NORMAL.recipeBuilder("fish_normal")
         .circuitMeta(1)
         .EUt(480)
         .duration(8 * 20)
         .addData("fish_table", "gameplay/fishing/fish")
         .save(provider);

        VoyagerRecipeTypes.FISH_NORMAL.recipeBuilder("fish_junk")
        .circuitMeta(2)
        .EUt(480)
        .duration(8 * 20)
        .addData("fish_table", "gameplay/fishing/junk")
        .save(provider);
    }
}
