package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.machine.multiblock.part.BeeHolderPartMachine;
import forestry.api.apiculture.genetics.IBee;
import forestry.api.apiculture.genetics.IBeeSpecies;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.ILifeStage;
import forestry.api.genetics.alleles.BeeChromosomes;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.utils.SpeciesUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static forestry.api.apiculture.genetics.BeeLifeStage.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ApiaryMachine extends WorkableElectricMultiblockMachine {

    private ArrayList<BeeHolderPartMachine> beeHolders;
    private int uptime;
    private NotifiableItemStackHandler outputBus;
    private ArrayList<ItemBusPartMachine> outputBuses;
    private TickableSubscription beeLogicSubscription;
    @Getter
    @Setter
    private boolean didWork;
    private final GTRecipe emptyRecipe = GTRecipeBuilder.ofRaw().buildRawRecipe();
    private final GTRecipe powerRecipe = GTRecipeBuilder.ofRaw().EUt(VHA[HV]).duration(20).buildRawRecipe();

    public ApiaryMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        initializePartLists();
        uptime = 0;
        // VoyagerCore.LOGGER.info("Has recipetype: {}",this.getRecipeType().toString());
        // beeLogicSubscription = subscribeServerTick(this::beeLogic);
    }

    @Override
    protected RecipeLogic createRecipeLogic(Object... args) {
        return new ApiaryRecipeLogic(this);
    }

    // Probably should've made the recipe actually work lol

    @Override
    public ApiaryRecipeLogic getRecipeLogic() {
        return (ApiaryRecipeLogic) super.getRecipeLogic();
    }

    private void initializePartLists() {
        this.beeHolders = new ArrayList<>();
        this.outputBuses = new ArrayList<>();
        for (IMultiPart part : getParts()) {
            // Bee Holders
            if (part instanceof BeeHolderPartMachine beeHolder) {
                this.beeHolders.add(beeHolder);
                // VoyagerCore.LOGGER.info("Found {}", beeHolder.getDefinition().getName());
                // continue;
            }
            // Add Frame Holder List Here
            // Output Bus
            // if (part instanceof ItemBusPartMachine bus) {
            // outputBuses.add(bus);
            // }
        }
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        this.resetState();
    }

    @Override
    public void onPartUnload() {
        super.onPartUnload();
        this.resetState();
    }

    private void resetState() {
        // unsubscribe(beeLogicSubscription);
        // beeHolders.forEach(p ->p.setLocked(false));
        this.beeHolders = null;
        this.uptime = 0;
        // Other holders need to be set to null here;
    }

    // @Override
    // public boolean beforeWorking(@Nullable GTRecipe recipe) {
    // if (!super.beforeWorking(recipe)) return false;
    // var t = beeHolders.stream().filter(p ->p.getRoyal() != ItemStack.EMPTY).toList().isEmpty();
    // return !t;
    // }

    @Override
    public boolean onWorking() {
        if (super.onWorking()) {
            this.beeLogic();
            return true;
        } else return false;
    }

    public void queenTick(IBee queen, ItemStack queenStack) {}

    public void breed(BeeHolderPartMachine machine) {}

    // private void beeInit(BeeHolderPartMachine machine){
    // if (this.beeQueenList == null){
    // this.beeQueenList = new ArrayList<>();
    // }
    // if (this.beePrincessList == null){
    // this.beePrincessList = new ArrayList<>();
    // }
    //
    // var royal = (IBee) IIndividualHandlerItem.getIndividual(machine.getRoyal());
    // if (royal != null){
    // ILifeStage beeAge = SpeciesUtil.BEE_TYPE.get().getLifeStage(machine.getRoyal());
    // if (beeAge == QUEEN){
    // this.beeQueenList.add(royal);
    // this.beeQueenItemStackList.add(machine.getRoyal());
    // }
    // if (beeAge == PRINCESS){
    // this.beePrincessList.add(royal);
    // this.beePrincessItemStackList.add(machine.getRoyal());
    // }
    // }
    // }

    private void beeLogic() {
        this.uptime++;
        this.uptime %= 1200;
        VoyagerCore.LOGGER.info("Running!");

        if (!this.isWorkingEnabled()) {
            beeHolders.forEach(part -> part.setLocked(false));
            return;
        }
        var slot = getOffsetTimer() % 20;

        if (slot >= beeHolders.size()) {
            return;
        }

        var part = beeHolders.get((int) slot);
        var royal = (IBee) IIndividualHandlerItem.getIndividual(part.getRoyal());

        if (royal == null) {
            part.setLocked(false);
            return;
        }

        part.setLocked(true);
        ILifeStage beeAge = SpeciesUtil.BEE_TYPE.get().getLifeStage(part.getRoyal());
        if (beeAge == PRINCESS) {
            if (!part.getDrones().isEmpty()) {
                breed(part);
                beeAge = QUEEN;
            }
        }
        if (beeAge == QUEEN) {
            IGenome genome = royal.getGenome();
            IBeeSpecies primary = royal.getSpecies();
            IBeeSpecies secondary = royal.getInactiveSpecies();
            float speed = genome.getActiveValue(BeeChromosomes.SPEED);
            int lifespan = genome.getActiveValue(BeeChromosomes.LIFESPAN);
            // Uptime check here

            List<Ingredient> outputs = new ArrayList<>();
            for (var product : primary.getProducts()) {
                VoyagerCore.LOGGER.info("Attempting Product! {}", product.toString());
                VoyagerCore.LOGGER.info("Creating Stack: {}", product.createStack().toString());
                outputs.add(Ingredient.of(new ItemStack(product.item(), 1)));
            }
            /*
             * TODO Rework this into actually giving it on a random interval?
             * for (var product : secondary.getProducts()){
             * outputs.add(Ingredient.of(product.createStack()));
             * }
             */

            Map<RecipeCapability<?>, List<Content>> outputMap = new IdentityHashMap<>();
            outputMap.computeIfAbsent(ItemRecipeCapability.CAP, c -> new ArrayList<>())
                    .addAll(Arrays.stream(outputs.toArray(Ingredient[]::new))
                            .map(ItemRecipeCapability.CAP::of)
                            .map(o -> new Content(o, 1, 0, 0))
                            .toList());
            RecipeHelper.handleRecipe(this, emptyRecipe, IO.OUT, outputMap, this.recipeLogic.getChanceCaches(), false,
                    false);
            // outputBuses.get(0).getInventory().handleRecipe(IO.OUT,
            // emptyRecipe,
            // List.of(Ingredient.of(product.createStack())), false);
        }
    }

    public class ApiaryRecipeLogic extends RecipeLogic {

        public ApiaryRecipeLogic(ApiaryMachine machine) {
            super(machine);
        }

        @Override
        public void findAndHandleRecipe() {
            lastRecipe = null;
            setupRecipe(powerRecipe);
        }

        @Override
        public void onRecipeFinish() {
            machine.afterWorking();
            setupRecipe(lastRecipe);
            if (suspendAfterFinish) {
                setStatus(Status.SUSPEND);
                suspendAfterFinish = false;
            }
        }
    }
}

/*
 * TODO: Create logic for this such that it reads the NBT of the bees in each hatch.
 * TODO: Will need to implement something similar to Forestry's BeekeepingLogic class, probably
 * TODO: OnFormed() and call a class that implements Breeding and products.
 * TODO: Will need to implement a frame holder s.t. it can modify the results/run time
 * TODO: RecipeLogic might not be necessary?
 * On working, decrement health. If health goes below 0, kill and spawn drones
 * Health decrement wont decrease with voltage, affected by genome+frames
 * Production rate should increase with voltage+genome+frames
 * Need to figure out how to not cause lag with checking each container
 * I'm just gonna push this and someone else can look at it
 * [BeeHolder]->[Get Queen/Princess] -> [if Princess, get drones, breed] -> [Produce]
 * ->[Age]->[Write NBT] -> [Kill+spawn drones if age <= 0f]
 * 
 */
