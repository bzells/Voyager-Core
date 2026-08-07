package com.jzells.voyagercore.common.item.component.tooltip;

import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import com.jzells.voyagercore.common.item.component.*;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.VN;

public class HelperModuleTooltipComponent implements IAddInformation {

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents,
                                TooltipFlag isAdvanced) {
        if (stack.getItem() instanceof HelperModuleComponentTooltipItem hmcpti) {
            for (IItemComponent comp : hmcpti.getComponents()) {
                if (comp instanceof HelperModuleItemComponent helperModuleItemComponent) {
                    if (!helperModuleItemComponent.isPARAMOUNT())
                        tooltipComponents.add(Component.literal(helperModuleItemComponent.isSpecialized() ?
                                "§7Can only be installed on §r§6specialized §r§7helpers" :
                                "§7Can only be installed on §r§6generic§r§7 helpers"));
                    else tooltipComponents.add(Component.literal(VoyagerVoltageTierUtils.paramountApplicationFromData(
                            helperModuleItemComponent.getModuleData()) + " §7helper exclusive"));
                    if (helperModuleItemComponent instanceof EnergyHelperModuleItemModifierComponent e) {
                        addTooltip("§7EUt Boost: ", e.getEUTMOD(), tooltipComponents, false);
                        addTooltipReverse("§7Hunger: ", e.getEAT_MOD(), tooltipComponents, true);
                        addTooltip("§7Output Modifier: ", e.getOUTPUT_MOD(), tooltipComponents, false);
                    }
                    if (helperModuleItemComponent instanceof HelperRecipeModuleItemComponent recipeModule) {
                        // good lord i love Java
                        addTooltip((helperModuleItemComponent.isSpecialized() ? "§7Specialization§r" : "§7Recipe§r"),
                                helperModuleItemComponent.isSpecialized() ?
                                        VoyagerVoltageTierUtils
                                                .helperSpecializationFromData(recipeModule.getRecipeType()) :
                                        VoyagerVoltageTierUtils.helperRecipeFromID(recipeModule.getRecipeType()),
                                tooltipComponents);

                        tooltipComponents.add(Component.literal(""));
                        tooltipComponents
                                .add(Component.literal("§7Recipe Slots Required: §r" + recipeModule.getRECIPE_COUNT()));

                    }
                    if (helperModuleItemComponent instanceof HelperModuleItemModifierComponent modifierComponent) {
                        addTooltip("§7Speed:§r ", modifierComponent.getSPEED(), tooltipComponents, true);
                        addTooltip("§7Efficiency:§r ", modifierComponent.getEUT_REDUCTION_PERCENT(), tooltipComponents,
                                true);
                        addTooltip("§7Parallels:§r ", modifierComponent.getPARALLELS(), tooltipComponents);
                        addTooltip("§7Output Modifier:§r ", modifierComponent.getOUTPUT_MOD(), tooltipComponents,
                                false);
                        tooltipComponents.add(Component.literal(""));
                        tooltipComponents.add(Component
                                .literal("§7Module Slots Required: §r" + modifierComponent.getMODULE_SPACE()));

                    }
                    if (helperModuleItemComponent instanceof HelperModuleItemBeamComponent beamComponent) {
                        addTooltip("§7Beam Concentration:§r ", beamComponent.getBEAM_PERCENT(), tooltipComponents,
                                true);
                    }
                    tooltipComponents.add(Component.literal(helperModuleItemComponent.isPARAMOUNT() ?
                            "§7Module Level: §6" + helperModuleItemComponent.getPARAMOUNT_LEVEL() :
                            "§7Module Tier: " + VoyagerVoltageTierUtils
                                    .getVoltageTierColorStringShortForm(VN[helperModuleItemComponent.getGT_TIER()])));
                    tooltipComponents.add(Component.literal("§7Tier Required to Install: " + VoyagerVoltageTierUtils
                            .getVoltageTierColorStringShortForm(VN[helperModuleItemComponent.getGT_TIER()])));

                    tooltipComponents.add(Component.literal(""));
                    if (helperModuleItemComponent instanceof HelperModuleItemModifierComponent)
                        tooltipComponents.add(Component.literal(
                                "§8Positive values are applied additively, negative values are applied multiplicatively"));

                }
            }
        }
    }

    private static void addTooltip(String name, int amt, List<Component> tooltipComponents) {
        if (amt > 0)
            tooltipComponents.add(Component.literal(name + "§a" + amt));
    }

    private static void addTooltip(String name, String recipe, List<Component> tooltipComponents) {
        tooltipComponents.add(Component.literal("§6" + name + ": " + recipe));
    }

    private static void addTooltip(String name, float amt, List<Component> tooltipComponents, boolean isPercentage) {
        String s = isPercentage ? String.format("%.1f%%", amt * 100) : String.format("%.2f", amt) + "x";
        if (amt > 0) {
            tooltipComponents.add(Component.literal(name + "§a+" + s));
        }
        if (amt < 0f) {
            tooltipComponents.add(Component.literal(name + "§c" + s));
        }
    }

    private static void addTooltipReverse(String name, float amt, List<Component> tooltipComponents,
                                          boolean isPercentage) {
        String s = isPercentage ? String.format("%.1f%%", amt * 100) : String.format("%.2f", amt) + "x";
        if (amt < 0) {
            tooltipComponents.add(Component.literal(name + "§a" + s));
        }
        if (amt > 0f) {
            tooltipComponents.add(Component.literal(name + "§c+" + s));
        }
    }
}
