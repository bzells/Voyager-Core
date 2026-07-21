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

import lombok.val;

public class AdvancedHelperCalorieConverterType extends WorkableElectricMultiblockMachine {

    public AdvancedHelperCalorieConverterType(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    private static final FluidStack DISTILLED_WATER_STACK;
    private static final FluidStack MILK_STACK;
    private static final FluidStack BLUE_HELPERADE_STACK;
    private static final FluidStack WATER_STACK;

    private boolean dt_boosted = false;
    private boolean milk_boosted = false;
    private boolean helperade_boosted = false;
    private boolean not_boosted = true;

    private final int refresh = 100;

    private int state = 0;

    private int runningTimer = 0;

    @Override
    public boolean onWorking() {
        boolean val = super.onWorking();

        GTRecipe milkRecipe = this.getMilkRecipe();
        GTRecipe dtRecipe = this.getDTRecipe();
        GTRecipe helperadeRecipe = this.getHelperadeRecipe();
        GTRecipe waterRecipe = this.getWaterRecipe();

        ++this.runningTimer;
        if (this.runningTimer > refresh) {
            this.runningTimer %= refresh;
        }

        if (this.runningTimer % refresh == 0) {

            if (!hasFluidInputs()) {
                recipeLogic.interruptRecipe();
            }
            this.milk_boosted = RecipeHelper.matchRecipe(this, milkRecipe).isSuccess() &&
                    RecipeHelper.handleRecipeIO(this, milkRecipe, IO.IN, this.recipeLogic.getChanceCaches())
                            .isSuccess();
            this.dt_boosted = RecipeHelper.matchRecipe(this, dtRecipe).isSuccess() &&
                    RecipeHelper.handleRecipeIO(this, dtRecipe, IO.IN, this.recipeLogic.getChanceCaches()).isSuccess();
            this.helperade_boosted = RecipeHelper.matchRecipe(this, helperadeRecipe).isSuccess() && RecipeHelper
                    .handleRecipeIO(this, helperadeRecipe, IO.IN, this.recipeLogic.getChanceCaches()).isSuccess();
            this.not_boosted = RecipeHelper.matchRecipe(this, waterRecipe).isSuccess() && RecipeHelper
                    .handleRecipeIO(this, waterRecipe, IO.IN, this.recipeLogic.getChanceCaches()).isSuccess();
        }

        return val;
    }

    private double getEUtMultiplier() {
        if (milk_boosted) {
            return 2;
        } else if (dt_boosted) {
            return 1;
        } else if (helperade_boosted) {
            return 4;
        } else if (not_boosted) {
            return .5;
        } else {
            return 0;
        }
    }

    private double getDurationModifier() {
        if (milk_boosted) {
            return 1.5;
        } else if (dt_boosted) {
            return 1;
        } else if (helperade_boosted) {
            return 4;
        } else if (not_boosted) {
            return .5;
        } else {
            return 0;
        }
    }

    protected GTRecipe getMilkRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(MILK_STACK).buildRawRecipe();
    }

    protected GTRecipe getDTRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(DISTILLED_WATER_STACK).buildRawRecipe();
    }

    protected GTRecipe getHelperadeRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(BLUE_HELPERADE_STACK).buildRawRecipe();
    }

    protected GTRecipe getWaterRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(WATER_STACK).buildRawRecipe();
    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof AdvancedHelperCalorieConverterType calorieConverter) {
            EnergyStack EUt = recipe.getOutputEUt();
            if (!EUt.isEmpty() && calorieConverter.hasFluidInputs()) {
                return ModifierFunction.builder().eutMultiplier(calorieConverter.getEUtMultiplier())
                        .durationMultiplier(calorieConverter.getDurationModifier()).build();
            } else {
                return ModifierFunction.builder().eutMultiplier(.1).build();
            }
        } else {
            return RecipeModifier.nullWrongType(AdvancedHelperCalorieConverterType.class, machine);
        }
    }

    private boolean hasFluidInputs() {
        return (not_boosted || dt_boosted || milk_boosted || helperade_boosted);
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
        WATER_STACK = GTMaterials.Water.getFluid(5000);
        DISTILLED_WATER_STACK = GTMaterials.DistilledWater.getFluid(1000);
        MILK_STACK = GTMaterials.Milk.getFluid(350);
        BLUE_HELPERADE_STACK = GTMaterials.Ammonia.getFluid(50);

    }
}
