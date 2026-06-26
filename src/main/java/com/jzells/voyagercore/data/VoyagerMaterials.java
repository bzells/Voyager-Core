package com.jzells.voyagercore.data;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import com.jzells.voyagercore.data.materials.FluidMaterials;

public class VoyagerMaterials {

    public static void init() {
        FluidMaterials.register();
    }

    public static Material Pyrotheum;
}
