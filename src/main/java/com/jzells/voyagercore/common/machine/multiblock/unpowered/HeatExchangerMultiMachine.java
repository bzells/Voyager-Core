package com.jzells.voyagercore.common.machine.multiblock.unpowered;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeatExchangerMultiMachine extends WorkableMultiblockMachine implements IDisplayUIMachine {

    private boolean counterflow;

    public HeatExchangerMultiMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        this.counterflow = counterflowCheck();
    }

    public boolean counterflowCheck() {
        Map<FluidHatchPartMachine, IO> fluidHatchMap = new HashMap<>();
        for (var part : this.getParts()) {
            if (part instanceof FluidHatchPartMachine fpart) {
                fluidHatchMap.put(fpart, fpart.tank.handlerIO);
            }
        }
        List<FluidHatchPartMachine> keys = List.copyOf(fluidHatchMap.keySet());
        FluidHatchPartMachine first = keys.get(0);
        IO firstIO = fluidHatchMap.get(first);
        BlockPos pos1 = first.getPos();
        for (int i = 1; i < fluidHatchMap.size(); i++) {
            FluidHatchPartMachine second = keys.get(i);
            IO secondIO = fluidHatchMap.get(second);
            BlockPos pos2 = second.getPos();
            if (!firstIO.equals(secondIO)) {
                BlockPos vec = pos1.subtract(pos2);
                BlockPos cross = vec.cross(this.getFrontFacing().getNormal());
                if (cross.equals(Vec3i.ZERO)) return true;
            }
        }
        return false;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        IDisplayUIMachine.super.addDisplayText(textList);
        if (counterflow) {
            textList.add(Component.literal("Counterflow enabled"));
        }
    }
}
