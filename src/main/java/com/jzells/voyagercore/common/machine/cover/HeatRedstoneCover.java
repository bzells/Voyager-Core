package com.jzells.voyagercore.common.machine.cover;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.capability.IEnergyInfoProvider;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.common.cover.detector.DetectorCover;

import net.minecraft.core.Direction;

import org.jetbrains.annotations.Nullable;

public class HeatRedstoneCover extends DetectorCover {

    private float heat = 0;

    public HeatRedstoneCover(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
    }

    @Override
    protected void update() {
        sendSignal();
    }

    @Override
    public boolean canAttach() {
        return super.canAttach();
    }

    @Override
    public void onChanged() {
        super.onChanged();
        sendSignal();
    }

    public void setHeat(float heat) {
        this.heat = heat;
    }

    public void sendSignal() {
        int redstone = (int) (heat * 10);

        setRedstoneSignalOutput(redstone);
    }

    @Nullable
    protected IEnergyInfoProvider getEnergyInfoProvider() {
        return GTCapabilityHelper.getEnergyInfoProvider(coverHolder.getLevel(), coverHolder.getPos(),
                attachedSide);
    }
}
