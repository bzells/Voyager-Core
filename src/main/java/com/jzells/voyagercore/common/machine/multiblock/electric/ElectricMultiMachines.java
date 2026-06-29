package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.*;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerCoreRecipeModifiers;
import com.jzells.voyagercore.common.data.VoyagerMaterials;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;

import java.util.Objects;

import static com.gregtechceu.gtceu.api.pattern.Predicates.any;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;
import static com.jzells.voyagercore.common.data.VoyagerBlocks.*;

@SuppressWarnings("removal")

public class ElectricMultiMachines {

    // Multiblocks
    public static final MultiblockMachineDefinition MAGMATIC_FOUNDRY = VOYAGERCORE_REGISTRATE
            .multiblock("magmatic_foundry",
                    (holder) -> new FluidCoilMulti(holder, VoyagerMaterials.Pyrotheum.getFluid(10)))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.BLAST_RECIPES)
            .recipeModifiers(FluidCoilMulti::recipeModifier, VoyagerCoreRecipeModifiers.HEAT_BOOSTING,
                    GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(CASING_FOUNDRY)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("aaaaa", "cdddc", "cdddc", "efefe", "cdddc", "cdddc", "aaaaa")
                    .aisle("aaaaa", "dgggd", "dhhhd", "fhhhf", "dhhhd", "dgggd", "aaaaa")
                    .aisle("aaaaa", "dgdgd", "dhdhd", "ehdhe", "dhdhd", "dgdgd", "aaaaa")
                    .aisle("aaaaa", "dgggd", "dhhhd", "fhhhf", "dhhhd", "dgggd", "aaaaa")
                    .aisle("aa@aa", "cdddc", "cdddc", "efefe", "cdddc", "cdddc", "aaaaa")

                    .where("a",
                            Predicates.blocks(CASING_FOUNDRY.get())
                                    .setMinGlobalLimited(35)
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where("c",
                            Predicates.blocks(GTMaterialBlocks.MATERIAL_BLOCKS
                                    .get(TagPrefix.frameGt, GTMaterials.Tungsten).get()))
                    .where("d", any())
                    .where("e", Predicates.blocks(GTBlocks.FIREBOX_STEEL.get()))
                    .where("f", Predicates.blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get()))
                    .where("g", Predicates.blocks(GCYMBlocks.HEAT_VENT.get()))
                    .where("h", Predicates.heatingCoils())
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/foundry_casing"),
                    VoyagerCore.id("block/multiblock/magmatic_foundry"))
            .register();

    public static final MultiblockMachineDefinition EVERFROST_CHILLER = VOYAGERCORE_REGISTRATE
            .multiblock("everfrost_chiller",
                    (holder) -> new FluidBasicMulti(holder, VoyagerMaterials.Cryotheum.getFluid(10)))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.VACUUM_RECIPES)
            .recipeModifiers(FluidBasicMulti::recipeModifier, VoyagerCoreRecipeModifiers.BASIC_BOOSTING,
                    GTRecipeModifiers.OC_NON_PERFECT)
            .appearanceBlock(CASING_FROST_CONDUCTING)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("aaaaa", "bcccb", "bcccb", "deded", "bcccb", "bcccb", "aaaaa")
                    .aisle("aaaaa", "cfffc", "cgggc", "eggge", "cgggc", "cfffc", "aaaaa")
                    .aisle("aaaaa", "cfifc", "cgigc", "dgigd", "cgigc", "cfcfc", "aaaaa")
                    .aisle("aaaaa", "cfffc", "cgggc", "eggge", "cgggc", "cfffc", "aaaaa")
                    .aisle("aa@aa", "bcccb", "bcccb", "deded", "bcccb", "bcccb", "aaaaa")
                    .where("a", Predicates.blocks(CASING_FROST_CONDUCTING.get()).setMinGlobalLimited(35)
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1)))
                    .where("b", Predicates.blocks(Objects.requireNonNull(GTMaterialBlocks.MATERIAL_BLOCKS
                            .get(TagPrefix.frameGt, GTMaterials.Tungsten)).get()))
                    .where("c", any())
                    .where("d", Predicates.blocks(GTBlocks.FIREBOX_STEEL.get()))
                    .where("e", Predicates.blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get()))
                    .where("f", Predicates.blocks(GCYMBlocks.HEAT_VENT.get()))
                    .where("g", Predicates.blocks(CASING_ALUMINIUM_FROSTPROOF.get()))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where("i", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/frost_conducting_casing"),
                    VoyagerCore.id("block/multiblock/everfrost_chiller"))
            .register();

    public static final MultiblockMachineDefinition CHEMICAL_PLANT = VOYAGERCORE_REGISTRATE
            .multiblock("chemical_plant",
                    ChemicalPlantMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.CHEMICAL_RECIPES, GTRecipeTypes.LARGE_CHEMICAL_RECIPES,
                    VoyagerRecipeTypes.CHEMICAL_PLANT)
            .recipeModifiers(VoyagerCoreRecipeModifiers.HEAT_BOOSTING,
                    GTRecipeModifiers.OC_PERFECT, ChemicalPlantMachine::recipeModifier)
            .appearanceBlock(CASING_CHEM_PLANT)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("aaaaa", "cdddc", "cdddc", "cdddc", "aaaaa")
                    .aisle("aaaaa", "deeed", "dfffd", "deeed", "aaaaa")
                    .aisle("aaaaa", "deded", "dfdfd", "deded", "aaaaa")
                    .aisle("aaaaa", "deeed", "dfffd", "deeed", "aaaaa")
                    .aisle("aa@aa", "cdddc", "cdddc", "cdddc", "aaaaa")

                    .where("a",
                            Predicates.blocks(CASING_CHEM_PLANT.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where("c", Predicates.blocks(Objects.requireNonNull(GTMaterialBlocks.MATERIAL_BLOCKS
                            .get(TagPrefix.frameGt, GTMaterials.Tungsten)).get()))
                    .where("d", any())
                    .where("e", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("f", Predicates.heatingCoils())
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/chemical_plant_casing"),
                    VoyagerCore.id("block/multiblock/magmatic_foundry"))
            .register();

    public static final MultiblockMachineDefinition BEAM_OF_TEUS = VOYAGERCORE_REGISTRATE
            .multiblock("beam_of_teus",
                    (holder) -> new BeamMachine(holder, .1f, .1f))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(VoyagerRecipeTypes.BEAM_HEATING)
            .recipeModifiers(BeamMachine::recipeModifier)
            .appearanceBlock(CASING_FOUNDRY)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("aaa", "aaa", "aaa")
                    .aisle("aaa", "aba", "aaa")
                    .aisle("aaa", "a@a", "aaa")
                    .where("b", Predicates.blocks(CASING_RADIANT_TITANEX.get()))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where("a",
                            Predicates.blocks(CASING_FOUNDRY.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where("c", Predicates.blocks(Objects.requireNonNull(GTMaterialBlocks.MATERIAL_BLOCKS
                            .get(TagPrefix.frameGt, GTMaterials.Tungsten)).get()))
                    .where("d", any())
                    .where("e", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("f", Predicates.heatingCoils())
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/foundry_casing"),
                    VoyagerCore.id("block/multiblock/magmatic_foundry"))
            .register();

    public static void init() {}
}
