package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;

import com.jzells.voyagercore.common.data.machines.IModuleProvider;
import com.jzells.voyagercore.common.data.machines.IModuleReceiver;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

public class ModuleInstanceMachine extends WorkableElectricMultiblockMachine implements IModuleProvider {

    @Nullable
    @Getter
    @Setter
    private IModuleReceiver moduleMachine;

    public ModuleInstanceMachine(IMachineBlockEntity holder) {
        super(holder);
    }
}
