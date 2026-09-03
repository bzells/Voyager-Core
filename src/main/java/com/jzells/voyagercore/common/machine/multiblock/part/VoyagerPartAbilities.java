package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;

import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.jzells.voyagercore.common.data.VoyagerBlocks;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public final class VoyagerPartAbilities {

    public static final PartAbility CRUSHING_WHEEL = new PartAbility("crushing_wheel");

    public static final PartAbility BEAM_LENS = new PartAbility("beam_lens");
    public static final PartAbility BEE_HOLDER = new PartAbility("bee_holder");
    public static final PartAbility HELPER_HOLDER = new PartAbility("helper_holder");
    public static final PartAbility PRECISE_ROBOT_ARM = new PartAbility("precise_robot_arm");

    public static final PartAbility PIPE_CASING = new PartAbility("pipe_casing");

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

//    where the fuck does this go GT docs?????????????
//    public static void register()
//    {
//        registerPipeCasing(GTValues.LV, GTBlocks.CASING_STEEL_PIPE);
//        registerPipeCasing(GTValues.MV, VoyagerBlocks.ALUMINIUM_PIPE_CASING);
//        registerPipeCasing(GTValues.HV, GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE);
//        registerPipeCasing(GTValues.EV, GTBlocks.CASING_TITANIUM_PIPE);
//        registerPipeCasing(GTValues.IV, GTBlocks.CASING_TUNGSTENSTEEL_PIPE);
//    }



    private VoyagerPartAbilities() {}
}
