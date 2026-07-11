package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.machine.multiblock.part.BeeHolderPartMachine;
import forestry.api.apiculture.genetics.IBee;
import forestry.api.apiculture.genetics.IBeeSpecies;
import forestry.api.genetics.ILifeStage;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.utils.SpeciesUtil;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static forestry.api.apiculture.genetics.BeeLifeStage.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ApiaryMachine extends WorkableElectricMultiblockMachine {
    private ArrayList<BeeHolderPartMachine> beeHolders;
    private int uptime;
    private NotifiableItemStackHandler outputBus;
    private ArrayList<ItemBusPartMachine> outputBuses;
    private TickableSubscription beeLogicSubscription;

    public ApiaryMachine(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        initializePartLists();
        uptime = 0;
        beeLogicSubscription = subscribeServerTick(this::beeLogic);
    }


    private void initializePartLists(){
        this.beeHolders = new ArrayList<>();
        this.outputBuses = new ArrayList<>();
        for (IMultiPart part : getParts()){
            //Bee Holders
            if (part instanceof BeeHolderPartMachine beeHolder){
                this.beeHolders.add(beeHolder);
                VoyagerCore.LOGGER.info("Found {}", beeHolder.getDefinition().getName());
                continue;
            }
            // Add Frame Holder List Here
            // Output Bus
            if (part instanceof ItemBusPartMachine bus){
                outputBuses.add(bus);
            }
//            var handlerLists = part.getRecipeHandlers();
//            for (var handlerList : handlerLists){
//                var recipeCap = handlerList.getCapability(ItemRecipeCapability.CAP);
//                if (handlerList.getHandlerIO().support(IO.OUT) && !recipeCap.isEmpty()){
//                    outputBuses.add( (NotifiableItemStackHandler) recipeCap.get(0));
//                }
//            }
        }
    }
    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        this.resetState();
    }

    @Override
    public void onPartUnload(){
        super.onPartUnload();
        this.resetState();
    }

    private void resetState(){
        unsubscribe(beeLogicSubscription);
        this.beeHolders = null;
        this.uptime = 0;
        //Other holders need to be set to null here;
    }

    public void queenTick(IBee queen, ItemStack queenStack){

    }

    public void breed(BeeHolderPartMachine machine){

    }

//    private void beeInit(BeeHolderPartMachine machine){
//        if (this.beeQueenList == null){
//            this.beeQueenList = new ArrayList<>();
//        }
//        if (this.beePrincessList == null){
//            this.beePrincessList = new ArrayList<>();
//        }
//
//        var royal = (IBee) IIndividualHandlerItem.getIndividual(machine.getRoyal());
//        if (royal != null){
//            ILifeStage beeAge = SpeciesUtil.BEE_TYPE.get().getLifeStage(machine.getRoyal());
//            if (beeAge == QUEEN){
//                this.beeQueenList.add(royal);
//                this.beeQueenItemStackList.add(machine.getRoyal());
//            }
//            if (beeAge == PRINCESS){
//                this.beePrincessList.add(royal);
//                this.beePrincessItemStackList.add(machine.getRoyal());
//            }
//        }
//    }

    private void beeLogic(){
        this.uptime++;
        VoyagerCore.LOGGER.info("Running!");
        if (!beeHolders.isEmpty() && getOffsetTimer() % 20 == 0 && isFormed()){
            for (BeeHolderPartMachine part : beeHolders) {
                 var royal = (IBee) IIndividualHandlerItem.getIndividual(part.getRoyal());
                 if (royal != null ){
                     ILifeStage beeAge = SpeciesUtil.BEE_TYPE.get().getLifeStage(part.getRoyal());
                     if (beeAge == PRINCESS){
                         if (!part.getDrones().isEmpty()){
                             breed(part);
                             beeAge = QUEEN;
                         }
                     }
                     if (beeAge == QUEEN){
                     }
                 }
            }
        }
        for (BeeHolderPartMachine part : beeHolders){
            if (getOffsetTimer() % 20 == beeHolders.indexOf(part)){
                var royal = (IBee) IIndividualHandlerItem.getIndividual(part.getRoyal());
                if (royal != null ){
                    ILifeStage beeAge = SpeciesUtil.BEE_TYPE.get().getLifeStage(part.getRoyal());
                    if (beeAge == PRINCESS){
                        if (!part.getDrones().isEmpty()){
                            breed(part);
                            beeAge = QUEEN;
                        }
                    }
                    if (beeAge == QUEEN){
                        IBeeSpecies primary = royal.getSpecies();
                        IBeeSpecies secondary = royal.getInactiveSpecies();
                        for (var product : primary.getProducts()){
                            VoyagerCore.LOGGER.info("Attempting Product! {}", product.toString());
                            VoyagerCore.LOGGER.info("Creating Stack: {}",product.createStack().toString());
                            //if (uptime % 20 == 0){
                            VoyagerCore.LOGGER.info("Bus used {}", outputBuses.get(0).getDefinition().getName());
                                outputBuses.get(0).getInventory().handleRecipe(IO.OUT, GTRecipeBuilder.ofRaw().buildRawRecipe(), List.of(Ingredient.of(product.createStack())), false);
                            //}
                        }
                    }
                }
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
