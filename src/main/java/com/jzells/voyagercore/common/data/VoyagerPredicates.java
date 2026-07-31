package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;

import net.minecraft.world.level.block.entity.BlockEntity;

import com.google.common.collect.Sets;
import com.jzells.voyagercore.common.data.machines.IModuleProvider;

import java.util.Set;

public class VoyagerPredicates {

    // TODO add in filtering for the module using MachineDef
    public static TraceabilityPredicate module() {
        return new TraceabilityPredicate(blockWorldState -> {
            Set<IModuleProvider> modules = blockWorldState.getMatchContext().getOrCreate("moduleMachines",
                    Sets::newHashSet);
            BlockEntity blockEntity = blockWorldState.getTileEntity();
            if (blockEntity == null) return true;
            if (blockEntity instanceof IMachineBlockEntity machineBE &&
                    machineBE.getMetaMachine() instanceof IModuleProvider module) {
                ;
                modules.add(module);
                return true;
            } else return false;
        }, null) {
            // @Override //Testing if needed.
            // public boolean isAny() {
            // return true;
            // }
            //
            // @Override
            // public boolean addCache() {
            // return true;
            // }
        };
    }
}
