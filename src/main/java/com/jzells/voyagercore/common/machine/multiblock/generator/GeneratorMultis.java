package com.jzells.voyagercore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.*;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerCoreRecipeModifiers;
import com.jzells.voyagercore.common.data.VoyagerMaterials;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import com.jzells.voyagercore.common.machine.multiblock.generator.calorieconverters.AdvancedHelperCalorieConverterType;
import com.jzells.voyagercore.common.machine.multiblock.generator.electric.MultiTurbineMachine;
import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;
import static com.jzells.voyagercore.common.data.VoyagerBlocks.*;

public class GeneratorMultis {

    // spotless:off
    // spotless is disabled in this file
    public static final MultiblockMachineDefinition HYPER_HELPER_CALORIE_CONVERTER = VOYAGERCORE_REGISTRATE
            .multiblock("hyper_helper_calorie_converter", holder -> new AdvancedHelperCalorieConverterType(holder, 2))
            .rotationState(RotationState.ALL)
            .recipeTypes(VoyagerRecipeTypes.ADVANCED_CALORIE_CONVERSION)
            .recipeModifiers(AdvancedHelperCalorieConverterType::recipeModifier, VoyagerCoreRecipeModifiers.PARAMOUNT_HELPER_REQUIRE, GTRecipeModifiers.OC_NON_PERFECT_SUBTICK)
            .langValue("Hyper Helper Calorie Converter (HHCC)")
            .generator(true)
            .appearanceBlock(CASING_VENT_RADIANT_TITANEX)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb", "cbddbbbbbbbddbc", "efddfbbbbbfddfe", "cbddbbbbbbbddbc", "bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb")
                    .aisle("bbbbbbbbbbbbbbb", "cfddbbbbbbbddfc", "cbddbbbbbbbddbc", "bbbgcbbbbbcgbbb", "bbbgcfbbbfcgbbb", "bbbgcbbbbbcgbbb", "cbddbbbbbbbddbc", "cfddbbbbbbbddfc", "bbbbbbbbbbbbbbb")
                    .aisle("bbbbbbbbbbbbbbb", "cbddbbbbbbbddbc", "bbbgcbbbbbcgbbb", "bbbgbdddddbgbbb", "bbbgbggLggbgbbb", "bbbgbdddddbgbbb", "bbbgcbbbbbcgbbb", "cbddbbbbbbbddbc", "bbbbbbbbbbbbbbb")
                    .aisle("cbddbbbbbbbddbc", "bbbgcbbbbbcgbbb", "bbbgbdddddbgbbb", "bbbgeebbbeegbbb", "bbbghhhhhhhgbbb", "bbbgeebbbeegbbb", "bbbgbdddddbgbbb", "bbbgcbbbbbcgbbb", "cbddbbbbbbbddbc")
                    .aisle("efddfbbbbbfddfe", "bbbgcfbbbfcgbbb", "bbbgbgggggbgbbb", "bbbghhhhhhhgbbb", "bbbgbbbbbbbgbbb", "bbbghhhhhhhgbbb", "bbbgbgggggbgbbb", "bbbgcfbbbfcgbbb", "efddfbbbbbfddfe")
                    .aisle("cbddbbbbbbbddbc", "bbbgcbbbbbcgbbb", "bbbgbdddddbgbbb", "bbbgeebbbeegbbb", "bbbghhhhhhhgbbb", "bbbgeebbbeegbbb", "bbbgbdddddbgbbb", "bbbgcbbbbbcgbbb", "cbddbbbbbbbddbc")
                    .aisle("bbbbbbbbbbbbbbb", "cbddbbbbbbbddbc", "bbbgcbbbbbcgbbb", "bbbgbdddddbgbbb", "bbbgbgg@ggbgbbb", "bbbgbdddddbgbbb", "bbbgcbbbbbcgbbb", "cbddbbbbbbbddbc", "bbbbbbbbbbbbbbb")
                    .aisle("bbbbbbbbbbbbbbb", "cfddbbbbbbbddfc", "cbddbbbbbbbddbc", "bbbgcbbbbbcgbbb", "bbbgcfbbbfcgbbb", "bbbgcbbbbbcgbbb", "cbddbbbbbbbddbc", "cfddbbbbbbbddfc", "bbbbbbbbbbbbbbb")
                    .aisle("bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb", "cbddbbbbbbbddbc", "efddfbbbbbfddfe", "cbddbbbbbbbddbc", "bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb")

