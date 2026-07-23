package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;

public class HelperHolder extends MultiblockPartMachine {

    private final HelperHelper helperHelper;

    public HelperHolder(IMachineBlockEntity holder) {
        super(holder);
        helperHelper = new HelperHelper(this);
    }

    private class HelperHelper extends NotifiableItemStackHandler {

        public HelperHelper(MetaMachine machine) {
            super(machine, 1, IO.IN, IO.BOTH, size -> new CustomItemStackHandler(size) {

                @Override
                public int getSlotLimit(int slot) {
                    return 1;
                }
            });
        }
    }
}
