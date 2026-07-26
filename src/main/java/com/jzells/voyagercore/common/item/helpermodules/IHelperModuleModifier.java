package com.jzells.voyagercore.common.item.helpermodules;

import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.common.item.component.HelperItemComponent;

public interface IHelperModuleModifier {

    void apply(ItemStack stack);

    boolean canApply(ItemStack stack, HelperItemComponent helperItemComponent);
}
