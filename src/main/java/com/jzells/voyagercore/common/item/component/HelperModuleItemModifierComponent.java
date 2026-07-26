package com.jzells.voyagercore.common.item.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;

@Getter
public class HelperModuleItemModifierComponent extends HelperModuleItemComponent {

    private final int PARALLELS;
    private final float EUT_REDUCTION_PERCENT;
    private final float SPEED;

    public HelperModuleItemModifierComponent(int gt_tier, int parallels, float eutReductionPercent, float speed) {
        super(gt_tier, null);
        PARALLELS = parallels;
        EUT_REDUCTION_PERCENT = eutReductionPercent;
        SPEED = speed;
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

        int helperParallel = tag.getInt("parallels");
        float helperEUt = tag.getFloat("eut");
        float helperSpeed = tag.getFloat("speed");

        tag.putInt("parallels", helperParallel + PARALLELS);
        tag.putFloat("eut", helperEUt + EUT_REDUCTION_PERCENT);
        tag.putFloat("speed", helperSpeed + SPEED);
    }
}
