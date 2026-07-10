package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.BlockableSlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.IInteractedMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;

import forestry.api.genetics.ILifeStage;
import forestry.core.utils.SpeciesUtil;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import static forestry.api.apiculture.genetics.BeeLifeStage.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BeeHolderPartMachine extends MultiblockPartMachine
                                  implements IControllable, IMachineLife, IInteractedMachine, IFancyUIMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(BeeHolderPartMachine.class,
            MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @Getter
    private final BeeHolderHandler beeHolder;

    protected final IO io;
    @Getter
    @Setter
    @Persisted
    @DescSynced
    @RequireRerender //Necessary?
    protected boolean workingEnabled;
    @Getter
    @Setter
    @Persisted
    @DescSynced
    public boolean isLocked;

    public BeeHolderPartMachine(IMachineBlockEntity holder, IO io) {
        super(holder);
        this.io = io;
        this.workingEnabled = true;
        beeHolder = new BeeHolderHandler(this);
    }

    @Override
    public void onMachineRemoved(){
        clearInventory(this.beeHolder.storage);
    }

    public ItemStack getRoyal(){
        return this.beeHolder.getStackInSlot(0);
    }

    // Maybe not needed? Used in RotorHolderPartMachine, but missing in later versions
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    };

    // Ripped straight from TieredIOPartMachine, might base to be TieredIOPart, in case more storage is needed
    // Seems to be missing in 8.0, will need to find why

    @Nullable
    @Override
    public PageGroupingData getPageGroupingData() {
        return switch (this.io) {
            case IN -> new PageGroupingData("gtceu.multiblock.page_switcher.io.import", 1);
            case OUT -> new PageGroupingData("gtceu.multiblock.page_switcher.io.export", 2);
            case BOTH -> new PageGroupingData("gtceu.multiblock.page_switcher.io.both", 3);
            case NONE -> null;
        };
    }

    private class BeeHolderHandler extends NotifiableItemStackHandler {

        public BeeHolderHandler(MetaMachine machine) {
            super(machine, 4, IO.IN, IO.BOTH, size -> new CustomItemStackHandler(size) {

                // Limits stack size of machine to 4 per slot, no drone singularity :P. Probably not needed, but JIC
                @Override
                public int getSlotLimit(int slot) {
                    return 4;
                }
            });
        }

        @Override
        public int getSlotLimit(int slot) {
            return 4;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (stack.isEmpty()) {
                return true;
            }
            boolean isDrone = false;
            boolean isRoyal = false;
            // Slot mapping 0 -> Queen/Princess, Else Drone
            ILifeStage beeAge = SpeciesUtil.BEE_TYPE.get().getLifeStage(stack);
            if ((beeAge == QUEEN)||(beeAge == PRINCESS)){
                isRoyal = true;
            }
            if ((beeAge == DRONE)) {
                   isDrone = true;
            }

            if (slot == 0 && isRoyal){
                return true;
            } else return slot != 0 && isDrone;
        }

        public ItemStack extractItem(int slot, int amount, boolean simulate){
            if (!isLocked()){
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }
    }

    //I'm lazy, and just want this thing to work lol
    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0,0,18*2+16,18*2+16);
        var container = new WidgetGroup(4,4,18*2+8,18*2+8);
        container.addWidget(new BlockableSlotWidget(beeHolder,0,4,4)
                .setIsBlocked(this::isLocked)
                .setBackground(GuiTextures.SLOT)); //Can add overlay to slot
        container.addWidget(new BlockableSlotWidget(beeHolder,1,4+18,4)
                .setIsBlocked(this::isLocked)
                .setBackground(GuiTextures.SLOT));
        container.addWidget(new BlockableSlotWidget(beeHolder,2,4,4+18)
                .setIsBlocked(this::isLocked)
                .setBackground(GuiTextures.SLOT));
        container.addWidget(new BlockableSlotWidget(beeHolder,3,4+18,4+18)
                .setIsBlocked(this::isLocked)
                .setBackground(GuiTextures.SLOT));
        container.setBackground(GuiTextures.BACKGROUND_INVERSE);
        group.addWidget(container);
        return group;
    }
}

// Need to filter slots done
// Get Auto input to work as well as working to disable that capability if needed
