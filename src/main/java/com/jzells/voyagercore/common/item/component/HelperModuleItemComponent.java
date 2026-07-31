package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.common.item.helpermodules.IHelperModuleModifier;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class HelperModuleItemComponent implements IItemComponent, IHelperModuleModifier {

    public HelperModuleItemComponent(int gt_tier, @Nullable String moduleData, boolean specialized) {
        this.ModuleData = moduleData;
        this.GT_TIER = gt_tier;
        this.specialized = specialized;
    }

    @Getter
    private final String TAG_ELEMENT = "modifiers";

    public final CompoundTag tag(ItemStack stack) {
        return stack.getOrCreateTagElement(TAG_ELEMENT);
    }

    private final int GT_TIER;
    private final String ModuleData;
    private final boolean specialized;

    @Override
    public void apply(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTagElement("modifiers");

        if (this.ModuleData != null)
            tag.putString("data", this.ModuleData);
    }

    @Override
    public boolean canApply(ItemStack stack, HelperItemComponent helperItemComponent) {
        int helperTier = helperItemComponent.getTier();
        int currentModuleCount = 0;

        if (specialized && !helperItemComponent.isSpecialized()) {
            return false;
        }

        assert stack.getTag() != null;
        if (stack.getOrCreateTag().contains("modifiers"))
            currentModuleCount = Integer.parseInt(stack.getTagElement("modifiers").getString("count"));

        return ((this.GT_TIER <= helperTier && currentModuleCount < helperItemComponent.getMAX_MODULE_COUNT()));
    }
}
