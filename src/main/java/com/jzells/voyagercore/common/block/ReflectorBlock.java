package com.jzells.voyagercore.common.block;

import com.jzells.voyagercore.common.machine.multiblock.steam.ThermalSolarMachine;
import com.jzells.voyagercore.util.debug.DebugVectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ReflectorBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ReflectorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
//        super.onPlace(state, level, pos, oldState, movedByPiston);
        ThermalSolarMachine therm = findClosestSolar(level, pos);
        if (therm != null && therm.attemptReflector(pos)) {
            therm.addReflectorPosition(pos);
            therm.updateReflectors(pos,false);
            therm.updateReflectorCount();
        }
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

//        DebugVectors.VECTORS.remove(String.valueOf(pos.hashCode()));
//        DebugVectors.VECTORS.remove(pos.hashCode() + "target");
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return super.getCollisionShape(state, level, pos, context);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected ThermalSolarMachine findClosestSolar(Level level, BlockPos pos){
        double minDist = 0;
        ThermalSolarMachine closest = null;
        var thermals = ThermalSolarMachine.getThermalSolarSet();
        if (thermals.isEmpty()) return null;
        for (ThermalSolarMachine machine : thermals) {
            BlockPos mpos = machine.getPos();
            if (mpos.getY() <= pos.getY() || machine.getLevel() != level) continue;
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
