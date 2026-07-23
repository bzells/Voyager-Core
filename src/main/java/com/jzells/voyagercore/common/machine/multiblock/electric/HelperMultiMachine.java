package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;

import com.jzells.voyagercore.common.machine.multiblock.part.HelperHolderPartMachine;

public class HelperMultiMachine extends WorkableElectricMultiblockMachine {

    public HelperHolderPartMachine helperHolder;

    public HelperMultiMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        initPartsList();
    }

    // Only one helper holder

    public void initPartsList() {
        for (IMultiPart part : getParts()) {
            if (part instanceof HelperHolderPartMachine h) {
                this.helperHolder = h;
                return;
            }
        }
    }
}
