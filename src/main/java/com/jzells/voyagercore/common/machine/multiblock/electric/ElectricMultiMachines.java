package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderHelper;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FusionReactorMachine;

import net.minecraft.world.level.block.Block;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerCoreRecipeModifiers;
import com.jzells.voyagercore.common.data.VoyagerMaterials;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;

import java.util.Objects;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.any;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.BATCH_MODE;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.registerTieredMultis;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableCasingMachineModel;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toRomanNumeral;
import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;
import static com.jzells.voyagercore.common.data.VoyagerBlocks.*;

@SuppressWarnings("removal")

public class ElectricMultiMachines {

    // Multiblocks
    public static final MultiblockMachineDefinition MAGMATIC_FOUNDRY = VOYAGERCORE_REGISTRATE
            .multiblock("magmatic_foundry",
                    (holder) -> new FluidCoilMulti(holder, VoyagerMaterials.Pyrotheum.getFluid(10)))
            .rotationState(RotationState.ALL)
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
            .rotationState(RotationState.ALL)
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
            .rotationState(RotationState.ALL)
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

    // public static final MultiblockMachineDefinition SUPER_DONUT_1 = VOYAGERCORE_REGISTRATE
    // .multiblock("super_donut_1",
    // (holder) -> new FusionReactorMachine(holder, 1))
    // .langValue("Supermassive Fusion Array Mk I (Super Donut)")
    // .rotationState(RotationState.ALL)
    // .recipeTypes(GTRecipeTypes.FUSION_RECIPES)
    // .recipeModifiers(VoyagerCoreRecipeModifiers.ADVANCED_BOOSTING,
    // FusionReactorMachine::recipeModifier, BATCH_MODE)
    // .appearanceBlock(FUSION_CASING)
    //// Wtf spotless 😂
    // .pattern(def -> FactoryBlockPattern.start()
    //
    //
    // .where("a", Predicates.any())
    // .where("b", Predicates.blocks(FUSION_CASING.get()))
    // .where("d", Predicates.blocks(FUSION_GLASS.get()))
    // .where("e", Predicates.blocks(SUPERCONDUCTING_COIL.get()))
    // .where("f", Predicates.abilities(PartAbility.INPUT_ENERGY))
    // .where("g", Predicates.abilities(PartAbility.IMPORT_FLUIDS))
    // .where("h", Predicates.abilities(PartAbility.EXPORT_FLUIDS))
    // .where("@", Predicates.controller(Predicates.blocks(def.get())))
    // .build())
    // .hasBER(true)
    // .workableCasingModel(GTCEu.id("block/casings/fusion/fusion_casing"),
    // GTCEu.id("block/multiblock/fusion_reactor/fusion"))
    // .register();

