package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IInteractedMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import forestry.api.apiculture.genetics.IBee;
import forestry.api.genetics.ILifeStage;
import forestry.core.utils.SpeciesUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import static forestry.api.apiculture.genetics.BeeLifeStage.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BeeHolderPartMachine extends MultiblockPartMachine implements IControllable, IMachineLife, IInteractedMachine {

    @Persisted
    public final NotifiableItemStackHandler inventory;

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(BeeHolderPartMachine.class, MultiblockPartMachine.MANAGED_FIELD_HOLDER);
    protected final IO io;
    @Getter
    @Setter
    @Persisted
    @DescSynced
    @RequireRerender
    protected boolean workingEnabled;

    public BeeHolderPartMachine(IMachineBlockEntity holder, IO io) {
        super(holder);
        this.io = io;
        this.workingEnabled = true;
        this.inventory = new NotifiableItemStackHandler(this, 2, IO.BOTH);
    }


    //Maybe not needed? Used in RotorHolderPartMachine, but missing in later versions
    @Override
    public ManagedFieldHolder getFieldHolder(){
        return MANAGED_FIELD_HOLDER;
    };




    //Ripped straight from TieredIOPartMachine, might base to be TieredIOPart, in case more storage is needed
    //Seems to be missing in 8.0, will need to find why
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
            super(machine,3,IO.IN,IO.BOTH, size -> new CustomItemStackHandler(size) {

                //Limits stack size of machine to 4 per slot, no drone singularity :P. Probably not needed, but JIC
                @Override
                public int getSlotLimit(int slot){
                    return 4;
                }
            });

        }

        @Override
        public int getSlotLimit(int slot){
            return 4;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (stack.isEmpty()){
                return true;
            }


            //Slot mapping 0 -> Queen/Princess, Else Drone
            if (stack.getItem() instanceof IBee bee){
                ILifeStage beeAge = SpeciesUtil.BEE_TYPE.get().getLifeStage(stack);
                if (slot == 0 && (beeAge == QUEEN || beeAge == PRINCESS)){
                    return true;
                }
                return slot != 0 && beeAge == DRONE;
            }
            return false;
        }
    }
}



//Need to filter slots
//Get Auto input to work as well as working to disable that capability if needed