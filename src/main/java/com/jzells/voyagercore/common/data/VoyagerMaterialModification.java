package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.data.chemical.material.properties.FluidProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.FluidState;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;

public class VoyagerMaterialModification {
    public static void init() {
        GTMaterials.Salt.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder().state(FluidState.LIQUID).temperature(900)));
        GTMaterials.Salt.getProperty(PropertyKey.FLUID).getStorage().enqueueRegistration(FluidStorageKeys.MOLTEN, new FluidBuilder().state(FluidState.LIQUID).temperature(1400));
    }
}
