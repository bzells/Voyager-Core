package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.*;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerCoreRecipeModifiers;
import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.any;
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
            .recipeModifiers(VoyagerCoreRecipeModifiers.HELPER_COMPATABILITY,
                    GTRecipeModifiers::ebfOverclock)
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

    public static void init() {}
}
