package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GCYMBlocks;

import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;

public class VoyagerPartAbilityRegistry {

    public static void register() {
        VoyagerPartAbilities.registerCrushingWheel(GTValues.IV, GCYMBlocks.CRUSHING_WHEELS.get());
        VoyagerPartAbilities.registerCrushingWheel(GTValues.LuV, VoyagerMachines.LUV_CRUSH_WHEEL.getBlock());
        VoyagerPartAbilities.registerCrushingWheel(GTValues.ZPM, VoyagerMachines.ZPM_CRUSH_WHEEL.getBlock());
        VoyagerPartAbilities.registerCrushingWheel(GTValues.UV, VoyagerMachines.UV_CRUSH_WHEEL.getBlock());

        VoyagerPartAbilities.registerBeamLens(1, VoyagerMachines.NETHER_STAR_BEAM.getBlock());
    }

    private VoyagerPartAbilityRegistry() {}
}
