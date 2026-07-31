package com.jzells.voyagercore.common.item.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;

@Getter
public class HelperModuleItemModifierComponent extends HelperModuleItemComponent {

    private final int PARALLELS;
    private final float EUT_REDUCTION_PERCENT;
    private final float SPEED;
    private final float OUTPUT_MOD;
    private final int MODULE_SPACE;

    public HelperModuleItemModifierComponent(int gt_tier, int parallels, float eutReductionPercent, float speed,
                                             float outputMod, boolean specialized, int moduleSpace) {
        super(gt_tier, null, specialized);
        PARALLELS = parallels;
        EUT_REDUCTION_PERCENT = eutReductionPercent;
        SPEED = speed;
        OUTPUT_MOD = outputMod;
        MODULE_SPACE = moduleSpace;
    }

    @Override
    public void apply(ItemStack stack) {
        super.apply(stack);

        CompoundTag tag = stack.getOrCreateTagElement(this.getTAG_ELEMENT());

        if (!tag.contains("parallels")) {
            tag.putInt("parallels", 1);
        }

        if (!tag.contains("eut")) {
            tag.putFloat("eut", 1.0f);
        }

        if (!tag.contains("speed")) {
            tag.putFloat("speed", 1.0f);
        }

        if (!tag.contains("output")) {
            tag.putFloat("output", 1.0f);
        }

        int helperParallel = tag.getInt("parallels");
        float helperEUt = tag.getFloat("eut");
        float helperSpeed = tag.getFloat("speed");
        float outputMod = tag.getFloat("output");

        tag.putInt("parallels", helperParallel + PARALLELS);
        tag.putFloat("eut", helperEUt + EUT_REDUCTION_PERCENT);
        tag.putFloat("speed", helperSpeed + SPEED);
        tag.putFloat("output", outputMod + OUTPUT_MOD);
    }
}
