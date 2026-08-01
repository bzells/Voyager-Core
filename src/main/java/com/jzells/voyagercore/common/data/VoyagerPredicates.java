package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;

import net.minecraft.world.level.block.entity.BlockEntity;

import com.google.common.collect.Sets;
import com.jzells.voyagercore.common.data.machines.IModuleProvider;

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
}
