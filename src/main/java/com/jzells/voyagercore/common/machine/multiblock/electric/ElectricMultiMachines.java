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
import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;

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
                            VoyagerCoreRecipeModifiers.ADVANCED_BOOSTING_FUSION,
                            // FusionReactorMachine::recipeModifier,
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
                    (holder) -> new BeamMachine(holder, .1f, .1f, 200))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(VoyagerRecipeTypes.BEAM_HEATING)
            .recipeModifiers(BeamMachine::recipeModifier)
            .appearanceBlock(CASING_FOUNDRY)
            .pattern(def -> FactoryBlockPattern.start()
                    // spotless:off
                .aisle("aaaabbbaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
                .aisle("aabbbbbbbaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
                .aisle("abeebbbeeba", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
                .aisle("abefffffeba", "aaaghhhgaaa", "aaaghhhgaaa", "aaaghhhgaaa", "aaagggggaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaiaaaaa", "aaaaaiaaaaa", "aaaaaiaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
                .aisle("bbbfbbbfbbb", "aaahaaahaaa", "aaahaaahaaa", "aaahaaahaaa", "aaagiiigaaa", "aaaajjjaaaa", "aaaaiiiaaaa", "aaaaaeaaaaa", "aaaacicaaaa", "aaaafifaaaa", "aaaacicaaaa", "aaaaaiaaaaa", "aaaaiiiaaaa")
                .aisle("bbbfbbbfbbb", "acahakahaca", "acahaaahaca", "acahaaahaca", "caagiligaac", "caaajkjaaac", "caaaikiaaac", "acaaekeaaca", "acaiikiiaca", "acciikiicca", "aaaiikiiaaa", "aaaaikiaaaa", "aaaaikiaaaa")
                .aisle("bbbfbbbfbbb", "aaahaaahaaa", "aaahaaahaaa", "aaahaaahaaa", "aaagiiigaaa", "aaaajjjaaaa", "aaaaiiiaaaa", "aaaaaeaaaaa", "aaaacicaaaa", "aaaafifaaaa", "aaaacicaaaa", "aaaaaiaaaaa", "aaaaiiiaaaa")
                .aisle("abefffffeba", "aaaghhhgaaa", "aaaghhhgaaa", "aaaghhhgaaa", "aaagggggaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaiaaaaa", "aaaaaiaaaaa", "aaaaaiaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
                .aisle("abeebbbeeba", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
                .aisle("aabbbbbbbaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
                .aisle("aaaab@baaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
                    //spotless:on
                    .where("a", any())
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where("b",
                            Predicates.blocks(CASING_TITANITE.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2)))
                    .where("c", Predicates.blocks(Objects.requireNonNull(GTMaterialBlocks.MATERIAL_BLOCKS
                            .get(TagPrefix.frameGt, VoyagerMaterials.Calorite)).get()))
                    .where("e", Predicates.blocks(HEAT_VENT.get()))
                    .where("f", Predicates.blocks(MACHINE_CASING_LuV.get()))
                    .where("g", Predicates.blocks(Objects.requireNonNull(GTMaterialBlocks.MATERIAL_BLOCKS
                            .get(TagPrefix.frameGt, GTMaterials.Tungsten)).get()))
                    .where("h", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("i", Predicates.blocks(CASING_BEAM_CONTROL.get()))
                    .where("j", Predicates.blocks(COMPUTER_HEAT_VENT.get()))
                    .where("k", Predicates.blocks(Objects.requireNonNull(GTMaterialBlocks.MATERIAL_BLOCKS
                            .get(TagPrefix.block, GTMaterials.NetherStar)).get()))
                    .where("l", Predicates.abilities(VoyagerPartAbilities.BEAM_LENS))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/titanite_casing"),
                    VoyagerCore.id("block/multiblock/magmatic_foundry"))
            .register();

    // .aisle("aaaabbbaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa",
    // "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
    // .aisle("aabbbbbbbaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa",
    // "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
    // .aisle("abeebbbeeba", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa",
    // "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
    // .aisle("abefffffeba", "aaaghhhgaaa", "aaaghhhgaaa", "aaaghhhgaaa", "aaagggggaaa", "aaaaaaaaaaa", "aaaaaaaaaaa",
    // "aaaaaaaaaaa", "aaaaaiaaaaa", "aaaaaiaaaaa", "aaaaaiaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
    // .aisle("bbbfbbbfbbb", "aaahaaahaaa", "aaahaaahaaa", "aaahaaahaaa", "aaagiiigaaa", "aaaajjjaaaa", "aaaaiiiaaaa",
    // "aaaaaeaaaaa", "aaaacicaaaa", "aaaafifaaaa", "aaaacicaaaa", "aaaaaiaaaaa", "aaaaiiiaaaa")
    // .aisle("bbbfbbbfbbb", "acahakahaca", "acahaaahaca", "acahaaahaca", "caagiligaac", "caaajkjaaac", "caaaikiaaac",
    // "acaaekeaaca", "acaiikiiaca", "acciikiicca", "aaaiikiiaaa", "aaaaikiaaaa", "aaaaikiaaaa")
    // .aisle("bbbfbbbfbbb", "aaahaaahaaa", "aaahaaahaaa", "aaahaaahaaa", "aaagiiigaaa", "aaaajjjaaaa", "aaaaiiiaaaa",
    // "aaaaaeaaaaa", "aaaacicaaaa", "aaaafifaaaa", "aaaacicaaaa", "aaaaaiaaaaa", "aaaaiiiaaaa")
    // .aisle("abefffffeba", "aaaghhhgaaa", "aaaghhhgaaa", "aaaghhhgaaa", "aaagggggaaa", "aaaaaaaaaaa", "aaaaaaaaaaa",
    // "aaaaaaaaaaa", "aaaaaiaaaaa", "aaaaaiaaaaa", "aaaaaiaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
    // .aisle("abeebbbeeba", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa",
    // "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
    // .aisle("aabbbbbbbaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa",
    // "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
    // .aisle("aaaab@baaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaacaaaaa", "aaaaacaaaaa", "aaaaacaaaaa",
    // "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaa")
    //
    // .where("a", Predicates.blocks("minecraft:air"))
    // .where("b", Predicates.blocks("kubejs:titanite_casing"))
    // .where("c", Predicates.blocks("gtceu:calorite_frame"))
    // .where("d", Predicates.blocks("chisel_chipped_integration:factory_blue_framed_circuit"))
    // .where("e", Predicates.blocks("gtceu:heat_vent"))
    // .where("f", Predicates.blocks("gtceu:luv_machine_casing"))
    // .where("g", Predicates.blocks("gtceu:black_steel_frame"))
    // .where("h", Predicates.blocks("gtceu:laminated_glass"))
    // .where("i", Predicates.blocks("kubejs:stout_titanium_carbide_casing"))
    // .where("j", Predicates.blocks("gtceu:computer_heat_vent"))
    // .where("k", Predicates.blocks("gtceu:nether_star_block"))
    // .where("l", Predicates.blocks("kubejs:teus_beam_block"))
    // .where("m", Predicates.blocks("gtceu:source_steel_frame"))

    public static final MultiblockMachineDefinition PULVERIZER = VOYAGERCORE_REGISTRATE
            .multiblock("pulverizer",
                    TieredPulverizerMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(VoyagerRecipeTypes.PULVERIZING)
            .recipeModifiers(TieredPulverizerMachine::recipeModifier, VoyagerCoreRecipeModifiers.ADVANCED_BOOSTING,
                    GTRecipeModifiers.OC_NON_PERFECT, BATCH_MODE)
            .appearanceBlock(CASING_INDUSTRIAL_MACERATION)
            .pattern(def -> FactoryBlockPattern.start()

                    .aisle("abbbbbbbbbbba", "abbbbbbbbbbba", "abbbbbbbbbbba", "abbbbbbbbbbba", "abbbbbbbbbbba")
                    .aisle("bbbbbbbbbbbbb", "bccdccdccdccb", "bccdccdccdccb", "beefccfccfeeb", "beefeefeefeeb")
                    .aisle("bbbbbbbbbbbbb", "bccdccdccdccb", "bccdccdccdccb", "beefccfccfeeb", "beefeefeefeeb")
                    .aisle("bbbbbbbbbbbbb", "bccdccdccdccb", "bccdccdccdccb", "beefccfccfeeb", "beefeefeefeeb")
                    .aisle("bbbbbbbbbbbbb", "bccdccdccdccb", "bccdccdccdccb", "beefccfccfeeb", "beefeefeefeeb")
                    .aisle("bbbbbbbbbbbbb", "bccdccdccdccb", "bccdccdccdccb", "beefccfccfeeb", "beefeefeefeeb")
                    .aisle("abbbbbbbbbbba", "abbbbbbbbbbba", "abbbbbbbbbbba", "abbbbbbbbbbba", "abbbbbbbbbbba")
                    .aisle("eeeeabbbaeeee", "eeeeabbbaeeee", "eeeeab@baeeee", "eeeeabbbaeeee", "eeeeabbbaeeee")
                    .where("b", Predicates.blocks(CASING_INDUSTRIAL_MACERATION.get())
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2)))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where("a",
                            Predicates.blocks(Objects.requireNonNull(GTMaterialBlocks.MATERIAL_BLOCKS
                                    .get(TagPrefix.frameGt, GTMaterials.Tungsten)).get()))
                    .where("@", Predicates.controller(Predicates.blocks(def.get())))
                    .where("c", Predicates.abilities(VoyagerPartAbilities.CRUSHING_WHEEL))
                    .where("d", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("e", any())
                    .where("f", Predicates.blocks(CASING_TUNGSTENSTEEL_ROBUST.get()))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/industrial_maceration_casing"),
                    VoyagerCore.id("block/multiblock/pulverizer"))
            .register();

    public static final MultiblockMachineDefinition INDUSTRIAL_APIARY = VOYAGERCORE_REGISTRATE
            .multiblock("industrial_apiary", ApiaryMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_FROST_CONDUCTING)
            .recipeTypes(GTRecipeTypes.DUMMY_RECIPES)
            .pattern(def -> FactoryBlockPattern.start()
                    .aisle("XXX","XXX","XXX")
                    .aisle("XXX","XAX","XXX")
                    .aisle("XXX","XCX","XXX")
                    .where("X", Predicates.blocks(CASING_FROST_CONDUCTING.get())
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(VoyagerPartAbilities.BEE_HOLDER).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2,1)))
                    .where("A",Predicates.any())
                    .where("C", Predicates.controller(Predicates.blocks(def.get())))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/frost_conducting_casing"),VoyagerCore.id("block/multiblock/magmatic_foundry"))
            .register();

    public static void init() {}
}
