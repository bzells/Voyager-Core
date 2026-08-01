package com.jzells.voyagercore.common.machine.multiblock;

import com.jzells.voyagercore.common.machine.multiblock.electric.ElectricMultiMachines;
import com.jzells.voyagercore.common.machine.multiblock.electric.HelperElectricMultiMachines;
import com.jzells.voyagercore.common.machine.multiblock.generator.calorieconverters.CalorieConverterMultis;
import com.jzells.voyagercore.common.machine.multiblock.generator.GeneratorMultis;

public class VoyagerMultiRegistry {

    public static void init() {
        GeneratorMultis.init();
        ElectricMultiMachines.init();
        HelperElectricMultiMachines.init();
    }
}
