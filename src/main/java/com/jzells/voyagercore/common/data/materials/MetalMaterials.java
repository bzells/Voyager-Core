package com.jzells.voyagercore.common.data.materials;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.jzells.voyagercore.common.data.VoyagerMaterials.*;

public class MetalMaterials {

    public static void register() {
        // registered under the GTCEu registrate due to kjs recipes
        Calorite = new Material.Builder(GTCEu.id("calorite"))
                .color(0xa10030, true)
                .langValue("Calorite")
                .ingot()
                .fluid()
                .iconSet(MaterialIconSet.METALLIC)
                .cableProperties(131072, 1, 8, false)
                .flags(MaterialFlags.GENERATE_FRAME, MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_ROD,
                        MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_FOIL, GENERATE_GEAR,
                        MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.GENERATE_ROTOR, MaterialFlags.GENERATE_RING,
                        MaterialFlags.GENERATE_FINE_WIRE, MaterialFlags.NO_SMELTING)
                .blast(5300)
                .buildAndRegister();

        Ostrum = new Material.Builder(GTCEu.id("ostrum"))
                .langValue("Ostrum")
                .ingot()
                .dust()
                .fluid()
                .color(0xc785a2)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(MaterialFlags.GENERATE_FRAME, MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_ROD,
                        MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_FOIL, GENERATE_GEAR,
                        MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.GENERATE_ROTOR, MaterialFlags.GENERATE_RING,
                        MaterialFlags.GENERATE_FINE_WIRE, MaterialFlags.NO_SMELTING)
                .blastTemp(5400, BlastProperty.GasTier.MID, (int) V[IV], 20 * 64)
                .cableProperties(GTValues.V[LuV], 1, 4, false)
                .buildAndRegister();

        Desh = new Material.Builder(GTCEu.id("desh"))
                .langValue("Desh")
                .color(0xd44e06, true)
                .ingot()
                .fluid()
                .iconSet(MaterialIconSet.METALLIC)
                .cableProperties(8192, 1, 8, false)
                .flags(MaterialFlags.GENERATE_FRAME, MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_ROD,
                        MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_FOIL, GENERATE_GEAR,
                        MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.GENERATE_ROTOR, MaterialFlags.GENERATE_RING,
                        MaterialFlags.GENERATE_FINE_WIRE, MaterialFlags.NO_SMELTING)
                .blast(3600)
                .ore()
                .rotorStats(300, 150, 1, 100000)
                .buildAndRegister();

        Aluminex202a = new Material.Builder(GTCEu.id("aluminex_202_a"))
                .langValue("Aluminex-202a")
                .ingot()
                .fluid()
                .color(0x96fffd)
                .secondaryColor(0x0080ff)
                .iconSet(MaterialIconSet.BRIGHT)
                .flags(MaterialFlags.GENERATE_FRAME, MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_ROD,
                        MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_FOIL, GENERATE_GEAR,
                        MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.GENERATE_ROTOR, MaterialFlags.GENERATE_RING,
                        MaterialFlags.GENERATE_FINE_WIRE, MaterialFlags.NO_SMELTING)
                .buildAndRegister();

        // New Materials
    }
}
