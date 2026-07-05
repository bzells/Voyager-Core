package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;

import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.jzells.voyagercore.common.machine.multiblock.part.BeeHolderPartMachine;
import net.minecraft.network.chat.Component;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.machine.multiblock.part.BeamPartMachine;
import com.jzells.voyagercore.common.machine.multiblock.part.CrushingWheelPartMachine;
import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.capability.recipe.IO.*;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableTieredHullMachineModel;

public class VoyagerMachines {

    public static final MachineDefinition LUV_CRUSH_WHEEL = VoyagerCore.VOYAGERCORE_REGISTRATE.machine(
            "luv_crushing_wheel",
            holder -> new CrushingWheelPartMachine(holder, GTValues.LuV))

            .rotationState(RotationState.NON_Y_AXIS)
            .abilities(VoyagerPartAbilities.CRUSHING_WHEEL)
            .workableCasingModel(VoyagerCore.id("block/crushing_wheel/luv/luv_crushing_wheel"),
                    VoyagerCore.id("block/crushing_wheel/luv/luv_crushing_wheel"))
            .tooltips(Component.literal("Crushing Wheel Tier: " + GTValues.LuV))
            .register();

    public static final MachineDefinition ZPM_CRUSH_WHEEL = VoyagerCore.VOYAGERCORE_REGISTRATE.machine(
            "zpm_crushing_wheel",
            holder -> new CrushingWheelPartMachine(holder, GTValues.ZPM))

            .rotationState(RotationState.NON_Y_AXIS)
            .abilities(VoyagerPartAbilities.CRUSHING_WHEEL)
            .workableCasingModel(VoyagerCore.id("block/crushing_wheel/zpm/zpm_crushing_wheel"),
                    VoyagerCore.id("block/crushing_wheel/zpm/zpm_crushing_wheel"))
            .tooltips(Component.literal("Crushing Wheel Tier: " + GTValues.ZPM))
            .register();

    public static final MachineDefinition UV_CRUSH_WHEEL = VoyagerCore.VOYAGERCORE_REGISTRATE.machine(
            "uv_crushing_wheel",
            holder -> new CrushingWheelPartMachine(holder, GTValues.UV))

            .rotationState(RotationState.NON_Y_AXIS)
            .abilities(VoyagerPartAbilities.CRUSHING_WHEEL)
            .workableCasingModel(VoyagerCore.id("block/crushing_wheel/uv/uv_crushing_wheel"),
                    VoyagerCore.id("block/crushing_wheel/uv/uv_crushing_wheel"))
            .tooltips(Component.literal("Crushing Wheel Tier: " + GTValues.UV))
            .register();

    public static final MachineDefinition NETHER_STAR_BEAM = VoyagerCore.VOYAGERCORE_REGISTRATE.machine(
            "nether_star_beam",
            holder -> new BeamPartMachine(holder, .1f))

            .rotationState(RotationState.ALL)
            .abilities(VoyagerPartAbilities.BEAM_LENS)
            .workableCasingModel(VoyagerCore.id("block/beam/nether_star_beam_block"),
                    VoyagerCore.id("block/beam/nether_star_beam_block"))
            .tooltips(Component.literal("Beam concentration: " + .1f * 100 + "%"))
            .register();

    public static final MachineDefinition BEE_HATCH = VoyagerCore.VOYAGERCORE_REGISTRATE.machine("bee_holder", holder -> new BeeHolderPartMachine(holder, IN))
            .langValue("Bee Holder")
            .tier(HV)
            .rotationState(RotationState.ALL)
            .abilities(VoyagerPartAbilities.BEE_HOLDER)
            .modelProperty(GTMachineModelProperties.IS_FORMED,false)
            .modelProperty(RecipeLogic.STATUS_PROPERTY, RecipeLogic.Status.IDLE)
            .model(createWorkableTieredHullMachineModel(GTCEu.id("block/machines/object_holder"))
                    .andThen((ctx, prov, model) -> {
                        model.addReplaceableTextures("bottom", "top", "side");
                    }))
            .register();


    public static void init() {};
}
