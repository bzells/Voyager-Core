package com.jzells.voyagercore.common.machine.multiblock.steam;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;

import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_BRONZE_BRICKS;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;

public class SteamMultis {

    public static final MultiblockMachineDefinition LARGE_STEAM_CENTRIFUGE = VOYAGERCORE_REGISTRATE
            .multiblock("large_steam_centrifuge", (holder) -> new SteamParallelMultiblockMachine(holder, 8))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CENTRIFUGE_RECIPES)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("DDD", "CCC", "CCC")
                    .aisle("DDD", "CAC", "CCC")
                    .aisle("DDD", "C@C", "CCC")

                    .where('D', Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(4)
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.STEAM).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setMaxGlobalLimited(1)))
                    .where('C', Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()))
                    .where('A', Predicates.blocks(GTBlocks.CASING_BRONZE_GEARBOX.get()))
                    .where('@', Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/centrifuge"))
            .register();

    public static final MultiblockMachineDefinition LARGE_STEAM_ORE_WASHER = VOYAGERCORE_REGISTRATE
            .multiblock("large_steam_ore_washer", (holder) -> new SteamParallelMultiblockMachine(holder, 8))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(ORE_WASHER_RECIPES)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("DDD", "CCC", "CCC")
                    .aisle("DDD", "CAC", "CCC")
                    .aisle("DDD", "C@C", "CCC")

                    .where('D', Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(4)
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.STEAM).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setMaxGlobalLimited(1)))
                    .where('C', Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()))
                    .where('A', Predicates.blocks(GTBlocks.CASING_BRONZE_GEARBOX.get()))
                    .where('@', Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/ore_washer"))
            .register();

    public static final MultiblockMachineDefinition LARGE_STEAM_FORGE_HAMMER = VOYAGERCORE_REGISTRATE
            .multiblock("large_steam_forge_hammer", (holder) -> new SteamParallelMultiblockMachine(holder, 16))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(ORE_WASHER_RECIPES)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("DDD", "CCC", "CCC")
                    .aisle("DDD", "CAC", "CCC")
                    .aisle("DDD", "C@C", "CCC")

                    .where('D', Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(4)
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.STEAM).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setMaxGlobalLimited(1)))
                    .where('C', Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()))
                    .where('A', Predicates.blocks(GTBlocks.CASING_BRONZE_GEARBOX.get()))
                    .where('@', Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/forge_hammer"))
            .register();

    public static final MultiblockMachineDefinition LARGE_STEAM_FORGE_COMPRESSOR = VOYAGERCORE_REGISTRATE
            .multiblock("large_steam_forge_compressor", (holder) -> new SteamParallelMultiblockMachine(holder, 16))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(ORE_WASHER_RECIPES)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("DDD", "CCC", "CCC")
                    .aisle("DDD", "CAC", "CCC")
                    .aisle("DDD", "C@C", "CCC")

                    .where('D', Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(4)
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.STEAM).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setMaxGlobalLimited(1)))
                    .where('C', Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()))
                    .where('A', Predicates.blocks(GTBlocks.CASING_BRONZE_GEARBOX.get()))
                    .where('@', Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/compressor"))
            .register();

    public static void init() {}
}
