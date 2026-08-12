package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.*;

import net.minecraft.world.level.block.Blocks;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerCoreRecipeModifiers;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.any;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;
import static com.jzells.voyagercore.common.data.VoyagerBlocks.*;

public class HelperElectricMultiMachines {

    // Multiblocks
    public static final MultiblockMachineDefinition HELPER_EBF = VOYAGERCORE_REGISTRATE
            .multiblock("helper_electric_blast_furnace",
                    CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.BLAST_RECIPES)
            .recipeModifiers(VoyagerCoreRecipeModifiers.HELPER_COMPATABILITY, GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(CASING_HEATPROOF_HELPER)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("ccc", "hhh", "hhh", "ccc")
                    .aisle("ccc", "h h", "h h", "cmc")
                    .aisle("c@c", "hhh", "hhh", "ccc")

                    .where("c",
                            Predicates.blocks(CASING_HEATPROOF_HELPER.get())
                                    .setMinGlobalLimited(5)
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2)
                                            .setMinGlobalLimited(1).setPreviewCount(1))
                                    .or(Predicates.abilities(VoyagerPartAbilities.HELPER_HOLDER).setExactLimit(1)
                                            .setPreviewCount(1)))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where(" ", any())
                    .where("h", Predicates.heatingCoils())
                    .where("m", Predicates.abilities(PartAbility.MUFFLER))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/heatproof_helper_casing"),
                    VoyagerCore.id("block/multiblock/helper_electric_blast_furnace"))
            .register();

    public static final MultiblockMachineDefinition SMD_ASSEMBLER = VOYAGERCORE_REGISTRATE
            .multiblock("smd_assembler",
                    WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeTypes(VoyagerRecipeTypes.SMD_ASSEMBLY)
            .recipeModifiers(
                    GTRecipeModifiers.OC_NON_PERFECT, VoyagerCoreRecipeModifiers.HELPER_COMPATABILITY)
            .appearanceBlock(CASING_CLEAN_ASSEMBLY)
            .pattern(def -> FactoryBlockPattern.start()

                    .aisle("cc", "cc")
                    .aisle("cc", "c@")

                    .where("c", Predicates.blocks(CASING_CLEAN_ASSEMBLY.get())
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setExactLimit(1))
                            .or(Predicates.abilities(VoyagerPartAbilities.HELPER_HOLDER).setPreviewCount(1)
                                    .setMaxGlobalLimited(1)))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/clean_assembly_casing"),
                    GTCEu.id("block/machines/assembler"))
            .register();

    public static final MultiblockMachineDefinition GRANDMAS_STOVETOP_OVEN = VOYAGERCORE_REGISTRATE
            .multiblock("grandmas_stovetop_oven", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_STEEL_SOLID)
            .recipeTypes(VoyagerRecipeTypes.GRANDMAS_BAKING)
            .recipeModifiers(GTRecipeModifiers.OC_NON_PERFECT, VoyagerCoreRecipeModifiers.HELPER_COMPATABILITY,
                    VoyagerCoreRecipeModifiers.PARAMOUNT_HELPER_REQUIRE, VoyagerCoreRecipeModifiers.HEAT_BOOSTING)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("abbba", "aaaaa", "aaaaa", "aaaaa", "aaaaa", "aa@aa", "daaad")
                    .aisle("beeeb", "cdddc", "cdddc", "cdddc", "aggga", "ddddd", "ddddd")
                    .aisle("beheb", "cdddc", "cdddc", "cdddc", "aghga", "ddddd", "ddddd")
                    .aisle("beeeb", "cdddc", "cdddc", "cdddc", "aggga", "ddddd", "ddddd")
                    .aisle("abbba", "cfffc", "cfffc", "cfffc", "aaaaa", "ddddd", "ddddd")
                    .where("a", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(VoyagerPartAbilities.HELPER_HOLDER).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2, 1)))
                    .where("b", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where("c", Predicates.blocks(CASING_STAINLESS_CLEAN.get()))
                    .where("d", Predicates.any())
                    .where("e", Predicates.heatingCoils())
                    .where("f", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("g", Predicates.blocks(CASING_HIGH_TEMPERATURE_SMELTING.get()))
                    .where("h", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                    GTCEu.id("block/machines/arc_furnace"))
            .register();

    public static final MultiblockMachineDefinition CELESTIAL_POST_BOX = VOYAGERCORE_REGISTRATE
            .multiblock("celestial_post_box", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_STEEL_SOLID)
            .recipeTypes(VoyagerRecipeTypes.CELESTIAL_POST_BOX)
            .recipeModifiers(GTRecipeModifiers.OC_NON_PERFECT, VoyagerCoreRecipeModifiers.HELPER_COMPATABILITY,
                    VoyagerCoreRecipeModifiers.PARAMOUNT_HELPER_REQUIRE)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa",
                            "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaggga")
                    .aisle("aaccca", "aaggga", "aaggga", "aaggga", "aaggga", "aggggg", "aaggga", "aaggga", "aaggga",
                            "aaggga", "aaggga", "aaggga", "aaggga", "aaggga", "agcccg")
                    .aisle("aaccca", "aagiga", "aagiga", "aagiga", "aagiga", "aggigg", "aagiga", "aagiga", "aagiga",
                            "aagiga", "aagiga", "aagiga", "aagiga", "aagiga", "agcccg")
                    .aisle("aaccca", "aaggga", "aaggga", "aaggga", "aaggga", "aggggg", "aaggga", "aaggga", "aaggga",
                            "aaggga", "aaggga", "aaggga", "aaggga", "aaggga", "agcccg")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aggggg", "abbbbb", "abcccb", "abcccb",
                            "abcccb", "abbcbb", "aabbba", "aaaaaa", "aaaaaa", "aaghga")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aggggg", "abbbbb", "abaaab", "abfefb",
                            "abaaab", "abbabb", "aabbba", "aaaaaa", "aaaaaa", "aaaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aggggg", "abbbbb", "abaaab", "abaeab",
                            "abaaab", "abbabb", "aabbba", "aaaaaa", "aaaaaa", "aaaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aggggg", "abbbbb", "abaaab", "abfefb",
                            "abaaab", "abbabb", "aabbba", "aaaaaa", "aaaaaa", "aaaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aggggg", "abbbbb", "abaaab", "abaeab",
                            "abaaab", "abbabb", "aabbba", "aaaaaa", "aaaaaa", "aaaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aggggg", "abbbbb", "abaaab", "abfefb",
                            "abaaab", "abbabb", "aabbba", "aaaaaa", "aaaaaa", "aaaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "abbbbb", "abaaab", "abaeab",
                            "abaaab", "abbabb", "aabbba", "aaaaaa", "aaaaaa", "aaaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "abbbbb", "abaaab", "abfefb",
                            "abaaab", "abbabb", "aabbba", "aaaaaa", "daaaaa", "daaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "abbbbb", "abaaab", "dbaeab",
                            "dbaaab", "dbbabb", "dabbba", "daaaaa", "daaaaa", "daaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "abbbbb", "abcccb", "abcccb",
                            "abcccb", "abbcbb", "aabbba", "aaaaaa", "aaaaaa", "aaaaaa")
                    .aisle("aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "aaaaaa", "abbbbb", "abaaab", "abaaab",
                            "abaaab", "abbabb", "aabbba", "aaaaaa", "aaaaaa", "aaaaaa")

                    .where("a", Predicates.any())
                    .where("b", Predicates.blocks(CASING_OSTRUM.get()))
                    .where("c", Predicates.blocks(CASING_CONDENSATION_RESISTANT_TUNGSTEN.get()))
                    .where("d", Predicates.blocks(Blocks.RED_CONCRETE))
                    .where("e", Predicates.blocks(CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("f", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                    .where("g", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(VoyagerPartAbilities.HELPER_HOLDER).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2, 1)))
                    .where("i",
                            Predicates.blocks(VoyagerVoltageTierUtils.getFrameBlock(GTMaterials.TungstenCarbide).get()))
                    .where("h", Predicates.controller(Predicates.blocks(def.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                    GTCEu.id("block/machines/electrolyzer"))
            .register();

    public static void init() {}
}
