package com.jzells.voyagercore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerMaterials;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import com.jzells.voyagercore.common.machine.multiblock.generator.calorieconverters.AdvancedHelperCalorieConverterType;
import com.jzells.voyagercore.common.machine.multiblock.generator.electric.MultiTurbineMachine;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;
import static com.jzells.voyagercore.common.data.VoyagerBlocks.*;

public class GeneratorMultis {

    // spotless:off
    // spotless is disabled in this file
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

    public static final MultiblockMachineDefinition XL_TURBIBNE = VOYAGERCORE_REGISTRATE
            .multiblock("xl_turbine", holder -> new MultiTurbineMachine(holder, GTValues.LuV))
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

    public static void init() {}
    //spotless:on
}
