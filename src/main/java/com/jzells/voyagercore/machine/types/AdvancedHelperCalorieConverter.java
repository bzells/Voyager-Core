package com.jzells.voyagercore.machine.types;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraftforge.fluids.FluidStack;

public class AdvancedHelperCalorieConverter extends WorkableElectricMultiblockMachine implements ITieredMachine {

    public AdvancedHelperCalorieConverter(IMachineBlockEntity holder, int tier) {
        super(holder, new Object[0]);
        this.tier = tier;
    }

    private static final FluidStack DISTILLED_WATER_STACK;
    private static final FluidStack MILK_STACK;
    private static final FluidStack BLUE_HELPERADE_STACK;

    private int runningTimer = 0;
    private boolean milkBoosted = false;

    @Override
    public boolean onWorking() {
        boolean val = super.onWorking();
        if (this.runningTimer % 72 == 0 && !RecipeHelper
                .handleRecipeIO(this, this.getMilkRecipe(), IO.IN, this.recipeLogic.getChanceCaches()).isSuccess()) {
            this.recipeLogic.interruptRecipe();
            return false;
        } else {
            GTRecipe boosterRecipe = this.getMilkRecipe();
            this.milkBoosted = RecipeHelper.matchRecipe(this, boosterRecipe).isSuccess() && RecipeHelper
                    .handleRecipeIO(this, boosterRecipe, IO.IN, this.recipeLogic.getChanceCaches()).isSuccess();

            ++this.runningTimer;
            if (this.runningTimer > 72000) {
                this.runningTimer %= 72000;
            }

            return val;
        }
    }

    protected GTRecipe getMilkRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(MILK_STACK).buildRawRecipe();
    }

    protected double getProductionBoost() {
        if (!this.milkBoosted) {
            return 1;
        } else {
            return 1.5;
        }
    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof AdvancedHelperCalorieConverter calorieConverter) {
            EnergyStack EUt = recipe.getOutputEUt();
            if (!EUt.isEmpty() &&
                    RecipeHelper.matchRecipe(calorieConverter, calorieConverter.getMilkRecipe()).isSuccess()) {
                double eutMultiplier = calorieConverter.getProductionBoost();
                return ModifierFunction.builder()
                        .eutMultiplier(eutMultiplier)
                        .build();
            } else {
                return ModifierFunction.NULL;
            }
        } else {
            return RecipeModifier.nullWrongType(AdvancedHelperCalorieConverter.class, machine);
        }
    }

    static {
        DISTILLED_WATER_STACK = GTMaterials.DistilledWater.getFluid(1);
        MILK_STACK = GTMaterials.Milk.getFluid(1);
        BLUE_HELPERADE_STACK = GTMaterials.Ammonia.getFluid(1);
    }
}
