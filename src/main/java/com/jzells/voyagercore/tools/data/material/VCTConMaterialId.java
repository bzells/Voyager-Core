package com.jzells.voyagercore.tools.data.material;

import com.jzells.voyagercore.VoyagerCore;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class VCTConMaterialId {

    public static final MaterialId dragonsteel_ice = id("dragonsteel_ice");
    public static final MaterialId dragonsteel_fire = id("dragonsteel_fire");

    private VCTConMaterialId() {};

    public static MaterialId id(String name) {
        return new MaterialId(VoyagerCore.MOD_ID, name);
    }
}
