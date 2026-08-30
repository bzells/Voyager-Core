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
    public static Material Helperade_BR;
    public static Material HIGH_STRESS_LUBRICANT;

    public static Material Lunarium;

    public static Material Calorite;
    public static Material Desh;
    public static Material Ostrum;
    public static Material Aluminex202a;

    public static Material Pearlic_Steel;
    public static Material Pink_Steel;
    public static Material Energetic_Alloy;
    public static Material Energetic_Pearlic_Alloy;
}
