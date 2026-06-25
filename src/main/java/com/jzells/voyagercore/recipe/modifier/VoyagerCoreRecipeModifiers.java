package com.jzells.voyagercore.recipe.modifier;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import static com.jzells.voyagercore.VoyagerCore.LOGGER;

public class VoyagerCoreRecipeModifiers {

    // private static final FluidStack MILK_STACK = GTMaterials.Milk.getFluid(3);

    public static RecipeModifier CUBE_BOOSTING = VoyagerCoreRecipeModifiers::cubeModifier;
    public static RecipeModifier HEAT_BOOSTING = VoyagerCoreRecipeModifiers::heatBoostingModifier;

    public static ModifierFunction cubeModifier(MetaMachine machine, GTRecipe recipe) {
        if (!(machine instanceof MetaMachine)) {
            return ModifierFunction.NULL;
        }
        if (!(recipe instanceof GTRecipe)) {
            return ModifierFunction.NULL;
        }
        int parallelMod = 3;
        double durationMod = 0.6;
        double eutMod = 0.6;

        return ModifierFunction.builder()
                .modifyAllContents(ContentModifier.multiplier(parallelMod))
                .durationMultiplier(durationMod)
                .eutMultiplier(eutMod)
                .parallels(parallelMod)
                .build();
    }

    public static ModifierFunction heatBoostingModifier(MetaMachine machine, GTRecipe recipe) {
        if (!(machine instanceof MetaMachine)) {
            return ModifierFunction.NULL;
        }
        if (!(recipe instanceof GTRecipe)) {
            return ModifierFunction.NULL;
        }
        if (!(machine instanceof CoilWorkableElectricMultiblockMachine)) {
            return ModifierFunction.NULL;
        }

        int coilTemp = ((CoilWorkableElectricMultiblockMachine) machine).getCoilType().getCoilTemperature();
        int recipetemp = recipe.data.getInt("ebf_temp");

        double eutMod = .85;
        int parallels = 1;
        double durationMod = 1;

        if (coilTemp - 500 >= recipetemp) {
            parallels = (coilTemp - recipetemp) / 500;

        }

        if (coilTemp - 1000 >= recipetemp) {
            durationMod = Math.pow(
                    0.9,
                    (coilTemp - recipetemp) / 1000.0);

        }

        int parallelAvailable = Math.max(0, ParallelLogic.getParallelAmountWithoutEU(machine, recipe, parallels));

        if (parallelAvailable >= parallels) {
            return ModifierFunction.builder()
                    .modifyAllContents(ContentModifier.multiplier(parallels))
                    .durationMultiplier(durationMod)
                    .eutMultiplier(eutMod)
                    .parallels(parallels)
                    .build();
        } else {
            int pars = Math.max(0, ParallelLogic.getParallelAmountWithoutEU(machine, recipe, parallels));
            return ModifierFunction.builder()
                    .modifyAllContents(ContentModifier.multiplier(pars))
                    .durationMultiplier(durationMod)
                    .eutMultiplier(eutMod)
                    .parallels(pars)
                    .build();
        }
    }

    // protected GTRecipe getMilkRecipe()
    // {
    // return GTRecipeBuilder.ofRaw().inputFluids(MILK_STACK).buildRawRecipe();
    // }
    //
    // public ModifierFunction advancedHelperCalorieConversionModifier(MetaMachine machine, GTRecipe recipe)
    // {
    // if(machine instanceof SimpleGeneratorMachine generatorMachine)
    // {
    // EnergyStack EUt = recipe.getOutputEUt();
    // if(!EUt.isEmpty() && RecipeHelper.matchRecipe(generatorMachine, this.getMilkRecipe()).isSuccess())
    // {
    //
    // }
    // }
    // }
}
