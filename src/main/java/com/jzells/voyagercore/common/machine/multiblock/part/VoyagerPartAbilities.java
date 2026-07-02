package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;

import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public final class VoyagerPartAbilities {

    public static final PartAbility CRUSHING_WHEEL = new PartAbility("crushing_wheel");

    public static final PartAbility BEAM_LENS = new PartAbility("beam_lens");

    private static final Map<Block, Integer> CRUSHING_WHEEL_TIERS = new HashMap<>();

    private static final Map<Block, Integer> BEAM_LENSES = new HashMap<>();

    public static void registerCrushingWheel(int tier, Block block) {
        CRUSHING_WHEEL.register(tier, block);
        CRUSHING_WHEEL_TIERS.put(block, tier);
    }

    public static void registerBeamLens(float concentration, Block block) {
        BEAM_LENS.register(1, block);
        BEAM_LENSES.put(block, 1);
    }

    public static int getCrushingWheelTier(Block block) {
        return CRUSHING_WHEEL_TIERS.getOrDefault(block, 0);
    }

    private VoyagerPartAbilities() {}
}
