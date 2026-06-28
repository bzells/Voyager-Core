package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.common.data.GCYMBlocks;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;

import net.minecraft.world.item.CreativeModeTab;

import com.jzells.voyagercore.VoyagerCore;
import com.tterrag.registrate.util.entry.RegistryEntry;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;

public class VoyagerCreativeModTab {

    public static RegistryEntry<CreativeModeTab> MAIN = VOYAGERCORE_REGISTRATE.defaultCreativeTab("main",
            builder -> builder
                    .displayItems(
                            new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("main", VOYAGERCORE_REGISTRATE))
                    .icon(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING::asStack)
                    .title(VOYAGERCORE_REGISTRATE.addLang("itemGroup", VoyagerCore.id("main"), "Voyager Core Blocks"))
                    .build())
            .register();
}
