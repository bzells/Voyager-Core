package com.jzells.voyagercore.common.block;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.common.machine.multiblock.steam.ThermalSolarMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ReflectorBlock extends Block {
    public ReflectorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        ThermalSolarMachine therm = findClosestSolar(level, pos);
        if (therm != null && therm.attemptReflector(pos)) {
            therm.addReflectorPosition(pos);
            therm.updateReflectors(pos,false);
            therm.updateReflectorCount();
        }
    }

    public ModelData getModelData() {
        ModelData model = this.
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        return super.getShape(state, level, pos, context);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        ThermalSolarMachine thermal = findActiveThermal(level, pos);
        if (thermal != null) {
            thermal.removeReflectorPosition(pos);
            thermal.updateReflectors(pos, true);
            thermal.removeReflectorFromCache(pos);
            thermal.updateReflectorCount();
        };
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context);
    }

    protected ThermalSolarMachine findClosestSolar(Level level, BlockPos pos){
        double minDist = 0;
        ThermalSolarMachine closest = null;
        var thermals = ThermalSolarMachine.getThermalSolarSet();
        if (thermals.isEmpty()) return null;
        for (ThermalSolarMachine machine : thermals) {
            BlockPos mpos = machine.getPos();
            if (mpos.getY() <= pos.getY() || machine.getLevel() != level) continue;
//            if (machine.getCenterVec3() == null) continue;
            double dist = pos.distToCenterSqr(machine.getCenterVec3());
            if (dist < (machine.getRange() * machine.getRange())) {
                if (minDist == 0 || dist <= minDist) {
                    minDist = dist;
                    closest = machine;
                }
            }
        }
        if (minDist != 0) return closest;
        return null;
    }

    protected ThermalSolarMachine findActiveThermal(Level level, BlockPos pos){
        var thermals = ThermalSolarMachine.getThermalSolarSet();
        if (thermals.isEmpty()) return null;
        for (ThermalSolarMachine thermal : thermals){
            if (thermal.getLevel() != level) continue;
            if (thermal.getReflectorPositions().contains(pos) || thermal.getBlockCache().containsKey(pos)) {
                return thermal;
            }
        }
        return null;
    }
}
