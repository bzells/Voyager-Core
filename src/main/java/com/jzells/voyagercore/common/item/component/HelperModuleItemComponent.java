package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.common.item.helpermodules.IHelperModuleModifier;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class HelperModuleItemComponent implements IItemComponent, IHelperModuleModifier {

    public HelperModuleItemComponent(int gt_tier, @Nullable String moduleData, boolean specialized, int moduleSpace) {
        this.ModuleData = moduleData;
        this.GT_TIER = gt_tier;
        this.specialized = specialized;
        this.MODULE_SPACE = moduleSpace;
        this.PARAMOUNT = false;
        this.PARAMOUNT_LEVEL = -1;
    }

    public HelperModuleItemComponent(int gt_tier, @Nullable String moduleData, boolean specialized, int moduleSpace,
                                     @Nullable Boolean isParamount, int paramountLevel) {
        this.ModuleData = moduleData;
        this.GT_TIER = gt_tier;
        this.specialized = specialized;
        this.MODULE_SPACE = moduleSpace;
        this.PARAMOUNT = Boolean.TRUE.equals(isParamount);
        this.PARAMOUNT_LEVEL = paramountLevel;
    }

    @Getter
    private final String TAG_ELEMENT = "modifiers";

    public final CompoundTag tag(ItemStack stack) {
        return stack.getOrCreateTagElement(TAG_ELEMENT);
    }

    private final int GT_TIER;
    private final String ModuleData;
    private final boolean specialized;
    private final int MODULE_SPACE;
    private final boolean PARAMOUNT;
    private final int PARAMOUNT_LEVEL;

    @Override
    public void apply(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTagElement("modifiers");

        if (this.ModuleData != null)
            tag.putString("data", this.ModuleData);
    }

    @Override
    public boolean canApply(ItemStack stack, HelperItemComponent helperItemComponent) {
        return helperModuleApplicationLogic(stack, helperItemComponent);
    }

    private boolean helperModuleApplicationLogic(ItemStack stack, HelperItemComponent helperItemComponent) {
        int currentModuleCount = 0;
        int helperTier = helperItemComponent.getTier();
        int helperMaxModules = helperItemComponent.getMAX_MODULE_COUNT();
        boolean isParamountHelper = helperItemComponent instanceof ParamountHelperItemComponent;

        if (stack.getOrCreateTag().contains("modifiers"))
            currentModuleCount = Integer.parseInt(stack.getTagElement("modifiers").getString("count"));

        if (this.PARAMOUNT) {
            if (isParamountHelper) {
                ((ParamountHelperItemComponent) helperItemComponent).setOwner(stack);
                return (currentModuleCount + MODULE_SPACE <= helperMaxModules) &&
                        (((ParamountHelperItemComponent) helperItemComponent).getLevel() >= this.PARAMOUNT_LEVEL);
            }
            return false;

        } else if (this.specialized) {
            if (helperItemComponent.isSpecialized()) return ((this.GT_TIER <= helperTier &&
                    currentModuleCount + MODULE_SPACE <= helperMaxModules));
            else return false;
        } else {
            return ((this.GT_TIER <= helperTier &&
                    currentModuleCount + MODULE_SPACE <= helperMaxModules) && !isParamountHelper);
        }
    }
}
