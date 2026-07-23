package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.utils.Position;

import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class HelperHolderPartMachine extends MultiblockPartMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            HelperHolderPartMachine.class,
            MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @Getter
    private final HelperHandler helperHandler;

    public HelperHolderPartMachine(IMachineBlockEntity holder) {
        super(holder);
        helperHandler = new HelperHandler(this);
    }

    @Override
    public Widget createUIWidget() {
        return new WidgetGroup(new Position(0, 0))
                .addWidget(new SlotWidget(helperHandler, 0, 4, 4)
                        .setBackground(GuiTextures.SLOT));
    }

    private class HelperHandler extends NotifiableItemStackHandler {

        public HelperHandler(MetaMachine machine) {
            super(machine, 1, IO.IN, IO.BOTH, size -> new CustomItemStackHandler(size) {

                @Override
                public int getSlotLimit(int slot) {
                    return 1;
                }
            });
        }

        // Definitely a Better way to do this, but I don't care.

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (stack.isEmpty()) return true;
            boolean isHelperItem = false;
            if (stack.getItem() instanceof IComponentItem metaItem) {
                for (IItemComponent behavior : metaItem.getComponents()) {
                    if (behavior instanceof HelperItemComponent) {
                        isHelperItem = true;
                        break;
                    }
                }
            }
            return isHelperItem;
        }
    }
}
