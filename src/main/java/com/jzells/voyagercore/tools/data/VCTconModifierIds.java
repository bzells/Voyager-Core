package com.jzells.voyagercore.tools.data;

import com.jzells.voyagercore.VoyagerCore;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class VCTconModifierIds {

    public static final ModifierId dragon_strength = id("dragon_strength");
    public static final ModifierId dragon_protection = id("dragon_protection");

    public static ModifierId id(String name){
        return new ModifierId(VoyagerCore.MOD_ID,name);
    }
}
