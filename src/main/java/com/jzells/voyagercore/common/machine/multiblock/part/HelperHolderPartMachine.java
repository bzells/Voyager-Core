package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.utils.Position;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.common.item.component.HelperItemComponent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class HelperHolderPartMachine extends MultiblockPartMachine implements IMachineLife {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            HelperHolderPartMachine.class,
            MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
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


    /**
     * Method to get the ItemStack inside the HelperHolder, can be used to remove helper, if wanted.
     * 
     * @param remove True removes the item in the Holder
     * @return ItemStack of helper
     */
    public ItemStack getHeldItem(boolean remove) {
        return getHeldItem(0, remove);
    }

    public void setHeldItem(ItemStack heldItem) {
        helperHandler.setStackInSlot(0, heldItem);
    }

    private ItemStack getHeldItem(int slot, boolean remove) {
        ItemStack stackInSlot = helperHandler.getStackInSlot(slot);
        if (remove && stackInSlot != ItemStack.EMPTY) {
            helperHandler.setStackInSlot(slot, ItemStack.EMPTY);
        }
        return stackInSlot;
    }

    @Override
    public void onMachineRemoved() {
        clearInventory(this.helperHandler.storage);
    }

    public NotifiableItemStackHandler getAsHandler() {
        return helperHandler;
    }

    private class HelperHandler extends NotifiableItemStackHandler {

        public HelperHandler(MetaMachine machine) {
            super(machine, 1, IO.IN, IO.BOTH, size -> new CustomItemStackHandler(size) {

                // Limits number of Items in slot, used in case someone forgets to set stack size of helper.
                @Override
                public int getSlotLimit(int slot) {
                    return 1;
                }
            });
        }
        // Definitely a Better way to do this, but I don't care.
        // Restricts Allowed Items to only those with HelperItemComponent attached

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
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
