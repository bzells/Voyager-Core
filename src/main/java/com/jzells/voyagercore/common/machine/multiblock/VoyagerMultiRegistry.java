package com.jzells.voyagercore.common.machine.multiblock;

import com.jzells.voyagercore.common.machine.multiblock.electric.ElectricMultiMachines;
import com.jzells.voyagercore.common.machine.multiblock.generator.calorieconverters.CalorieConverterMultis;

public class VoyagerMultiRegistry {

    public static void init() {
        CalorieConverterMultis.init();
        ElectricMultiMachines.init();
    }
}
