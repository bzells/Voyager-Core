package com.jzells.voyagercore.common.machine.multiblock;

import com.jzells.voyagercore.common.machine.multiblock.electric.ElectricMultiMachines;
import com.jzells.voyagercore.common.machine.multiblock.electric.HelperElectricMultiMachines;
import com.jzells.voyagercore.common.machine.multiblock.generator.GeneratorMultis;
import com.jzells.voyagercore.common.machine.multiblock.steam.SteamMultis;

public class VoyagerMultiRegistry {

    public static void init() {
        GeneratorMultis.init();
        ElectricMultiMachines.init();
        HelperElectricMultiMachines.init();
        SteamMultis.init();
    }
}
