package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;

public class CrushingWheelPartMachine extends MultiblockPartMachine {

    private final int crushingTier;

    public CrushingWheelPartMachine(IMachineBlockEntity holder, int crushingTier) {
        super(holder);
        this.crushingTier = crushingTier;
    }

    public int getCrushingTier() {
        return crushingTier;
    }
}
