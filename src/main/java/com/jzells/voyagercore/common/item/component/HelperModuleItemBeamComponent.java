package com.jzells.voyagercore.common.item.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;

@Getter
public class HelperModuleItemBeamComponent extends HelperModuleItemModifierComponent {

    private final float BEAM_PERCENT;

    public HelperModuleItemBeamComponent(int gt_tier, int parallels, float eutReductionPercent, float speed,
                                         float outputMod, boolean specialized, int moduleSpace, float beamPercent) {
        super(gt_tier, parallels, eutReductionPercent, speed, outputMod, specialized, moduleSpace);
        this.BEAM_PERCENT = beamPercent;
    }

    @Override
    public void apply(ItemStack stack) {
        super.apply(stack);
        CompoundTag tag = stack.getOrCreateTagElement(this.getTAG_ELEMENT());
        if (!tag.contains("beam")) {
            tag.putFloat("output", 1.0f);
        }

        float beamP = tag.getFloat("beam");

        tag.putFloat("beam", beamP + BEAM_PERCENT);
    }
}
