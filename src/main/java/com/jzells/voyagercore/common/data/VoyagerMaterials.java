package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import com.jzells.voyagercore.common.data.materials.FluidMaterials;
import com.jzells.voyagercore.common.data.materials.MetalMaterials;

public class VoyagerMaterials {

    public static void init() {
        FluidMaterials.register();
        MetalMaterials.register();
    }

    public static Material Pyrotheum;
    public static Material Cryotheum;

    public static Material Calorite;
}
