package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerMaterials;

import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;

@SuppressWarnings("removal")

public class ElectricMultiMachines {

    // Multiblocks
    public static final MultiblockMachineDefinition MAGMATIC_FOUNDRY = VOYAGERCORE_REGISTRATE
            .multiblock("magmatic_foundry",
                    (holder) -> new FluidCoilMulti(holder, VoyagerMaterials.Pyrotheum.getFluid(200)))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.BLAST_RECIPES)
            .recipeModifiers(FluidCoilMulti::recipeModifier)
            .appearanceBlock(CASING_HIGH_TEMPERATURE_SMELTING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("HHTHH", "F   F", "FAAAF", "F   F", "F   F", "F   F", "FAAAF", "F   F", "HHTHH")
                    .aisle("HHHHH", " VVV ", "AAAAA", " CCC ", " CCC ", " CCC ", "AAAAA", " VVV ", "HHHHH")
                    .aisle("THHHT", " V V ", "AAAAA", " C C ", " C C ", " C C ", "AAAAA", " V V ", "THHHT")
                    .aisle("HHHHH", " VVV ", "AAAAA", " CCC ", " CCC ", " CCC ", "AAAAA", " VVV ", "HHHHH")
                    .aisle("HH@HH", "F   F", "FAAAF", "F   F", "F   F", "F   F", "FAAAF", "F   F", "HHTHH")
                    .where('A',
                            blocks(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("kubejs:foundry_casing"))))
                    .where('T', blocks(CASING_STEEL_PIPE.get()))
                    .where('V', blocks(HEAT_VENT.get()))
                    .where('F', Predicates.frames(GTMaterials.Tungsten))
                    .where('H', blocks(CASING_HIGH_TEMPERATURE_SMELTING.get())
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1)))
                    .where("C", Predicates.heatingCoils())
                    .where("M", Predicates.abilities(PartAbility.MUFFLER))
                    .where("@", Predicates.controller(blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/gcym/high_temperature_smelting_casing"),
                    VoyagerCore.id("block/multiblock/magmatic_foundry"))
            .register();

    public static void init() {}
}
