package com.jzells.voyagercore;

import com.jzells.voyagercore.tools.modifiers.DragonLootingModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class VoyagerCoreTConModifiers {
    static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(VoyagerCore.MOD_ID);

    /// Have to add tags to this for tinkers to recognize it correctly. Oh, and datagen.
    public static final StaticModifier<DragonLootingModifier> dragon_looting = MODIFIERS.register("dragon_looting", DragonLootingModifier::new);
}
