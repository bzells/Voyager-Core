package com.jzells.voyagercore.common.data.recipe.recipes;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.data.recipes.FinishedRecipe;

import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;

import java.util.function.Consumer;

public class ChemicalPlantRecipes {

    public static final void init(Consumer<FinishedRecipe> provider) {
        chemPlantRecipes(provider);
    }

    public static void chemPlantRecipes(Consumer<FinishedRecipe> provider) {
        VoyagerRecipeTypes.CHEMICAL_PLANT.recipeBuilder("nitrobenzene")
                .inputFluids(GTMaterials.Benzene.getFluid(5000), GTMaterials.NitrationMixture.getFluid(2000),
                        GTMaterials.DistilledWater.getFluid(2000))
                .outputFluids(GTMaterials.Nitrobenzene.getFluid(8000), GTMaterials.DilutedSulfuricAcid.getFluid(1000))
                .EUt(480)
                .duration(8 * 20)
                .blastFurnaceTemp(3600)
                .save(provider);
    }
}
