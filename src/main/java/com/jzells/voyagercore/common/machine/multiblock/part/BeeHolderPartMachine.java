package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;

public class BeeHolderPartMachine extends MultiblockPartMachine implements IControllable, IMachineLife {

    protected final IO io;
    protected boolean workingEnabled;

    public BeeHolderPartMachine(IMachineBlockEntity holder, IO io) {
        super(holder);
        this.io = io;
        this.workingEnabled = true;
    }

    @Override
    public void setWorkingEnabled(boolean workingEnabled) {
        this.workingEnabled = workingEnabled;
        // syncDataHolder.markClientSyncFieldDirty("workingEnabled");
    }

    public boolean isWorkingEnabled() {
        return false;
    }
}
