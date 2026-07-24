package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.lowdragmc.lowdraglib.syncdata.managed.IManagedVar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import static com.gregtechceu.gtceu.api.GTValues.VOLTAGE_NAMES;

public class HelperModuleItemTierComponent extends HelperModuleItemComponent{
    public HelperModuleItemTierComponent(int gt_tier, String moduleData) {
        super(gt_tier, moduleData);
    }

    @Override
    public void apply(ItemStack stack) {
        super.apply(stack);
        this.tag(stack).putString("helper_tier", VOLTAGE_NAMES[this.getGT_TIER()]);
    }
}
