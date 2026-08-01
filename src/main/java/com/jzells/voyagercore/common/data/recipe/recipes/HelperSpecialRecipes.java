package com.jzells.voyagercore.common.data.recipe.recipes;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class HelperSpecialRecipes {

    public static final void init(Consumer<FinishedRecipe> provider) {
        helperSpecialRecipes(provider);
    }

    public static void helperSpecialRecipes(Consumer<FinishedRecipe> provider) {}
}
