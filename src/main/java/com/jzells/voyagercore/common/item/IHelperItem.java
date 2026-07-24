package com.jzells.voyagercore.common.item;

import com.jzells.voyagercore.common.item.component.HelperModuleItem;
import net.minecraft.world.item.ItemStack;

public interface IHelperItem {

    boolean canInstall(ItemStack stack, HelperModuleItem module);

    void installModule(ItemStack stack, HelperModuleItem module);

    int getModuleGTTier(ItemStack stack, HelperModuleItem type);
}
