package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTBlocks;

import static com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities.PIPE_CASING;

public class VoyagerPartAbilityRegistry {

    public static void register() {
        PIPE_CASING.register(GTValues.ULV, GTBlocks.CASING_BRONZE_PIPE.get());
        PIPE_CASING.register(GTValues.MV, VoyagerBlocks.ALUMINIUM_PIPE_CASING.get());
        PIPE_CASING.register(GTValues.HV, GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE.get());
        PIPE_CASING.register(GTValues.EV, GTBlocks.CASING_TITANIUM_PIPE.get());
        PIPE_CASING.register(GTValues.IV, GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get());
        PIPE_CASING.register(GTValues.LV, GTBlocks.CASING_STEEL_PIPE.get());
    }
}
