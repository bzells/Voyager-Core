package com.jzells.voyagercore.common.item;

import com.jzells.voyagercore.common.item.component.HelperModuleItemComponent;
import net.minecraft.world.item.ItemStack;

public interface IHelperItem {

    boolean canInstall(ItemStack stack, HelperModuleItemComponent module);

    void installModule(ItemStack stack, HelperModuleItemComponent module);

    int getModuleGTTier(ItemStack stack, HelperModuleItemComponent type);
}