                    .where("b", Predicates.any())
                    .where("c", Predicates.blocks(GCYMBlocks.CASING_VIBRATION_SAFE.get()))
                    .where("d", Predicates.blocks(CASING_RADIANT_TITANEX.get()))
                    .where("e", Predicates.blocks(COOLING_LAMP.get()))
                    .where("f", Predicates.blocks(VoyagerVoltageTierUtils.getFrameBlock(GTMaterials.IncoloyMA956).get()))
                    .where("g", Predicates.blocks(CASING_VENT_RADIANT_TITANEX.get())
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.OUTPUT_ENERGY).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                    )
                    .where("h", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where('@', Predicates.controller(Predicates.blocks(def.get())))
                    .where('L', Predicates.abilities(VoyagerPartAbilities.HELPER_HOLDER))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/radiant_titanex_vent_casing"),
                    VoyagerCore.id("block/multiblock/hyper_helper_calorie_converter"))
            .register();

    public static final MultiblockMachineDefinition XL_TURBIBNE = VOYAGERCORE_REGISTRATE
            .multiblock("xl_turbine", holder -> new MultiTurbineMachine(holder, IV))
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.GAS_TURBINE_FUELS)
            .generator(true)
            .recipeModifier(MultiTurbineMachine::recipeModifier)
            .appearanceBlock(CASING_OSTRUM)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("CCCCCCC", "CCCDCCC", "CRCCCRC", "CCCCCCC", "CCCCCCC")
                    .aisle("CCCCCCC", "CTTTTTC", "CFFFFFC", "CTTTTTC", "CCCCCCC")
                    .aisle("CCCCCCC", "CTTTTTC", "CFFFFFC", "CTTTTTC", "CCCCCCC")
                    .aisle("CCCCCCC", "CTTTTTC", "CFFFFFC", "CTTTTTC", "CCCCCCC")
                    .aisle("CCCCCCC", "CCCCCCC", "CRC@CRC", "CCCCCCC", "CCCCCCC")
                    .where('@', Predicates.controller(Predicates.blocks(def.get())))
                    .where('C', Predicates.blocks(CASING_OSTRUM.get()).setMinGlobalLimited(5)
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where('T', Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where('F', Predicates.frames(VoyagerMaterials.Aluminex202a))
                    .where("R", Predicates.ability(PartAbility.ROTOR_HOLDER).setExactLimit(4))
                    .where('D', Predicates.abilities(PartAbility.OUTPUT_ENERGY))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/ostrum_casing"),
                    VoyagerCore.id("block/multiblock/advanced_gas_turbine"))
            .register();




    public static final MultiblockMachineDefinition HELPER_CALORIE_CONVERTER = VOYAGERCORE_REGISTRATE
            .multiblock("helper_calorie_converter", holder -> new AdvancedHelperCalorieConverterType(holder, 1))
            .rotationState(RotationState.ALL)
            .recipeTypes(VoyagerRecipeTypes.ADVANCED_CALORIE_CONVERSION)
            .recipeModifiers(AdvancedHelperCalorieConverterType::recipeModifier, VoyagerCoreRecipeModifiers.PARAMOUNT_HELPER_REQUIRE, GTRecipeModifiers.OC_NON_PERFECT_SUBTICK)
            .langValue("Helper Calorie Converter (HCC)")
            .generator(true)
            .appearanceBlock(GCYMBlocks.HEAT_VENT)
            .pattern(def -> FactoryBlockPattern.start()
                .aisle("aaaaaaaaaaaaa", "baccaaaaaccab", "bdccdaaadccdb", "baccaaaaaccab", "aaaaaaaaaaaaa")
                .aisle("baccaaaaaccab", "aaaebaaabeaaa", "aaaebcecbeaaa", "aaaebaaabeaaa", "baccaaaaaccab")
                .aisle("bdccdaaadccdb", "aaaebcecbeaaa", "aaaebgggbeaaa", "aaaebcecbeaaa", "bdccdaaadccdb")
                .aisle("baccaaaaaccab", "aaaebaaabeaaa", "aaaebcfcbeaaa", "aaaebaaabeaaa", "baccaaaaaccab")
                .aisle("aaaaaaaaaaaaa", "baccaaaaaccab", "bdccdaaadccdb", "baccaaaaaccab", "aaaaaaaaaaaaa")

                .where("a", Predicates.any())
                            .where("b", Predicates.blocks(CASING_CONDENSATION_RESISTANT_TUNGSTEN.get()))
                            .where("c", Predicates.blocks(CASING_LEAD.get()))
                            .where("d", Predicates.blocks(VoyagerVoltageTierUtils.getFrameBlock(GTMaterials.TungstenCarbide).get()))
                            .where("e", Predicates.blocks(GCYMBlocks.HEAT_VENT.get())
                                .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                                .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                                .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                                .or(Predicates.abilities(PartAbility.OUTPUT_ENERGY).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                                .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(4,1).setMinGlobalLimited(1))
                            )
                            .where("f", Predicates.controller(Predicates.blocks(def.get())))
                            .where("g", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                    .build())
            .workableCasingModel(GTCEu.id("block/heat_vent"),
                    VoyagerCore.id("block/multiblock/hyper_helper_calorie_converter"))
            .register();









    public static void init() {}
    //spotless:on
}
