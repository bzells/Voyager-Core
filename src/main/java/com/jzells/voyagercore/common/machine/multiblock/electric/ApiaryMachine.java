package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.jzells.voyagercore.common.machine.multiblock.part.BeeHolderPartMachine;
import forestry.api.apiculture.genetics.IBee;
import forestry.api.genetics.ILifeStage;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.utils.SpeciesUtil;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

import static forestry.api.apiculture.genetics.BeeLifeStage.*;

public class ApiaryMachine extends WorkableElectricMultiblockMachine {
    private ArrayList<BeeHolderPartMachine> beeHolders;
    private ArrayList<IBee> beeQueenList;
    private ArrayList<ItemStack> beeQueenItemStackList;
    private ArrayList<IBee> beePrincessList;
    private ArrayList<ItemStack> beePrincessItemStackList;
    private ArrayList<NotifiableItemStackHandler> outputBuses;
    private TickableSubscription beeLogicSubscription;

    public ApiaryMachine(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        initializePartLists();
        beeLogicSubscription = subscribeServerTick(this::beeLogic);
    }
    private void initializePartLists(){
        this.beeHolders = new ArrayList<>();
        this.outputBuses = new ArrayList<>();
        for (IMultiPart part : getParts()){
            //Bee Holders
            if (part instanceof BeeHolderPartMachine beeHolder){
                this.beeHolders.add(beeHolder);
                continue;
            }
            // Add Frame Holder List Here
            // Output Bus
            var handlerLists = part.getRecipeHandlers();
            for (var handlerList : handlerLists){
                var recipeCap = handlerList.getCapability(ItemRecipeCapability.CAP);
                if (handlerList.getHandlerIO().support(IO.OUT) && !recipeCap.isEmpty()){
                    NotifiableItemStackHandler outputInstance = (NotifiableItemStackHandler) recipeCap.get(0);
                    this.outputBuses.add(outputInstance);
                }
            }
        }
    }
    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        resetState();
    }

    @Override
    public void onPartUnload(){
        super.onPartUnload();
        resetState();
    }

    private void resetState(){
        unsubscribe(beeLogicSubscription);
        beeHolders = null;
        beeQueenList = null;
        beeQueenItemStackList = null;
        beePrincessItemStackList = null;
        beePrincessList = null;
        //Other holders need to be set to null here;
    }

    private void beeInit(BeeHolderPartMachine machine){
        if (this.beeQueenList == null){
            this.beeQueenList = new ArrayList<>();
        }
        if (this.beePrincessList == null){
            this.beePrincessList = new ArrayList<>();
        }

        var royal = (IBee) IIndividualHandlerItem.getIndividual(machine.getRoyal());
        if (royal != null){
            ILifeStage beeAge = SpeciesUtil.BEE_TYPE.get().getLifeStage(machine.getRoyal());
            if (beeAge == QUEEN){
                this.beeQueenList.add(royal);
                this.beeQueenItemStackList.add(machine.getRoyal());
            }
            if (beeAge == PRINCESS){
                this.beePrincessList.add(royal);
                this.beePrincessItemStackList.add(machine.getRoyal());
            }
        }
    }

    private void beeLogic(){
        //Bee Initialization
        if (beeHolders != null){
            for (BeeHolderPartMachine part : beeHolders) {
                beeInit(part);
            }
        }
        //Production
        if (getOffsetTimer() % 20 == 0 && isFormed() && !getMultiblockState().hasError()){
            //Get Frame values here
        }
    }
//    public static class ApiaryRecipeLogic extends RecipeLogic {
//        public ApiaryRecipeLogic(ApiaryMachine machine){
//            super(machine);
//        }
//    }
}

/*  TODO: Create logic for this such that it reads the NBT of the bees in each hatch.
    TODO: Will need to implement something similar to Forestry's BeekeepingLogic class, probably
    TODO: OnFormed() and call a class that implements Breeding and products.
    TODO: Will need to implement a frame holder s.t. it can modify the results/run time
    TODO: RecipeLogic might not be necessary?
    On working, decrement health. If health goes below 0, kill and spawn drones
    Health decrement wont decrease with voltage, affected by genome+frames
    Production rate should increase with voltage+genome+frames
    Need to figure out how to not cause lag with checking each container
    I'm just gonna push this and someone else can look at it
    [BeeHolder]->[Get Queen/Princess] -> [if Princess, get drones, breed] -> [Produce]
        ->[Age]->[Write NBT] -> [Kill+spawn drones if age <= 0f]

*/
