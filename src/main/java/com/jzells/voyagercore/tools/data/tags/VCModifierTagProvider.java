package com.jzells.voyagercore.tools.data.tags;

import com.jzells.voyagercore.VoyagerCore;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;

import static com.jzells.voyagercore.tools.VCTConModifiers.*;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.*;

public class VCModifierTagProvider extends AbstractModifierTagProvider {
    public VCModifierTagProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, VoyagerCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
//        this.tag(HARVEST_ABILITIES).add(dragon_looting.getId());
    }

    @Override
    public String getName() {
        return "VoyagerCore TCon Modifier Tag Provider";
    }
}
