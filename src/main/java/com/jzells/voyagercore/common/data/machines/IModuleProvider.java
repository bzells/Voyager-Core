package com.jzells.voyagercore.common.data.machines;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import javax.annotation.Nullable;

public interface IModuleProvider extends IMachineFeature {

    @Nullable
    IModuleReceiver getModuleMachine();

    void setModuleMachine(IModuleReceiver machine);
}
