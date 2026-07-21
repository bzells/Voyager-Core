package com.jzells.voyagercore.common.data.recipe.recipes;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.common.data.VoyagerMaterials;

import java.util.function.Consumer;

public class PulverizerRecipes {

    public static final void init(Consumer<FinishedRecipe> provider) {
        pulverizerRecipes(provider);
    }

    private static ItemStack desh_ore = ChemicalHelper.get(TagPrefix.crushed, VoyagerMaterials.Desh);

    public static void pulverizerRecipes(Consumer<FinishedRecipe> provider) {
        // VoyagerRecipeTypes.PULVERIZING.recipeBuilder("small_moon_rock_to_ores")
        // .inputItems(VoyagerKJSIntegration.getItemFromKubeJSRegistry("small_moon_rock"))
        // .outputItems(desh_ore)
        // .EUt(480)
        // .duration(8 * 20)
        // .addData("crushing_wheel_tier", GTValues.LuV)
        // .save(provider);
    }
}
