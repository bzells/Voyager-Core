package com.jzells.voyagercore.machine.types;

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

import org.jetbrains.annotations.NotNull;

public class FluidCoilMulti extends CoilWorkableElectricMultiblockMachine {

    private int runningTimer = 0;
    private FluidStack requiredFluid;
    // private int fluidAmountPerInterval = 0;
    private int fluidConsumeInterval = 20;
    // private TickableSubscription tickSub;
    // private boolean canRecipeRun = false;
    private final FluidStack CHLORINE_STACK = GTMaterials.Water.getFluid(200);

    public FluidCoilMulti(IMachineBlockEntity holder, FluidStack requiredFluid) {
        super(holder);
        this.requiredFluid = requiredFluid;
    }

    protected GTRecipe getFluidConsumptionRecipe() {
        return GTRecipeBuilder.ofRaw()
                .inputFluids(CHLORINE_STACK)
                .buildRawRecipe();
    }

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof FluidCoilMulti fluidMachine)) {
            return RecipeModifier.nullWrongType(FluidCoilMulti.class, machine);
        }
        if (RecipeHelper.matchRecipe(fluidMachine, fluidMachine.getFluidConsumptionRecipe()).isSuccess()) {
            return ModifierFunction.IDENTITY;
        }
        return ModifierFunction.NULL;
    }

    @Override
    public boolean onWorking() {
        boolean val = super.onWorking();

        if (runningTimer % fluidConsumeInterval == 0) {
            if (!RecipeHelper
                    .handleRecipeIO(this, getFluidConsumptionRecipe(), IO.IN, this.recipeLogic.getChanceCaches())
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
