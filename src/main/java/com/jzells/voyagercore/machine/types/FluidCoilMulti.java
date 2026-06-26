package com.jzells.voyagercore.machine.types;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraftforge.fluids.FluidStack;

public class FluidCoilMulti extends CoilWorkableElectricMultiblockMachine {

    private int runningTimer = 0;
    private FluidStack requiredFluid;
    // private int fluidAmountPerInterval = 0;
    private int fluidConsumeInterval = 20;
    // private TickableSubscription tickSub;
    // private boolean canRecipeRun = false;

    public FluidCoilMulti(IMachineBlockEntity holder) {
        super(holder);
        this.requiredFluid = FluidStack.EMPTY;
    }

    @Override
    public boolean onWorking() {
        var val = super.onWorking();

        if (this.runningTimer % fluidConsumeInterval == 0 && !RecipeHelper
                .handleRecipeIO(this, this.getFluidConsumptionRecipe(), IO.IN, this.recipeLogic.getChanceCaches())
                .isSuccess()) {
            this.recipeLogic.interruptRecipe();
            return false;
        }
        ++this.runningTimer;

        this.runningTimer %= fluidConsumeInterval;

        return val;
    }

    protected GTRecipe getFluidConsumptionRecipe() {
        return GTRecipeBuilder.ofRaw()
                .inputFluids(requiredFluid)
                .buildRawRecipe();
    }

    public void setFluidConsumption(FluidStack fluid, int amount, int intervalTicks) {
        this.requiredFluid = fluid.copy();
        // this.fluidAmountPerInterval = amount;
        this.fluidConsumeInterval = Math.max(1, intervalTicks);
    }
}
