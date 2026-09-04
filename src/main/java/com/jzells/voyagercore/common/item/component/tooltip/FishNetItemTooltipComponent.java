package com.jzells.voyagercore.common.item.component.tooltip;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import com.jzells.voyagercore.common.item.component.FishNetItemComponent;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.VN;

public class FishNetItemTooltipComponent implements IAddInformation {

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents,
                                TooltipFlag isAdvanced) {
        if (stack.getItem() instanceof ComponentItem componentItem) {
            for (IItemComponent comp : componentItem.getComponents()) {
                if (comp instanceof FishNetItemComponent fishNetItemComponent) {
                    tooltipComponents.add(Component.literal("Tier: " + VoyagerVoltageTierUtils
                            .getVoltageTierColorStringShortForm(VN[fishNetItemComponent.getTier()])));
                    tooltipComponents.add(Component.literal("Parallels: " + fishNetItemComponent.getPars()));
                }
            }
        }
    }
}
