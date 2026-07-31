package com.jzells.voyagercore.common.item.component.tooltip;

import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.jzells.voyagercore.common.item.component.*;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HelperModuleTooltipComponent implements IAddInformation {
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (stack.getItem() instanceof HelperModuleComponentTooltipItem hmcpti)
        {
            for(IItemComponent comp : hmcpti.getComponents())
            {
                if(comp instanceof HelperModuleItemComponent helperModuleItemComponent)
                {
                    tooltipComponents.add(Component.literal(helperModuleItemComponent.isSpecialized() ? "§7Can only be installed on §r§6specialized §r§7helpers" : "§7Can be installed on all helpers"));
                    if(helperModuleItemComponent instanceof HelperRecipeModuleItemComponent recipeModule)
                    {
                        // good lord i love Java
                        addTooltip((helperModuleItemComponent.isSpecialized() ? "§7Specialization§r" : "§7Recipe§r"),
                                helperModuleItemComponent.isSpecialized() ? VoyagerVoltageTierUtils.helperSpecializationFromData(recipeModule.getRecipeType()) : VoyagerVoltageTierUtils.helperRecipeFromID(recipeModule.getRecipeType())
                                , tooltipComponents);

                        tooltipComponents.add(Component.literal(""));
                        tooltipComponents.add(Component.literal("§7Recipe Slots Required: §r" + recipeModule.getRECIPE_COUNT()));

                    }
                    if(helperModuleItemComponent instanceof HelperModuleItemModifierComponent modifierComponent)
                    {
                        addTooltip("§7Speed:§r ", modifierComponent.getSPEED(), tooltipComponents, true);
                        addTooltip("§7Efficiency:§r ", modifierComponent.getEUT_REDUCTION_PERCENT(), tooltipComponents, true);
                        addTooltip("§7Parallels:§r ", modifierComponent.getPARALLELS(), tooltipComponents);
                        addTooltip("§7Output Modifier:§r ", modifierComponent.getOUTPUT_MOD(), tooltipComponents, false);
                        tooltipComponents.add(Component.literal(""));
                        tooltipComponents.add(Component.literal("§7Modifier Slots Required: §r" + modifierComponent.getMODULE_SPACE()));

                    }
                    if(helperModuleItemComponent instanceof HelperModuleItemBeamComponent beamComponent)
                    {
                        addTooltip("§7Beam Concentration:§r ", beamComponent.getBEAM_PERCENT(), tooltipComponents, true);
                    }

                }
            }
        }
    }
    private static void addTooltip(String name, int amt, List<Component> tooltipComponents)
    {
        if(amt > 0)
            tooltipComponents.add(Component.literal(name + "§a" + amt));
    }

    private static void addTooltip(String name, String recipe, List<Component> tooltipComponents)
    {
            tooltipComponents.add(Component.literal("§6" + name + ": " + recipe));
    }

    private static void addTooltip(String name, float amt, List<Component> tooltipComponents, boolean isPercentage)
    {
        String s = isPercentage ? String.format("%.1f%%", amt * 100) : String.format("%.2f", amt) + "x";
        if(amt > 0)
        {
            tooltipComponents.add(Component.literal(name + "§a+" + s));
        }
        if(amt < 0f)
        {
            tooltipComponents.add(Component.literal(name + "§c" + s));
        }

    }
}


