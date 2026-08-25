package com.jzells.voyagercore.common.machine.multiblock.steam;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.FancyMachineUIWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;

import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.jzells.voyagercore.util.debug.DebugVector;
import com.jzells.voyagercore.util.debug.DebugVectors;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.*;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ThermalSolarMachine extends WorkableMultiblockMachine implements IFancyUIMachine, IDisplayUIMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            ThermalSolarMachine.class, MultiblockControllerMachine.MANAGED_FIELD_HOLDER);

    @Getter
    // Probably should have this as blockPos.
    protected static Set<ThermalSolarMachine> thermalSolarSet = new HashSet<>();
    @Getter
    @Setter
    protected int reflectorCount = 0;
    @Getter
    @Setter
    @Persisted
    protected Set<BlockPos> reflectorPositions = new HashSet<>();
    @Getter
    @Setter
//    protected Set<BlockPos> reflectorCache = new HashSet<>();
    protected Map<BlockPos, Vec3> blockCache = new HashMap<>(); //intentionally nonpersistent;
    @Getter
    protected double range = 10d;
    @Getter
    protected Vec3 centerVec3;


    public ThermalSolarMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    public void updateReflectorCount(){
//        verifyReflectors();
        reflectorCount = reflectorPositions.size();
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        thermalSolarSet.add(this);
        updateReflectorCount();
        restoreCache();
        Direction dir = holder.getMetaMachine().getFrontFacing();
        centerVec3 = holder.getCurrentPos().getCenter().relative(dir.getOpposite(),1);
        //There's a weird bug that maybe exists
        //apparently a dummy machine gets loaded before the world loads
        //and the position is logged in this code...
        //If a player makes this multi near this dummy co-ord, it'll fuck up.
        // Another possible issue is that the dummy machine never get removed from mem.
        // Woo mem leaks :)
        // Woe to those that try to fix my fucked up code
    }

    public void addReflectorPosition(BlockPos pos){
        reflectorPositions.add(pos);
        addBlockToCache(pos);
    }

    public void removeReflectorPosition(BlockPos pos){
        reflectorPositions.remove(pos);
    }

    public void removeReflectorFromCache(BlockPos pos){
        blockCache.remove(pos);
    }

    public boolean attemptReflector(BlockPos pos) {
        if (getLevel() != null && !getLevel().canSeeSky(pos.above())) return false;
//        Direction dir = holder.getMetaMachine().getFrontFacing();
//        Vec3 mpos = holder.getCurrentPos().getCenter().relative(dir.getOpposite(),1);
        Vec3 mpos = getCenterVec3();
        Vec3 tpos = pos.getCenter().relative(Direction.UP,0.4);
        Vec3 nvec = mpos.vectorTo(tpos).normalize();
        Vec3 gap = nvec.scale(4);
        Vec3 tvec = mpos.add(gap);
        ClipContext bc = new ClipContext(tvec, tpos, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, null);
        BlockHitResult result = getLevel().clip(bc);


        boolean hit = result.getBlockPos().equals(pos);
        DebugVectors.VECTORS.put(String.valueOf(pos.hashCode()), new DebugVector(tvec, result.getLocation(), hit ? Color.GREEN : Color.RED, true));
        DebugVectors.VECTORS.put(pos.hashCode() + "target", new DebugVector(result.getLocation(), tpos, Color.GRAY, false));

        return hit;
    }

    ///Runs on world load, restores active blocks to block cache
    protected void restoreCache(){
        for (BlockPos pos : reflectorPositions){
            addBlockToCache(pos);
        }

    }

    ///
    protected void addBlockToCache(BlockPos pos) {
        Vec3 norm = calculateNormalToMachine(pos);
        blockCache.putIfAbsent(pos,norm);
    }

    protected Vec3 calculateNormalToMachine(BlockPos pos) {
//        Direction dir = holder.getMetaMachine().getFrontFacing();
//        Vec3 machine = holder.getCurrentPos().getCenter().relative(dir.getOpposite(),1);
        Vec3 machine = getCenterVec3();
        Vec3 reflector = pos.getCenter().relative(Direction.UP,0.5);
        Vec3 vec = machine.vectorTo(reflector);
        return vec.normalize();
    }

    protected boolean checkDirection(BlockPos pos1, BlockPos pos2, float sameness) {
        Vec3 n1 = blockCache.get(pos1);
        Vec3 n2 = blockCache.get(pos2);
        return n1.dot(n2) > sameness;
    }

    // I'm sorry
    public void updateReflectors(BlockPos blockPos, boolean removing) {
        for (BlockPos cache : blockCache.keySet()) {
            // Skip if the cached block is the checking block
            if (cache.equals(blockPos)) continue;
            // Skip the cached if the DotProd isn't similar
            if (!checkDirection(blockPos, cache, 0.98F)) continue;
            // If the altered Block is being added and the cached block is active, attempt check
            if (!removing && reflectorPositions.contains(cache) && !attemptReflector(cache)) {
                // If check returns false, remove cached from Active
                removeReflectorPosition(cache);
                continue;
            }
            // If the altered block is being removed, attempt check
            if (removing && attemptReflector(cache)){
                // Add it back to active if true
                addReflectorPosition(cache);
            }
        }
    }

    protected void checkReflectorsCanSeeSky() {
        Set<BlockPos> removal = new HashSet<>();
        for (BlockPos pos : reflectorPositions) {
            if (getLevel() != null && !getLevel().canSeeSky(pos)) {
                removal.add(pos);
            }
        }
        reflectorPositions.removeAll(removal);
        updateReflectorCount();
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        thermalSolarSet.remove(this);
        reflectorCount = 0;
    }

    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 182 + 8, 117 + 8);
        group.addWidget(new DraggableScrollableWidgetGroup(4, 4, 182, 117).setBackground(getScreenTexture())
                .addWidget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()))
                .addWidget(new ComponentPanelWidget(4, 17, this::addDisplayText)
                        .textSupplier(this.getLevel().isClientSide ? null : this::addDisplayText)
                        .setMaxWidthLimit(200)
                        .clickHandler(this::handleDisplayClick)));
        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        return new ModularUI(198, 208, this, entityPlayer).widget(new FancyMachineUIWidget(this, 198, 208));
    }

    @Override
    public void addDisplayText(List<Component> textList) {

        textList.add(Component.literal("Reflectors: %d".formatted(reflectorCount)));
    }
}
// lifecycle: Thermal Formed -> reflector placed -> reflector checks for valid thermal
//      -> onSuccess add normalized vector between machine+reflector to Map with key of BlockPos and add to reflCount
//      -> verify reflectors with norm dot prod > 0.98 (arbitrary, can tune)
//      -> if check fails, remove from reflCount, but keep in blockCache
//      -> on reflectorRemove, reflectPosition
//      -> check blockCache