    public static final MultiblockMachineDefinition[] SUPER_DONUT = registerTieredMultis(VOYAGERCORE_REGISTRATE,
            "super_donut",
            FusionReactorMachine::new,
            (tier, builder) -> builder
                    .langValue("Supermassive Fusion Array Mk %s (Super Donut)"
                            .formatted(toRomanNumeral(tier - 5)))
                    .rotationState(RotationState.ALL)
                    .recipeType(GTRecipeTypes.FUSION_RECIPES)
                    .recipeModifiers(
                            VoyagerCoreRecipeModifiers.ADVANCED_BOOSTING,
                            FusionReactorMachine::recipeModifier,
                            BATCH_MODE)
                    .appearanceBlock(FUSION_CASING)
                    .pattern(def -> FactoryBlockPattern.start()

                            .aisle("aaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaabbbaaaaaaaaaaa",
                                    "aaaaaaaaabbbbbbbaaaaaaaaa", "aaaaaaaaaaabbbaaaaaaaaaaa",
                                    "aaaaaaaaaaaaaaaaaaaaaaaaa")

                            .aisle("aaaaaaaaaaaadaaaaaaaaaaaa", "aaaaaaaabbbbabbbbaaaaaaaa",
                                    "aaaaaaabbaaaaaaabbaaaaaaa", "aaaaaaaabbbbabbbbaaaaaaaa",
                                    "aaaaaaaaaaaadaaaaaaaaaaaa")

                            .aisle("aaaaaaaaadddddddaaaaaaaaa", "aaaaaabbbaaaaaaabbbaaaaaa",
                                    "aaaaabbeeaaaaaaaeebbaaaaa", "aaaaaabbbaaaaaaabbbaaaaaa",
                                    "aaaaaaaaadddddddaaaaaaaaa")

                            .aisle("aaaaaaadddddddddddaaaaaaa", "aaaaabbaaaaaaaaaaabbaaaaa",
                                    "aaaabeeaaaaaaaaaaaeebaaaa", "aaaaabbaaaaaaaaaaabbaaaaa",
                                    "aaaaaaadddddddddddaaaaaaa")

                            .aisle("aaaaaadddaaadaaddddaaaaaa", "aaaabbaaaabbabbaaaabbaaaa",
                                    "aaabeaaaaaaaaaaaaaaaebaaa", "aaaabbaaaabbabbaaaabbaaaa",
                                    "aaaaaaddddaadaaddddaaaaaa")

                            .aisle("aaaaadddaaaaaaaaadddaaaaa", "aaabbaaabbabfbabbaaabbaaa",
                                    "aabeaaaaaabfgfbaaaaaaebaa", "aaabbaaabbabfbabbaaabbaaa",
                                    "aaaaadddaaaaaaaaadddaaaaa")

                            .aisle("aaaadddaaaaaaaaaaadddaaaa", "aabbaaabaaaaaaaaabaaabbaa",
                                    "aabeaaaabbaaaaabbaaaaebaa", "aabbaaabaaaaaaaaabaaabbaa",
                                    "aaaadddaaaaaaaaaaadddaaaa")

                            .aisle("aaadddaaaaaaaaaaaaadddaaa", "aabaaabaaaaaaaaaaabaaabaa",
                                    "abeaaaabaaaaaaaaabaaaaeba", "aabaaabaaaaaaaaaaabaaabaa",
                                    "aaadddaaaaaaaaaaaaadddaaa")

                            .aisle("aaaddaaaaaaaaaaaaaaaddaaa", "abbaabaaaaaaaaaaaaabaabba",
                                    "abeaaabaaaaaaaaaaabaaaeba", "abbaabaaaaaaaaaaaaabaabba",
                                    "aaaddaaaaaaaaaaaaaaaddaaa")

                            .aisle("aadddaaaaaaaaaaaaaaaaddaa", "abaaabaaaaaaaaaaaaabaaaba",
                                    "baaaaabaaaaaaaaaaabaaaaab", "abaaabaaaaaaaaaaaaabaaaba",
                                    "aadddaaaaaaaaaaaaaaaaddaa")

                            .aisle("aaddaaaaaaaaaaaaaaaaaddaa", "abaabaaaaaaaaaaaaaaabaaba",
                                    "baaaabaaaaaaaaaaaaabaaaab", "abaabaaaaaaaaaaaaaaabaaba",
                                    "aaddaaaaaaaaaaaaaaaaaddaa")

                            .aisle("aaddaaaaaaaaaaaaaaaaaddaa", "bbaabbaaaaaaaaaaaaabbaabb",
                                    "baaaafaaaaaaaaaaaaafaaaab", "bbaabbaaaaaaaaaaaaabbaabb",
                                    "aaddaaaaaaaaaaaaaaaaaddaa")

                            .aisle("addddaaaaaaaaaaaaaaadddda", "baaaafaaaaaaaaaaaaafaaaab",
                                    "baaaahaaaaaaaaaaaaahaaaab", "baaaafaaaaaaaaaaaaafaaaab",
                                    "addddaaaaaaaaaaaaaaadddda")

                            .aisle("aaddaaaaaaaaaaaaaaaaaddaa", "bbaabbaaaaaaaaaaaaabbaabb",
                                    "baaaafaaaaaaaaaaaaafaaaab", "bbaabbaaaaaaaaaaaaabbaabb",
                                    "aaddaaaaaaaaaaaaaaaaaddaa")

                            .aisle("aaddaaaaaaaaaaaaaaaaaddaa", "abaabaaaaaaaaaaaaaaabaaba",
                                    "baaaabaaaaaaaaaaaaabaaaab", "abaabaaaaaaaaaaaaaaabaaba",
                                    "aaddaaaaaaaaaaaaaaaaaddaa")

                            .aisle("aaddaaaaaaaaaaaaaaaadddaa", "abaabaaaaaaaaaaaaaabaaaba",
                                    "baaaabaaaaaaaaaaaabaaaaab", "abaabaaaaaaaaaaaaaabaaaba",
                                    "aaddaaaaaaaaaaaaaaaadddaa")

                            .aisle("aaaddaaaaaaaaaaaaaaaddaaa", "abbaabaaaaaaaaaaaaabaabba",
                                    "abeaaabaaaaaaaaaaabaaaeba", "abbaabaaaaaaaaaaaaabaabba",
                                    "aaaddaaaaaaaaaaaaaaaddaaa")

                            .aisle("aaadddaaaaaaaaaaaaadddaaa", "aabaaabaaaaaaaaaaabaaabaa",
                                    "abeaaaabaaaaaaaaabaaaaeba", "aabaaabaaaaaaaaaaabaaabaa",
                                    "aaadddaaaaaaaaaaaaadddaaa")

                            .aisle("aaaadddaaaaaaaaaaadddaaaa", "aabbaaabaaaaaaaaabaaabbaa",
                                    "aabeaaaabbaaaaabbaaaaebaa", "aabbaaabaaaaaaaaabaaabbaa",
                                    "aaaadddaaaaaaaaaaadddaaaa")

                            .aisle("aaaaadddaaaaaaaaadddaaaaa", "aaabbaaabbabfbabbaaabbaaa",
                                    "aabeaaaaaabfgfbaaaaaaebaa", "aaabbaaabbabfbabbaaabbaaa",
                                    "aaaaadddaaaaaaaaadddaaaaa")

                            .aisle("aaaaaaddddaadaaddddaaaaaa", "aaaabbaaaabbabbaaaabbaaaa",
                                    "aaabeaaaaaaaaaaaaaaaebaaa", "aaaabbaaaabbabbaaaabbaaaa",
                                    "aaaaaaddddaadaaddddaaaaaa")

                            .aisle("aaaaaaadddddddddddaaaaaaa", "aaaaabbaaaaaaaaaaabbaaaaa",
                                    "aaaabeeaaaaaaaaaaaeebaaaa", "aaaaabbaaaaaaaaaaabbaaaaa",
                                    "aaaaaaadddddddddddaaaaaaa")

                            .aisle("aaaaaaaaadddddddaaaaaaaaa", "aaaaaabbbaaaaaaabbbaaaaaa",
                                    "aaaaabbeeaaaaaaaeebbaaaaa", "aaaaaabbbaaaaaaabbbaaaaaa",
                                    "aaaaaaaaadddddddaaaaaaaaa")

                            .aisle("aaaaaaaaaaaadaaaaaaaaaaaa", "aaaaaaaabbbbabbbbaaaaaaaa",
                                    "aaaaaaabbaaaaaaabbaaaaaaa", "aaaaaaaabbbbabbbbaaaaaaaa",
                                    "aaaaaaaaaaaadaaaaaaaaaaaa")

                            .aisle("aaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaabbbaaaaaaaaaaa",
                                    "aaaaaaaaabbb@bbbaaaaaaaaa", "aaaaaaaaaaabbbaaaaaaaaaaa",
                                    "aaaaaaaaaaaaaaaaaaaaaaaaa")

                            .where('a', Predicates.any())
                            .where('b', Predicates.blocks(FusionReactorMachine.getCasingState(tier)))
                            .where('d', Predicates.blocks(FUSION_GLASS.get()))
                            .where('e', Predicates.blocks(FusionReactorMachine.getCoilState(tier)))

                            .where('f',
                                    Predicates.blocks(FusionReactorMachine.getCasingState(tier)).or(
                                            Predicates.blocks(
                                                    PartAbility.INPUT_ENERGY
                                                            .getBlockRange(tier, UV)
                                                            .toArray(Block[]::new))
                                                    .setMinGlobalLimited(1)
                                                    .setPreviewCount(16)))

                            .where('g',
                                    Predicates.blocks(FusionReactorMachine.getCasingState(tier)).or(
                                            Predicates.abilities(PartAbility.IMPORT_FLUIDS)
                                                    .setMinGlobalLimited(1)))

                            .where('h',
                                    Predicates.blocks(FusionReactorMachine.getCasingState(tier)).or(
                                            Predicates.abilities(PartAbility.EXPORT_FLUIDS)
                                                    .setMinGlobalLimited(1)))

                            .where('@', Predicates.controller(Predicates.blocks(def.get())))
                            .build())

                    .modelProperty(GTMachineModelProperties.RECIPE_LOGIC_STATUS, RecipeLogic.Status.IDLE)
                    .model(createWorkableCasingMachineModel(
                            GTCEu.id(FusionReactorMachine.getCasingType(tier).getTexture().getPath()),
                            GTCEu.id("block/multiblock/fusion_reactor/fusion"))
                            .andThen(b -> b.addDynamicRenderer(DynamicRenderHelper::createFusionRingRender)))

                    .hasBER(true)
                    .register(),
            LuV, ZPM, UV);

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
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2)))
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
