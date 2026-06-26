package com.jzells.voyagercore.data.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;

import com.jzells.voyagercore.VoyagerCore;

import static com.jzells.voyagercore.data.VoyagerMaterials.*;

public class FluidMaterials {

    public static void register() {
        Pyrotheum = new Material.Builder(VoyagerCore.id("pyrotheum"))
                .color(0xff5900, true)
                .liquid(new FluidBuilder().temperature(2000))
                .buildAndRegister();
    }
}
