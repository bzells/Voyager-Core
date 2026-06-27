package com.jzells.voyagercore.machine.multis;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;

import com.jzells.voyagercore.recipe.types.VoyagerRecipeTypes;
import com.jzells.voyagercore.util.VoyagerKJSIntegration;
import dev.latvian.mods.kubejs.KubeJS;

import static com.jzells.voyagercore.VoyagerCore.VOYAGER_REGISTRATE;

public class HyperHelperCalorieConverter {

    // public static final MultiblockMachineDefinition HYPER_HELPER_CALORIE_CONVERTER = VOYAGER_REGISTRATE
    // .multiblock("test", WorkableElectricMultiblockMachine::new)
    // .rotationState(RotationState.ALL)
    // .recipeTypes(VoyagerRecipeTypes.ADVANCED_CALORIE_CONVERSION)
    // .generator(true)
    // .recipeModifiers()
    // .appearanceBlock(GCYMBlocks.CASING_ATOMIC)
    // .pattern(definition -> FactoryBlockPattern.start()
    // .aisle("CCC", "CDC", "CCC")
    // .aisle("CCC", "CBC", "CCC")
    // .aisle("CCC", "CBC", "CCC")
    // .aisle("III", "I@I", "III")
    // .where('@', Predicates.controller(Predicates.blocks(definition.get())))
    // // .where('C',
    // // Predicates.blocks(VoyagerKJSIntegration.getBlockFromKubeJSRegistry("radiant_titanex"))
    // .where('C', Predicates.blocks(GCYMBlocks.CASING_ATOMIC.get())
    // .setMinGlobalLimited(5)
    // .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setExactLimit(1).setPreviewCount(1))
    // .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setExactLimit(1).setPreviewCount(1))
    // .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
    // .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
    // .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
    // // .where('B', Predicates.blocks(VoyagerKJSIntegration.getBlockFromKubeJSRegistry("cooling_lamp")))
    // .where('B', Predicates.blocks(GCYMBlocks.CASING_ATOMIC.get()))
    // // .where('I',
    // // Predicates.blocks(VoyagerKJSIntegration.getBlockFromKubeJSRegistry("radiant_titanex_vent")))
    // .where('I', Predicates.blocks(GCYMBlocks.CASING_ATOMIC.get()))
    // .where('D', Predicates.abilities(PartAbility.IMPORT_ITEMS))
    // .build())
    // // .workableCasingModel(KubeJS.id("block/casing/radiant_titanex_casing"),
    // // KubeJS.id("block/multiblock/hyper_helper_calorie_converter"))
    // .register();

    public static final MultiblockMachineDefinition TEST = VOYAGER_REGISTRATE
            .multiblock("hyper_helper_calorie_converter", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeTypes(VoyagerRecipeTypes.ADVANCED_CALORIE_CONVERSION)
            // .recipeType()
            // .recipeTypes
            // (GTRecipeTypes.BENDER_RECIPES)
            .langValue("Hyper Helper Calorie Converter (HHCC)")
            .generator(true)
            // .recipeModifiers(GTRecipeModifiers.OC_NON_PERFECT)
            .appearanceBlock(() -> VoyagerKJSIntegration.getBlockFromKubeJSRegistry(("radiant_titanex_casing")))
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("CCC", "CDC", "CCC")
                    .aisle("CCC", "CBC", "CCC")
                    .aisle("CCC", "CBC", "CCC")
                    .aisle("III", "I@I", "III")
                    .where('@', Predicates.controller(Predicates.blocks(def.get())))
                    .where('C',
                            Predicates
                                    .blocks(VoyagerKJSIntegration.getBlockFromKubeJSRegistry("radiant_titanex_casing"))
                                    // .where('C', Predicates.blocks(GCYMBlocks.CASING_ATOMIC.get())
                                    .setMinGlobalLimited(5)
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setExactLimit(1)
                                            .setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setExactLimit(1)
                                            .setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1)
                                            .setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setExactLimit(1)
                                            .setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where('B', Predicates.blocks(VoyagerKJSIntegration.getBlockFromKubeJSRegistry("cooling_lamp")))
                    .where('I',
                            Predicates.blocks(
                                    VoyagerKJSIntegration.getBlockFromKubeJSRegistry("radiant_titanex_vent_casing")))
                    .where('D', Predicates.abilities(PartAbility.OUTPUT_ENERGY))
                    .build())
            .workableCasingModel(KubeJS.id("block/casing/radiant_titanex_casing"),
                    KubeJS.id("block/multiblock/hyper_helper_calorie_converter"))
            .register();

    public static void init() {}
}
