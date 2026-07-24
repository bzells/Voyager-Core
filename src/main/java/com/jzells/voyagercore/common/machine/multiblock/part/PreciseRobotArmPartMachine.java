package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import lombok.Getter;

@Getter
public class PreciseRobotArmPartMachine extends MultiblockPartMachine{

    private final int tier;

    public PreciseRobotArmPartMachine(IMachineBlockEntity holder, int tier) {
        super(holder);
        this.tier = tier;
    }


}
