package com.jzells.voyagercore.util;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.KubeJSPlugin;

public class VoyagerKJSIntegration extends KubeJSPlugin {

    public static Block getBlockFromKubeJSRegistry(String block) {
        return ForgeRegistries.BLOCKS.getValue(KubeJS.id(block));
    }
}
