package com.jzells.voyagercore.machine.types;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;

public class AdvancedHelperCalorieConverterType extends WorkableElectricMultiblockMachine {

    public AdvancedHelperCalorieConverterType(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    // public AdvancedHelperCalorieConverterType(IMachineBlockEntity holder, Object... args) {
    // super(holder, args);
    // }
    //
    // private static final FluidStack DISTILLED_WATER_STACK;
    // private static final FluidStack MILK_STACK;
    // private static final FluidStack BLUE_HELPERADE_STACK;
    //
    // private int runningTimer = 0;
    // private boolean milkBoosted = false;
    //
    // @Override
    // public boolean onWorking() {
    // boolean val = super.onWorking();
    // if (this.runningTimer % 72 == 0 && !RecipeHelper
    // .handleRecipeIO(this, this.getMilkRecipe(), IO.IN, this.recipeLogic.getChanceCaches()).isSuccess()) {
    // this.recipeLogic.interruptRecipe();
    // return false;
    // } else {
    // GTRecipe boosterRecipe = this.getMilkRecipe();
    // this.milkBoosted = RecipeHelper.matchRecipe(this, boosterRecipe).isSuccess() && RecipeHelper
    // .handleRecipeIO(this, boosterRecipe, IO.IN, this.recipeLogic.getChanceCaches()).isSuccess();
    //
    // ++this.runningTimer;
    // if (this.runningTimer > 72000) {
    // this.runningTimer %= 72000;
    // }
    //
    // return val;
    // }
    // }
    //
    // protected GTRecipe getMilkRecipe() {
    // return GTRecipeBuilder.ofRaw().inputFluids(MILK_STACK).buildRawRecipe();
    // }
    //
    // protected double getProductionBoost() {
    // if (!this.milkBoosted) {
    // return 1;
    // } else {
    // return 1.5;
    // }
    // }
    //
    // public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
    // if (machine instanceof AdvancedHelperCalorieConverterType calorieConverter) {
    // EnergyStack EUt = recipe.getOutputEUt();
    // if (!EUt.isEmpty() &&
    // RecipeHelper.matchRecipe(calorieConverter, calorieConverter.getMilkRecipe()).isSuccess()) {
    // double eutMultiplier = calorieConverter.getProductionBoost();
    // return ModifierFunction.builder()
    // .eutMultiplier(eutMultiplier)
    // .build();
    // } else {
    // return ModifierFunction.NULL;
    // }
    // } else {
    // return RecipeModifier.nullWrongType(AdvancedHelperCalorieConverterType.class, machine);
    // }
    // }
    //
    // static {
    // DISTILLED_WATER_STACK = GTMaterials.DistilledWater.getFluid(1);
    // MILK_STACK = GTMaterials.Milk.getFluid(1);
    // BLUE_HELPERADE_STACK = GTMaterials.Ammonia.getFluid(1);
    // }
}
