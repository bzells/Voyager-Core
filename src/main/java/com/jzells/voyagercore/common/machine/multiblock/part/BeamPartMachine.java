package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;

public class BeamPartMachine extends MultiblockPartMachine {

    private final float beamConcentration;

    public BeamPartMachine(IMachineBlockEntity holder, float beamTier) {
        super(holder);
        this.beamConcentration = beamTier;
    }

    public float getbeamConcentration() {
        return beamConcentration;
    }
}
