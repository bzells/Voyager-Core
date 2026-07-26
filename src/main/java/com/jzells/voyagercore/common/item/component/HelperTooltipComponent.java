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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents,
                                TooltipFlag isAdvanced) {
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

        int installed = 0;

        if (modifiers.contains("count")) {
            installed = Integer.parseInt(modifiers.getString("count"));
        }

        int remaining = maxModules - installed;

        if (remaining != 0)
            tooltipComponents.add(Component.literal("§7Remaining Module Slots: §a" + remaining));
        tooltipComponents.add(Component.literal("§6Helper Stats:"));

        for (String key : modifiers.getAllKeys()) {
            if (key.equals("count")) {
                continue;
            }
            if (key.equals("parallels")) {
                int pars = modifiers.getInt(key);
                String color;
                if (pars > 1)
                    color = "§a";
                else {
                    color = "§e";
                }
                tooltipComponents.add(Component.literal("§7Parallels: " + color + modifiers.getInt(key)));
            }
            if (key.equals("eut")) {
                float eut = modifiers.getFloat(key);
                String color;
                if (eut > 1.0f) {
                    color = "§a";
                } else if (eut == 1.0f) {
                    color = "§e";
                } else {
                    color = "§c";
                }
                tooltipComponents.add(Component.literal(
                        "§7Efficiency: " + color +
                                String.format("%.1f%%", modifiers.getFloat(key) * 100)));
            }
            if (key.equals("speed")) {
                float speed = modifiers.getFloat(key);
                String color;
                if (speed > 1.0f) {
                    color = "§a";
                } else if (speed == 1.0f) {
                    color = "§e";
                } else {
                    color = "§c";
                }
                tooltipComponents.add(Component.literal(
                        "§7Speed: " + color +
                                String.format("%.1f%%", modifiers.getFloat(key) * 100)));
            }
        }
    }
}
