package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HelperTooltipComponent implements IAddInformation {
    @Override
    public void onAttached(Item item) {
        IAddInformation.super.onAttached(item);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains("modifiers")) {
            return;
        }

        CompoundTag modifiers = tag.getCompound("modifiers");

        if (modifiers.isEmpty()) {
            return;
        }

        int maxModules = 0;


        if (stack.getItem() instanceof ComponentItem componentItem) {
            for (IItemComponent component : componentItem.getComponents()) {
                if (component instanceof HelperItemComponent helperComponent) {
                    maxModules = helperComponent.getMAX_MODULE_COUNT();
                    break;
                }
            }
        }


        String module_count = Integer.toString((maxModules) - Integer.parseInt(tag.getString("module_count")));

        tooltipComponents.add(Component.literal("Remaining Module Slots: " + module_count));
        tooltipComponents.add(Component.literal("Installed Modules:"));

        for (String key : modifiers.getAllKeys()) {
            tooltipComponents.add(
                    Component.literal(" - " + key + ": " + modifiers.getString(key))
            );
        }
    }
}
