package com.jzells.voyagercore.common.item.helpermodules;

import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import net.minecraft.world.item.ItemStack;

public interface IHelperModuleModifier {
    void apply(ItemStack stack);

    boolean canApply(ItemStack stack, HelperItemComponent helperItemComponent);
}
