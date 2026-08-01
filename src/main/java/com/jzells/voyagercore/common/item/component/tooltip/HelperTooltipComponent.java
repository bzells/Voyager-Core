package com.jzells.voyagercore.common.item.component.tooltip;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.*;

public class HelperTooltipComponent implements IAddInformation {

    @Override
    public void onAttached(Item item) {
        IAddInformation.super.onAttached(item);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents,
                                TooltipFlag isAdvanced) {
        int maxModules = 0;
        int maxRecipes = 0;
        int tier = 0;
        boolean specialized = false;
        boolean isHull = false;

        if (stack.getItem() instanceof ComponentItem componentItem) {
            for (IItemComponent component : componentItem.getComponents()) {
                if (component instanceof HelperItemComponent helperComponent) {
                    maxModules = helperComponent.getMAX_MODULE_COUNT();
                    maxRecipes = helperComponent.getRecipeCount();
                    tier = helperComponent.getTier();
                    specialized = helperComponent.isSpecialized();
                    isHull = helperComponent.isHull();
                    break;
                }
            }
        }

        if (isHull) {
            tooltipComponents.add(Component.literal("§7Modifier Slots: §a" + maxModules));
            tooltipComponents.add(Component.literal(
                    specialized ? "§7Specialization Slots: §b" + maxRecipes : "§7Recipe Slots: §b" + maxRecipes));
            return;
        }

        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains("modifiers")) {
            return;
        }

        CompoundTag modifiers = tag.getCompound("modifiers");
        CompoundTag recipes = tag.getCompound("recipes");

        if (modifiers.isEmpty()) {
            return;
        }

        int installed = 0;
        int insalledRecipes = 0;
        float beam = 0;

        if (tag.contains("recipes")) {
            insalledRecipes = recipes.getAllKeys().size();
        }

        if (modifiers.contains("count")) {
            installed = Integer.parseInt(modifiers.getString("count"));
        }
        if (modifiers.contains("beam")) {
            beam = modifiers.getFloat("beam");
        }

        int remaining = maxModules - installed;

        int remainingRecipes = maxRecipes - insalledRecipes;

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
            if (key.equals("beam")) {
                float beamP = modifiers.getFloat(key);
                String color;
                if (beamP > 1.0f) {
                    color = "§a";
                } else if (beamP == 1.0f) {
                    color = "§e";
                } else {
                    color = "§c";
                }
                tooltipComponents.add(Component.literal(
                        "§7Beam Concentration: " + color +
                                String.format("%.1f%%", modifiers.getFloat(key) * 100)));
            }
            if (key.equals("output") && specialized) {
                float output = modifiers.getFloat(key);
                String color;
                if (output > 1.0f) {
                    color = "§a";
                } else if (output == 1.0f) {
                    color = "§e";
                } else {
                    color = "§c";
                }
                tooltipComponents.add(Component.literal(
                        "§7Output Modifier: " + color +
                                String.format("%.2f", modifiers.getFloat(key)) + "x"));
            }
        }
        tooltipComponents.add(Component.literal(""));
        if (remainingRecipes != 0) {

            tooltipComponents.add(Component.literal("§7Remaining Recipe Slots: §a" + remainingRecipes));
        }

        tooltipComponents.add(Component.literal(specialized ? "§3Specialized In:" : "§7Compatible Recipes:"));
        for (String key : recipes.getAllKeys()) {
            String recipe = specialized ? String.valueOf(key) : String.valueOf(GTRecipeTypes.get(key));
            // System.out.println(recipe);
            tooltipComponents.add(Component.literal(
                    "§6" + (specialized ? VoyagerVoltageTierUtils.helperSpecializationFromData(recipe) : recipe)));
        }
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component
                .literal("§6Helper Tier: " + VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier])));
        if (specialized) {
            tooltipComponents.add(Component.literal("§eSpecialized"));
        }
    }
}
