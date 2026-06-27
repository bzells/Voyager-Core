package com.jzells.voyagercore.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.KubeJSPlugin;

public class VoyagerKJSIntegration extends KubeJSPlugin {

    public static Block getBlockFromKubeJSRegistry(String block) {
        return ForgeRegistries.BLOCKS.getValue(KubeJS.id(block));
    }

    public static FluidStack getFluidStackFromKubeJSRegistry(String fluid) {
        Fluid kjsfluid = ForgeRegistries.FLUIDS.getValue(KubeJS.id(fluid));
        return new FluidStack(kjsfluid, 1);
    }
}
