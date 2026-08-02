package com.jzells.voyagercore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerPredicates;
import com.jzells.voyagercore.common.machine.multiblock.electric.ModularMachine;
import com.jzells.voyagercore.common.machine.multiblock.electric.ModuleInstanceMachine;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;
import static com.jzells.voyagercore.common.data.VoyagerBlocks.CASING_INDUSTRIAL_SOLID;

public class VoyagerTestMultis {

    public static final MultiblockMachineDefinition TEST_MODULE = VOYAGERCORE_REGISTRATE
            .multiblock("test_module", ModuleInstanceMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .appearanceBlock(CASING_INDUSTRIAL_SOLID)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("XCX")
                    .where('X', Predicates.blocks(CASING_INDUSTRIAL_SOLID.get()))
                    .where('C', Predicates.controller(Predicates.blocks(def.get())))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/solid_industrial_casing"),
                    VoyagerCore.id("block/multiblock/helper_coiltronics_assembly"))
            .register();

    public static final MultiblockMachineDefinition TEST_NOT_MODULE = VOYAGERCORE_REGISTRATE
            .multiblock("test_not_module", ModuleInstanceMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .appearanceBlock(CASING_INDUSTRIAL_SOLID)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("XCX")
                    .where('X', Predicates.blocks(CASING_INDUSTRIAL_SOLID.get()))
                    .where('C', Predicates.controller(Predicates.blocks(def.get())))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/solid_industrial_casing"),
                    VoyagerCore.id("block/multiblock/helper_coiltronics_assembly"))
            .register();

    public static final MultiblockMachineDefinition TEST_MODULAR = VOYAGERCORE_REGISTRATE
            .multiblock("test_modular_multi", ModularMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .appearanceBlock(CASING_INDUSTRIAL_SOLID)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("   ", " M ", "   ")
                    .aisle("XXX", "XXX", "XXX")
                    .aisle("XXX", "XCX", "XXX")
                    .where('X', Predicates.blocks(CASING_INDUSTRIAL_SOLID.get()))
                    .where(' ', Predicates.any())
                    .where('M', VoyagerPredicates.module(TEST_MODULE))
                    .where('C', Predicates.controller(Predicates.blocks(def.get())))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/solid_industrial_casing"),
                    VoyagerCore.id("block/multiblock/helper_coiltronics_assembly"))
            .register();
}
