package com.jzells.voyagercore.common.item.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.util.VoyagerConstants;
import lombok.Getter;

@Getter
public class HelperModuleItemModifierComponent extends HelperModuleItemComponent {

    private final int PARALLELS;
    private final float EUT_REDUCTION_PERCENT;
    private final float SPEED;
    private final float OUTPUT_MOD;
    @Getter
    protected boolean MULT = true;
    // private final float PARAMOUNT_LEVEL;

    public HelperModuleItemModifierComponent(int gt_tier, int parallels, float eutReductionPercent, float speed,
                                             float outputMod, boolean specialized, int moduleSpace) {
        super(gt_tier, null, specialized, moduleSpace);
        PARALLELS = parallels;
        EUT_REDUCTION_PERCENT = eutReductionPercent;
        SPEED = speed;
        OUTPUT_MOD = outputMod;
        // PARAMOUNT_LEVEL = 0;
    }

    public HelperModuleItemModifierComponent(int gt_tier, int parallels, float eutReductionPercent, float speed,
                                             float outputMod, boolean specialized, int moduleSpace, boolean mult) {
        super(gt_tier, null, specialized, moduleSpace);
        PARALLELS = parallels;
        EUT_REDUCTION_PERCENT = eutReductionPercent;
        SPEED = speed;
        OUTPUT_MOD = outputMod;
        this.MULT = mult;
        // PARAMOUNT_LEVEL = 0;
    }

    public HelperModuleItemModifierComponent(int gt_tier, int parallels, float eutReductionPercent, float speed,
                                             float outputMod, boolean specialized, int moduleSpace,
                                             boolean paramount, String paramountData,
                                             int levelReq) {
        super(gt_tier, paramountData, specialized, moduleSpace, paramount, levelReq);
        PARALLELS = parallels;
        EUT_REDUCTION_PERCENT = eutReductionPercent;
        SPEED = speed;
        OUTPUT_MOD = outputMod;
        // PARAMOUNT_LEVEL = levelReq;
    }

    public HelperModuleItemModifierComponent(int gt_tier, int parallels, float eutReductionPercent, float speed,
                                             float outputMod, boolean specialized, int moduleSpace,
                                             boolean paramount, String paramountData,
                                             int levelReq, boolean mult) {
        super(gt_tier, paramountData, specialized, moduleSpace, paramount, levelReq);
        PARALLELS = parallels;
        EUT_REDUCTION_PERCENT = eutReductionPercent;
        SPEED = speed;
        OUTPUT_MOD = outputMod;
        this.MULT = mult;
        // PARAMOUNT_LEVEL = levelReq;
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

        float eut;
        float speed;

        if(MULT)
        {
            eut = Math.max(VoyagerConstants.MIN_HELPER_EUT, helperEUt + (helperEUt * EUT_REDUCTION_PERCENT));
            speed = Math.max(VoyagerConstants.MIN_HELPER_SPEED, helperSpeed + (helperSpeed * SPEED));
        }
        else
        {
            eut = Math.max(VoyagerConstants.MIN_HELPER_EUT, helperEUt + EUT_REDUCTION_PERCENT);
            speed = Math.max(VoyagerConstants.MIN_HELPER_SPEED, helperSpeed + SPEED);
        }



        tag.putInt("parallels", helperParallel + PARALLELS);
        tag.putFloat("eut", eut);
        tag.putFloat("speed", speed);
        tag.putFloat("output", outputMod + OUTPUT_MOD);
    }
}
