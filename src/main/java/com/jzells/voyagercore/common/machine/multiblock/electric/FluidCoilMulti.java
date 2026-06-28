package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraftforge.fluids.FluidStack;

import com.jzells.voyagercore.common.data.VoyagerMaterials;
import org.jetbrains.annotations.NotNull;

import static com.jzells.voyagercore.VoyagerCore.LOGGER;

public class FluidCoilMulti extends CoilWorkableElectricMultiblockMachine {

    private int runningTimer = 0;
    private FluidStack requiredFluid;
    // private int fluidAmountPerInterval = 0;
    private int fluidConsumeInterval = 20;
    // private TickableSubscription tickSub;
    // private boolean canRecipeRun = false;

    public final FluidStack CHLORINE_STACK = GTMaterials.Chlorine.getFluid(200);
    private final FluidStack PYROTHEUM_STACK = VoyagerMaterials.Pyrotheum.getFluid(10);

    public FluidCoilMulti(IMachineBlockEntity holder) {
        super(holder);
        this.requiredFluid = PYROTHEUM_STACK;
    }

    public void setRequiredFluid(FluidStack fluid) {
        // LOGGER.info("This is: {}", this.getHolder().getDefinition().getName());
        // LOGGER.info("FluidStack given: {}", fluid.getDisplayName().getString());
        this.requiredFluid = fluid;
    }

    protected GTRecipe getFluidConsumptionRecipe() {
        return GTRecipeBuilder.ofRaw()
                .inputFluids(this.requiredFluid)
                .buildRawRecipe();
    }

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof FluidCoilMulti fluidMachine)) {
            return RecipeModifier.nullWrongType(FluidCoilMulti.class, machine);
        }
        if (RecipeHelper.matchRecipe(fluidMachine, fluidMachine.getFluidConsumptionRecipe()).isSuccess()) {
            LOGGER.info("Machine is: {}", fluidMachine.getDefinition().getName());
            LOGGER.info("Fluid of Machine is: {}", fluidMachine.requiredFluid.getDisplayName().getString());
            return ModifierFunction.IDENTITY;
        }
        return ModifierFunction.NULL;
    }

    @Override
    public boolean onWorking() {
        boolean val = super.onWorking();

        if (runningTimer % fluidConsumeInterval == 0) {
            var fluidRecipe = this.getFluidConsumptionRecipe();
            // LOGGER.info("Required Fluid:");
            LOGGER.info("Required Fluid:{}", this.requiredFluid.getDisplayName().getString());
            if (!RecipeHelper
                    .handleRecipeIO(this, fluidRecipe, IO.IN, this.recipeLogic.getChanceCaches())
                    .isSuccess()) {
                recipeLogic.interruptRecipe();
                return false;
            }
        }
        runningTimer++;
        // runningTimer %= fluidConsumeInterval;

        return val;
    }

    public void setFluidConsumption(FluidStack fluid, int amount, int intervalTicks) {
        this.requiredFluid = fluid.copy();
        // this.fluidAmountPerInterval = amount;
        this.fluidConsumeInterval = Math.max(1, intervalTicks);
    }
}
