package com.jzells.voyagercore.common.machine.multiblock.generator.calorieconverters;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraftforge.fluids.FluidStack;

public class AdvancedHelperCalorieConverterType extends WorkableElectricMultiblockMachine {

    public AdvancedHelperCalorieConverterType(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    private static final FluidStack DISTILLED_WATER_STACK;
    private static final FluidStack MILK_STACK;
    private static final FluidStack BLUE_HELPERADE_STACK;

    private boolean dt_boosted = false;
    private boolean milk_boosted = false;
    private boolean helperade_boosted = false;

    private int refresh = 100;

    private int runningTimer = 0;

    @Override
    public boolean onWorking() {
        boolean val = super.onWorking();

        if (!hasFluidInputs() && this.runningTimer % refresh == 0) {
            this.recipeLogic.interruptRecipe();
            return false;
        } else {
            GTRecipe getDTR = this.getDTRecipe();
            GTRecipe getMR = this.getMilkRecipe();
            GTRecipe getHPR = this.getHelperadeRecipe();

            this.dt_boosted = RecipeHelper.matchRecipe(this, getDTR).isSuccess() &&
                    RecipeHelper.handleRecipeIO(this, getDTR, IO.IN, this.recipeLogic.getChanceCaches()).isSuccess();
            this.milk_boosted = RecipeHelper.matchRecipe(this, getMR).isSuccess() &&
                    RecipeHelper.handleRecipeIO(this, getMR, IO.IN, this.recipeLogic.getChanceCaches()).isSuccess();
            this.helperade_boosted = RecipeHelper.matchRecipe(this, getHPR).isSuccess() &&
                    RecipeHelper.handleRecipeIO(this, getHPR, IO.IN, this.recipeLogic.getChanceCaches()).isSuccess();

            ++this.runningTimer;
            if (this.runningTimer > refresh) {
                this.runningTimer %= refresh;
                // add +1 to this prob to prevent double
            }

            return val;

        }
    }

    protected GTRecipe getMilkRecipe() {
        if (this.runningTimer % refresh == 0) {
            return GTRecipeBuilder.ofRaw().inputFluids(MILK_STACK).buildRawRecipe();
        }
        return GTRecipeBuilder.ofRaw().buildRawRecipe();
    }

    protected GTRecipe getDTRecipe() {
        if (this.runningTimer % refresh == 0) {
            return GTRecipeBuilder.ofRaw().inputFluids(DISTILLED_WATER_STACK).buildRawRecipe();
        }
        return GTRecipeBuilder.ofRaw().buildRawRecipe();
    }

    protected GTRecipe getHelperadeRecipe() {
        if (this.runningTimer % refresh == 0) {
            return GTRecipeBuilder.ofRaw().inputFluids(BLUE_HELPERADE_STACK).buildRawRecipe();
        }
        return GTRecipeBuilder.ofRaw().buildRawRecipe();
    }

    protected double getProductionBoost() {
        return 3;
    }

    // public static RecipeModifier ADVANCED_CALORIE_CONVERSION = AdvancedHelperCalorieConverterType::recipeModifier;

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof AdvancedHelperCalorieConverterType calorieConverter) {
            EnergyStack EUt = recipe.getOutputEUt();
            if (!EUt.isEmpty()) {
                if (calorieConverter.hasDTRecipe()) {
                    return ModifierFunction.builder().eutMultiplier(1).build();
                } else if (calorieConverter.hasMilkRecipe()) {
                    return ModifierFunction.builder().eutMultiplier(2).durationMultiplier(1.5).build();
                } else if (calorieConverter.hasHelperadeRecipe()) {
                    return ModifierFunction.builder().eutMultiplier(4).durationMultiplier(4).build();
                } else {
                    return ModifierFunction.builder().eutMultiplier(.5).durationMultiplier(.5).build();
                }
            } else {
                return ModifierFunction.NULL;
            }
        } else {
            return RecipeModifier.nullWrongType(AdvancedHelperCalorieConverterType.class, machine);
        }
    }

    private boolean hasFluidInputs() {
        boolean distilled_water = (RecipeHelper
                .handleRecipeIO(this, this.getDTRecipe(), IO.IN, this.recipeLogic.getChanceCaches()).isSuccess());
        boolean milk = (RecipeHelper
                .handleRecipeIO(this, this.getMilkRecipe(), IO.IN, this.recipeLogic.getChanceCaches()).isSuccess());
        boolean helperade = (RecipeHelper
                .handleRecipeIO(this, this.getHelperadeRecipe(), IO.IN, this.recipeLogic.getChanceCaches())
                .isSuccess());
        return (distilled_water || milk || helperade);
    }

    private boolean hasMilkRecipe() {
        return (RecipeHelper
                .handleRecipeIO(this, this.getMilkRecipe(), IO.IN, this.recipeLogic.getChanceCaches()).isSuccess());
    }

    private boolean hasDTRecipe() {
        return (RecipeHelper
                .handleRecipeIO(this, this.getDTRecipe(), IO.IN, this.recipeLogic.getChanceCaches()).isSuccess());
    }

    private boolean hasHelperadeRecipe() {
        return (RecipeHelper
                .handleRecipeIO(this, this.getHelperadeRecipe(), IO.IN, this.recipeLogic.getChanceCaches())
                .isSuccess());
    }

    static {
        DISTILLED_WATER_STACK = GTMaterials.DistilledWater.getFluid(500);
        MILK_STACK = GTMaterials.Milk.getFluid(175);
        BLUE_HELPERADE_STACK = GTMaterials.Ammonia.getFluid(25);
        // For whatever reason, what the recipe runs, it uses the amount x2...
        // I think it is because refresh % 0 can be 0 at x = refresh, and 0. So it does it twice.
    }
}
