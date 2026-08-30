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

import com.jzells.voyagercore.common.item.component.EnergyModParamountHelperItemComponent;
import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import com.jzells.voyagercore.common.item.component.ParamountHelperItemComponent;
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
        boolean isParamount = false;
        // hungryHelperTooltip(e, tooltipComponents);
        boolean isEnergyParamount = false;
        ParamountHelperItemComponent paramountHelperItemComponent = null;
        EnergyModParamountHelperItemComponent energyModParamountHelperItemComponent = null;

        if (stack.getItem() instanceof ComponentItem componentItem) {
            for (IItemComponent component : componentItem.getComponents()) {
                if (component instanceof HelperItemComponent helperComponent) {

                    if (helperComponent instanceof ParamountHelperItemComponent p) {
                        isParamount = true;
                        paramountHelperItemComponent = p;
                        paramountHelperItemComponent.setOwner(stack);
                        if (p instanceof EnergyModParamountHelperItemComponent e) {
                            isEnergyParamount = true;
                            energyModParamountHelperItemComponent = e;
                        }

                    }

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
            if (isParamount) tooltipComponents.add(Component.literal("§6P§ea§ara§dmo§5unt §1He§9lp§ber"));
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

        if (isEnergyParamount) {
            hungryHelperTooltip(energyModParamountHelperItemComponent, tooltipComponents, remaining);
            return;
        }

        if (remaining != 0)
            tooltipComponents.add(Component
                    .literal((specialized ? "§7Remaining Specialization Slots: §a" : "§7Remaining Module Slots: §a") +
                            remaining));
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
            if (key.equals("output") && (specialized || isParamount)) {
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
        if (!isParamount) {
            if (remainingRecipes != 0) {

                tooltipComponents.add(Component.literal("§7Remaining Recipe Slots: §a" + remainingRecipes));
            }

            tooltipComponents.add(Component.literal(specialized ? "§3Specialized In:" : "§6Compatible Recipes:"));
            for (String key : recipes.getAllKeys()) {
                String recipe = specialized ? String.valueOf(key) : String.valueOf(GTRecipeTypes.get(key));
                // System.out.println(recipe);
                tooltipComponents.add(Component.literal(
                        (specialized ? VoyagerVoltageTierUtils.helperSpecializationFromData(recipe) :
                                VoyagerVoltageTierUtils.helperRecipeFromID(recipe))));
            }
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component
                    .literal("§6Helper Tier: " + VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier])));
            if (specialized) {
                tooltipComponents.add(Component.literal("§eSpecialized"));
            }
        }

        if (isParamount) {
            tooltipComponents.add(Component.literal("§7Max Recipe Tier: §r" + VoyagerVoltageTierUtils
                    .getVoltageTierColorStringShortForm(VN[paramountHelperItemComponent.getGTTier() + 1])));
            tooltipComponents.add(Component.literal("§7Helper Level: §b" + paramountHelperItemComponent.getLevel()));
            tooltipComponents.add(Component.literal("§7XP: " + paramountHelperItemComponent.getCurrentXP() + "/" +
                    paramountHelperItemComponent.getLevelUpXP()));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.literal("§7Paramount Application: " + VoyagerVoltageTierUtils
                    .paramountApplicationFromData(paramountHelperItemComponent.getPARAMOUNT_DATA())));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.literal("§6P§ea§ara§dmo§5unt §1He§9lp§ber"));

        }
    }

    private void hungryHelperTooltip(EnergyModParamountHelperItemComponent hungryHelper,
                                     List<Component> tooltipComponents, int remaining) {
        float hunger = 1 / hungryHelper.getEatTimeMod();
        String color = "";
        if (hunger >= .75) color = "§a";
        if (hunger < .75 && hunger > .5) color = "§e";
        if (hunger <= .50) color = "§c";
        if (remaining != 0)
            tooltipComponents.add(Component.literal("§7Remaining Module Slots: §a" + remaining));
        tooltipComponents.add(Component.literal("§6Helper Stats:"));
        tooltipComponents.add(
                Component.literal("§7Hunger: " + color + String.format("%.2f%%", 100 * hungryHelper.getEatTimeMod())));
        tooltipComponents
                .add(Component.literal("§7Power Bonus: §a" + String.format("%.2f", hungryHelper.getEUtGenMod()) + "x"));
        tooltipComponents.add(
                Component.literal("§7Output Modifier: §a" + String.format("%.2f", hungryHelper.getOutput()) + "x"));
        tooltipComponents.add(
                Component.literal("§7Parallels: §a" + hungryHelper.getParallels() + "x"));
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§7Helper Level: §b" + hungryHelper.getLevel()));
        tooltipComponents.add(Component.literal("§7XP: " + hungryHelper.getCurrentXP() + "/" +
                hungryHelper.getLevelUpXP()));
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§7Paramount Application: " + VoyagerVoltageTierUtils
                .paramountApplicationFromData(hungryHelper.getPARAMOUNT_DATA())));
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§6P§ea§ara§dmo§5unt §1He§9lp§ber"));
    }
}
