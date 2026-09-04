package com.jzells.voyagercore.util;

import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;

import com.lowdragmc.lowdraglib.utils.BlockInfo;

import net.minecraft.world.level.block.Block;

import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;

public class VoyagerMultiblockUtils {

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
