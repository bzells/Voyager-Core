package com.jzells.voyagercore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import com.jzells.voyagercore.common.machine.multiblock.unpowered.HeatExchangerMultiMachine;
import com.jzells.voyagercore.common.machine.multiblock.unpowered.ThermalSolarMachine;
import com.jzells.voyagercore.util.VoyagerPredicates;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;
import static com.jzells.voyagercore.common.data.VoyagerBlocks.*;

public class UnpoweredMultiMachines {

    public static final MultiblockMachineDefinition HEAT_EXCHANGER = VOYAGERCORE_REGISTRATE
            .multiblock("heat_exchanger", HeatExchangerMultiMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .appearanceBlock(CASING_INDUSTRIAL_SOLID)
            .pattern(
                    def -> FactoryBlockPattern
                            .start(RelativeDirection.BACK, RelativeDirection.LEFT, RelativeDirection.DOWN)
                            .aisle("XXXXXXXXX")
                            .aisle("FPPPPPPPF")
                            .aisle("CXXXXXXXX")
                            .aisle("FPPPPPPPF")
                            .aisle("XXXXXXXXX")
                            .where('X', Predicates.blocks(CASING_INDUSTRIAL_SOLID.get()))
                            .where('P', VoyagerPredicates.pipeCasingTraceable())
                            .where('C', Predicates.controller(Predicates.blocks(def.get())))
                            .where('F', Predicates.ability(PartAbility.IMPORT_FLUIDS_1X)
                                    .setExactLimit(2)
                                    .or(Predicates
                                            .abilities(PartAbility.EXPORT_FLUIDS_1X)
                                            .setExactLimit(2)))
                            .build())
            .workableCasingModel(VoyagerCore.id("block/casing/solid_industrial_casing"),
                    VoyagerCore.id("block/multiblock/heat_exchanger"))
            .register();
    public static final MultiblockMachineDefinition THERMAL_SOLAR = VOYAGERCORE_REGISTRATE
            .multiblock("thermal_solar", ThermalSolarMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(CASING_INDUSTRIAL_SOLID)
            .recipeType(VoyagerRecipeTypes.SOLAR_TOWER)
            .recipeModifier(ThermalSolarMachine::recipeModifier, true)
            .pattern(def -> FactoryBlockPattern
                    .start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                    .aisle("A A", "   ", "A A").setRepeatable(5)
                    .aisle("BBB", "BBB", "BBB")
                    .aisle("B@B", "B B", "BBB")
                    .aisle("BBB", "BBB", "BBB")
                    .where('A', Predicates.frames(GTMaterials.Tungsten))
                    .where('B', Predicates.blocks(CASING_INDUSTRIAL_SOLID.get())
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setExactLimit(1)))
                    .where('@', Predicates.controller(Predicates.blocks(def.get())))
                    .build())
            .workableCasingModel(VoyagerCore.id("block/casing/condensation_resistant_tungsten_casing"),
                    VoyagerCore.id("block/multiblock/thermal_solar_tower"))
            .register();

    public static void init() {}
}
