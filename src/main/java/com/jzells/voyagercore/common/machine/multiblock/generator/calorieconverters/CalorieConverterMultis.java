package com.jzells.voyagercore.common.machine.multiblock.generator.calorieconverters;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;
import static com.jzells.voyagercore.common.data.VoyagerBlocks.*;

public class CalorieConverterMultis {

    public static final MultiblockMachineDefinition HYPER_HELPER_CALORIE_CONVERTER = VOYAGERCORE_REGISTRATE
            .multiblock("hyper_helper_calorie_converter", AdvancedHelperCalorieConverterType::new)
            .rotationState(RotationState.ALL)
            .recipeTypes(VoyagerRecipeTypes.ADVANCED_CALORIE_CONVERSION)
            .recipeModifier(AdvancedHelperCalorieConverterType::recipeModifier)
            .langValue("Hyper Helper Calorie Converter (HHCC)")
            .generator(true)
            .appearanceBlock(CASING_RADIANT_TITANEX)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("CCC", "CDC", "CCC")
                    .aisle("CCC", "CBC", "CCC")
                    .aisle("CCC", "CBC", "CCC")
                    .aisle("III", "I@I", "III")
                    .where('@', Predicates.controller(Predicates.blocks(def.get())))
                    .where('C',
                            Predicates
                                    .blocks(CASING_RADIANT_TITANEX.get())
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
                    .where('B', Predicates.blocks(COOLING_LAMP.get()))
                    .where('I',
                            Predicates.blocks(CASING_VENT_RADIANT_TITANEX.get()))
                    .where('D', Predicates.abilities(PartAbility.OUTPUT_ENERGY))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/radiant_titanex_casing"),
                    VoyagerCore.id("block/multiblock/hyper_helper_calorie_converter"))
            .register();

    public static void init() {}
}
