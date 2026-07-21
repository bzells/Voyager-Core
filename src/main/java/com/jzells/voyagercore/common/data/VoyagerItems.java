package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;

import com.jzells.voyagercore.common.machine.cover.HeatRedstoneCoverDefinition;
import com.tterrag.registrate.util.entry.ItemEntry;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;

public class VoyagerItems {

    public static ItemEntry<ComponentItem> COVER_HEAT_REDSTONE = VOYAGERCORE_REGISTRATE
            .item("heat_redstone_cover", ComponentItem::create)
            .lang("Heat Detector")
            .onRegister(item -> item.attachComponents(
                    new CoverPlaceBehavior(HeatRedstoneCoverDefinition.HEAT_REDSTONE_COVER)))
            .register();

    public static void init() {}
}
