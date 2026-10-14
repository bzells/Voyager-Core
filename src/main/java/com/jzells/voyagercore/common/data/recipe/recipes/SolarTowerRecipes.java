package com.jzells.voyagercore.common.data.recipe.recipes;

import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.fluids.FluidStack;

import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;

import java.util.function.Consumer;

public class SolarTowerRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        solarTowerRecipes(provider);
    }

    private static void solarTowerRecipes(Consumer<FinishedRecipe> provider) {
        solarRecipe(provider, "water_to_steam",
                GTMaterials.Water.getFluid(20),
                GTMaterials.Steam.getFluid(160 * 20),
                20,
                10);

        solarRecipe(provider, "liquid_salt_to_molten_salt",
                GTMaterials.Salt.getFluid(FluidStorageKeys.LIQUID,20),
                GTMaterials.Salt.getFluid(FluidStorageKeys.MOLTEN,20),
                20,
                20);
    }

    private static void solarRecipe(Consumer<FinishedRecipe> provider, String recipeName, FluidStack fluidInput,
                                    FluidStack fluidOutput, int duration, int reflectorCount) {
        VoyagerRecipeTypes.SOLAR_TOWER
                .recipeBuilder(recipeName)
                .inputFluids(fluidInput)
                .outputFluids(fluidOutput)
                .duration(duration)
                .addData("reflector", reflectorCount)
                .save(provider);
    }
}
