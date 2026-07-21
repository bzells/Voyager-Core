package com.jzells.voyagercore.common.data.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;

import com.jzells.voyagercore.VoyagerCore;

import static com.jzells.voyagercore.common.data.VoyagerMaterials.*;

public class FluidMaterials {

    public static void register() {
        Pyrotheum = new Material.Builder(VoyagerCore.id("pyrotheum"))
                .color(0xff5900, true)
                .liquid(new FluidBuilder().temperature(2000))
                .buildAndRegister();

        Cryotheum = new Material.Builder(VoyagerCore.id("cryotheum"))
                .color(0x00fffb, true)
                .liquid(new FluidBuilder().temperature(50))
                .buildAndRegister();

        HIGH_STRESS_LUBRICANT = new Material.Builder(VoyagerCore.id("high_stress_lubricant"))
                .color(0x9972a8, true)
                .liquid(new FluidBuilder().temperature(300))
                .buildAndRegister();
    }
}
