package com.jzells.voyagercore.common.item.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class HelperModuleItemModifierComponent extends HelperModuleItemComponent {

    private final int PARALLELS;
    private final float EUT_REDUCTION_PERCENT;
    private final float SPEED;
    private final float OUTPUT_MOD;

    public HelperModuleItemModifierComponent(int gt_tier, int parallels, float eutReductionPercent, float speed,
                                             float outputMod, boolean specialized, int moduleSpace,
                                             @Nullable Boolean paramount, @Nullable String paramountData) {
        super(gt_tier, paramountData, specialized, moduleSpace, Boolean.TRUE.equals(paramount));
        PARALLELS = parallels;
        EUT_REDUCTION_PERCENT = eutReductionPercent;
        SPEED = speed;
        OUTPUT_MOD = outputMod;
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

        float eut = Math.max(0.05f, helperEUt + EUT_REDUCTION_PERCENT);
        float speed = Math.max(0.05f, helperSpeed + SPEED);

        tag.putInt("parallels", helperParallel + PARALLELS);
        tag.putFloat("eut", eut);
        tag.putFloat("speed", speed);
        tag.putFloat("output", outputMod + OUTPUT_MOD);
    }
}
