package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FusionReactorMachine;

import net.minecraft.network.chat.Component;

import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import com.jzells.voyagercore.common.machine.multiblock.electric.HelperMultiMachine;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
import org.jetbrains.annotations.NotNull;

public class VoyagerCoreRecipeModifiers {

    // private static final FluidStack MILK_STACK = GTMaterials.Milk.getFluid(3);

    public static RecipeModifier CUBE_BOOSTING = VoyagerCoreRecipeModifiers::cubeModifier;
    public static RecipeModifier HEAT_BOOSTING = VoyagerCoreRecipeModifiers::heatBoostingModifier;
    public static RecipeModifier BASIC_BOOSTING = VoyagerCoreRecipeModifiers::basicBoostingModifier;
    public static RecipeModifier ADVANCED_BOOSTING = VoyagerCoreRecipeModifiers::advancedBoostingModifier;
    public static RecipeModifier ADVANCED_BOOSTING_FUSION = VoyagerCoreRecipeModifiers::advancedBoostingModifierFusion;
    public static RecipeModifier HELPER_BOOSTING = VoyagerCoreRecipeModifiers::helperBoosting;

    public static ModifierFunction cubeModifier(MetaMachine machine, GTRecipe recipe) {
        if (!(machine instanceof MetaMachine)) {
            return ModifierFunction.NULL;
        }
        if (!(recipe instanceof GTRecipe)) {
            return ModifierFunction.NULL;
        }
        int parallelMod = 2;
        double durationMod = 0.75;
        double eutMod = 0.75;

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

        int recipetemp = 0;

        if (!recipe.data.contains("ebf_temp")) {
            long voltage = recipe.getInputEUt().voltage();

            for (long i = 32; i < voltage; i *= 4) {
                recipetemp += 1000;
            }
        } else {
            recipetemp = recipe.data.getInt("ebf_temp");
        }

        int coilTemp = ((CoilWorkableElectricMultiblockMachine) machine).getCoilType().getCoilTemperature();

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
            int pars = Math.max(0, ParallelLogic.getParallelAmount(machine, recipe, parallels));
            return ModifierFunction.builder()
                    .modifyAllContents(ContentModifier.multiplier(pars))
                    .durationMultiplier(durationMod)
                    .eutMultiplier(eutMod)
                    .parallels(pars)
                    .build();
        }
    }

    public static ModifierFunction basicBoostingModifier(MetaMachine machine, GTRecipe recipe) {
        if (!(machine instanceof MetaMachine)) {
            return ModifierFunction.NULL;
        }
        if (!(recipe instanceof GTRecipe)) {
            return ModifierFunction.NULL;
        }

        long machineVoltage = ((WorkableElectricMultiblockMachine) machine).getMaxVoltage();
        long recipeVoltage = recipe.getInputEUt().voltage();

        double eutMod = .85;
        int parallels = 1;
        double durationMod = 1;

        for (long recV = recipeVoltage; recV < machineVoltage; recV *= 4) {
            parallels += 2;
            durationMod *= .9;
            // eutMod *= 4;
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
            int pars = Math.max(0, ParallelLogic.getParallelAmount(machine, recipe, parallels));
            return ModifierFunction.builder()
                    .modifyAllContents(ContentModifier.multiplier(pars))
                    .durationMultiplier(durationMod)
                    .eutMultiplier(eutMod)
                    .parallels(pars)
                    .build();
        }
    }

    public static ModifierFunction advancedBoostingModifier(MetaMachine machine, GTRecipe recipe) {
        if (!(machine instanceof MetaMachine)) {
            return ModifierFunction.NULL;
        }
        if (!(recipe instanceof GTRecipe)) {
            return ModifierFunction.NULL;
        }

        long machineVoltage = ((WorkableElectricMultiblockMachine) machine).getOverclockVoltage();
        long recipeVoltage = recipe.getInputEUt().voltage();

        int recipeTier = VoyagerVoltageTierUtils.getExactVoltageTier(recipeVoltage);
        int machineTier = VoyagerVoltageTierUtils.getExactVoltageTier(machineVoltage);

        double eutMod = .80;
        int parallels = 4;
        double durationMod = 1;

        double eutMult = 4;

        for (int i = recipeTier; i < machineTier; i++) {
            parallels *= 2;
            durationMod *= .9;
            // eutMod *= eutMult;
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
            int pars = Math.max(0, ParallelLogic.getParallelAmount(machine, recipe, parallels));
            return ModifierFunction.builder()
                    .modifyAllContents(ContentModifier.multiplier(pars))
                    .durationMultiplier(durationMod)
                    .eutMultiplier(eutMod)
                    .parallels(pars)
                    .build();
        }
    }

    public static ModifierFunction advancedBoostingModifierFusion(MetaMachine machine, GTRecipe recipe) {
        if (!(machine instanceof MetaMachine)) {
            return ModifierFunction.NULL;
        }
        if (!(recipe instanceof GTRecipe)) {
            return ModifierFunction.NULL;
        }

        if (machine instanceof FusionReactorMachine fusionReactorMachine) {

            long machineVoltage = ((WorkableElectricMultiblockMachine) machine).getOverclockVoltage();
            long recipeVoltage = recipe.getInputEUt().voltage();

            int recipeTier = VoyagerVoltageTierUtils.getExactVoltageTier(recipeVoltage);
            int machineTier = VoyagerVoltageTierUtils.getExactVoltageTier(machineVoltage);

            // System.out.println("Recipe volt: " + recipeVoltage);

            double eutMod = .80;
            int parallels = 4;
            double durationMod = 1;

            double eutMult = 2;

            // System.out.println(fusionReactorMachine.getTier());

            if (recipeTier > fusionReactorMachine.getTier())
                return ModifierFunction.cancel(Component.literal("Fusion tier too low"));

            durationMod = Math.pow(0.5, fusionReactorMachine.getTier() - recipeTier);

            for (int i = recipeTier; i < machineTier; i++) {
                parallels *= 2;
                durationMod *= .9;
                eutMod *= eutMult;
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
                int pars = Math.max(0, ParallelLogic.getParallelAmount(machine, recipe, parallels));
                return ModifierFunction.builder()
                        .modifyAllContents(ContentModifier.multiplier(pars))
                        .durationMultiplier(durationMod)
                        .eutMultiplier(eutMod)
                        .parallels(pars)
                        .build();
            }
        } else {
            return ModifierFunction.cancel(Component.literal("This isn't a fusion reactor!"));
        }
    }

    public static ModifierFunction helperBoosting(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof HelperMultiMachine helperMachine)) {
            return RecipeModifier.nullWrongType(HelperMultiMachine.class, machine);
        }
        var data = helperMachine.helperHolder.getHelperData();
        if (data == HelperItemComponent.NULL_HELPER) {
            return ModifierFunction.cancel(Component.literal("Helper is Missing, or Something"));
        }
        var recipeVoltageTier = VoyagerVoltageTierUtils.getExactVoltageTier(recipe.getInputEUt().voltage());
        if (data.getRecipeType() != recipe.recipeType) {
            return ModifierFunction.cancel(Component.literal("Wrong Recipe Type!"));
        }
        if (data.getTier() < recipeVoltageTier) {
            return ModifierFunction.cancel(Component.literal("Helper Tier too low!"));
        }
        var tierBoost = data.getTier() - recipeVoltageTier;
        return ModifierFunction.builder()
                .eutMultiplier(Math.max(1 - 0.05 * (tierBoost), 0.25))
                .durationModifier(ContentModifier.multiplier(Math.max(1 - 0.025 * (tierBoost), 0.75)))
                .inputModifier(ContentModifier.multiplier(Math.max(1 - 0.025 * (tierBoost), 0.75)))
                .outputModifier(ContentModifier.multiplier(Math.min(1 + 0.025 * (tierBoost), 2.0)))
                .build();
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
