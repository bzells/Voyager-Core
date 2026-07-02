package com.jzells.voyagercore.util;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.utils.GTUtil;

public class VoyagerVoltageTierUtils {

    public static int getExactVoltageTier(long voltage) {
        return GTUtil.getTierByVoltage(voltage);
    }

    public static int getOverclockCount(long recipeVolts, long machineVolts) {
        return getExactVoltageTier(machineVolts) - getExactVoltageTier(recipeVolts);
    }

    public static int getOverclockCount(GTRecipe recipe, WorkableElectricMultiblockMachine machine) {
        return getExactVoltageTier(machine.getMaxVoltage()) - getExactVoltageTier(recipe.getInputEUt().voltage());
    }
}
