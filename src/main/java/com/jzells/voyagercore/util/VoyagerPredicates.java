package com.jzells.voyagercore.util;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;

import com.lowdragmc.lowdraglib.utils.BlockInfo;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import com.google.common.collect.Sets;
import com.jzells.voyagercore.common.data.machines.IModuleProvider;
import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;

import java.util.Arrays;
import java.util.Set;

public class VoyagerPredicates {

    // TODO change it to give an error, but still form on wrong module
    public static TraceabilityPredicate module(MachineDefinition... def) {
        return new TraceabilityPredicate(blockWorldState -> {
            Set<IModuleProvider> modules = blockWorldState.getMatchContext().getOrCreate("moduleMachines",
                    Sets::newHashSet);
            BlockEntity blockEntity = blockWorldState.getTileEntity();
            if (blockEntity == null) return true;
            if (blockEntity instanceof IMachineBlockEntity machineBE &&
                    machineBE.getMetaMachine() instanceof IModuleProvider module &&
                    Arrays.stream(def).anyMatch(b -> b == machineBE.getDefinition())) {
                modules.add(module);
            }
            return true;
        }, null);
    }

    /// Causes a crash. It works on formation, but for SOME FUCKING REASON...
    public static TraceabilityPredicate partDirectional(PartAbility abilityOfDirectional, PartAbility ofBehindPart) {
        return new TraceabilityPredicate(
                multiblockState -> {
                    BlockState blockState = multiblockState.getBlockState();
                    Block block = blockState.getBlock();
                    if (!abilityOfDirectional.isApplicable(block)) {
                        return false;
                    }
                    for (var prop : blockState.getProperties()) {
                        if (!(prop instanceof DirectionProperty directionProperty)) continue;
                        BlockState opposite = multiblockState.getWorld().getBlockState(multiblockState.getPos()
                                .relative(blockState.getValue(directionProperty).getOpposite()));
                        if (ofBehindPart.isApplicable(opposite.getBlock())) return true;
                    }
                    return false;
                },
                () -> abilityOfDirectional.getAllBlocks().stream().map(BlockInfo::fromBlock).toArray(BlockInfo[]::new));
    }

    public static TraceabilityPredicate pipeCasingTraceable() {
        return new TraceabilityPredicate(
                blockWorldState -> {
                    Block block = blockWorldState.getBlockState().getBlock();

                    if (!VoyagerPartAbilities.PIPE_CASING.isApplicable(block)) {
                        return false;
                    }

                    Block current = blockWorldState.getMatchContext()
                            .getOrPut("pipe_casing", block);

                    return current == block;
                },
                () -> VoyagerPartAbilities.PIPE_CASING.getAllBlocks().stream()
                        .map(block -> BlockInfo.fromBlockState(block.defaultBlockState()))
                        .toArray(BlockInfo[]::new));
    }
}
