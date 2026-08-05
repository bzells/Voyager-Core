package com.jzells.voyagercore.common.item.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class EnergyHelperModuleItemModifierComponent extends HelperModuleItemModifierComponent {

    public EnergyHelperModuleItemModifierComponent(int gt_tier, int parallels, int moduleSpace,
                                                   @Nullable Boolean paramount, @Nullable String paramountData,
                                                   float eutMod, float eatMod, float outputMod, int levelReq) {
        super(gt_tier, parallels, 0, 0, 0, false, moduleSpace, paramount, paramountData, levelReq);
        this.EUTMOD = eutMod;
        this.EAT_MOD = eatMod;
        this.OUTPUT = outputMod;
    }

    private final float EUTMOD;
    private final float EAT_MOD;
    private final float OUTPUT;

    @Override
    public void apply(ItemStack stack) {
        super.apply(stack);

        CompoundTag tag = stack.getOrCreateTagElement("paramount_helper_data");

        if (!tag.contains("eut_gen_mod")) {
            tag.putInt("eut_gen_mod", 1);
        }

        if (!tag.contains("eat_time_mod")) {
            tag.putFloat("eat_time_mod", 1.0f);
        }

        if (!tag.contains("parallels")) {
            tag.putInt("parallels", 1);
        }

        if (!tag.contains("output")) {
            tag.putFloat("output", 1.0f);
        }

        int helperParallel = tag.getInt("parallels");
        float helperEUt = tag.getFloat("eut_gen_mod");
        float helperSpeed = tag.getFloat("eat_time_mod");
        float outputMod = tag.getFloat("output");

        float eutm;
        float speed;

        if(MULT)
        {
            eutm = Math.max(0.05f, helperEUt + (helperEUt * EUTMOD));
            speed = Math.max(0.05f, helperSpeed + (helperSpeed * EAT_MOD));
        }
        else
        {
            eutm = Math.max(0.05f, helperEUt + EUTMOD);
            speed = Math.max(0.05f, helperSpeed + EAT_MOD);
        }



        tag.putInt("parallels", helperParallel + getPARALLELS());
        tag.putFloat("eut_gen_mod", eutm);
        tag.putFloat("eat_time_mod", speed);
        tag.putFloat("output", outputMod + OUTPUT);
    }
}
