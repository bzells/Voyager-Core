package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.jzells.voyagercore.common.item.helpermodules.IHelperModuleModifier;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class HelperModuleItem implements IItemComponent, IHelperModuleModifier {
    public HelperModuleItem(int gt_tier, String moduleData) {
        this.ModuleData = moduleData;
        this.GT_TIER = gt_tier;
    }

    @Getter
    private final int GT_TIER;
    @Getter
    private final String ModuleData;

    @Override
    public void apply(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();

        tag.putString("data", this.ModuleData);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        int helperTier = stack.getOrCreateTag().getInt("tier");

        return this.GT_TIER <= helperTier;
    }
}
