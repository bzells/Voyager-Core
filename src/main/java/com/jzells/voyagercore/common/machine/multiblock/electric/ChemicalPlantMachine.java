package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import org.jetbrains.annotations.NotNull;

public class ChemicalPlantMachine extends CoilWorkableElectricMultiblockMachine {

    public ChemicalPlantMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof ChemicalPlantMachine chemicalPlantMachine)) {
            return RecipeModifier.nullWrongType(FluidCoilMulti.class, machine);
        } else {
            int coilTempRecipe = recipe.data.getInt("ebf_temp");
            int machineCoilTemp = chemicalPlantMachine.getCoilType().getCoilTemperature();

            if (coilTempRecipe > machineCoilTemp) {
                return ModifierFunction.NULL;
            } else {
                return ModifierFunction.IDENTITY;
            }
        }
    }
}
