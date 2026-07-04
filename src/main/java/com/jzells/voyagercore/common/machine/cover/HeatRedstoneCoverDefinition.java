package com.jzells.voyagercore.common.machine.cover;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.client.renderer.cover.ICoverRenderer;
import com.gregtechceu.gtceu.client.renderer.cover.SimpleCoverRenderer;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class HeatRedstoneCoverDefinition {

    private static CoverDefinition register(String id, CoverDefinition.CoverBehaviourProvider behaviorCreator) {
        return register(id, behaviorCreator, () -> () -> new SimpleCoverRenderer(GTCEu.id("block/cover/" + id)));
    }

    private static CoverDefinition register(String id, CoverDefinition.CoverBehaviourProvider behaviorCreator,
                                            Supplier<Supplier<ICoverRenderer>> coverRenderer) {
        return register(GTCEu.id(id), behaviorCreator, coverRenderer);
    }

    public static CoverDefinition register(ResourceLocation id, CoverDefinition.CoverBehaviourProvider behaviorCreator,
                                           Supplier<Supplier<ICoverRenderer>> coverRenderer) {
        var definition = new CoverDefinition(id, behaviorCreator, coverRenderer);
        GTRegistries.COVERS.register(definition.getId(), definition);
        return definition;
    }

    public static CoverDefinition HEAT_REDSTONE_COVER;

    public static void registerAll() {
        HEAT_REDSTONE_COVER = register(
                "heat_redstone_cover",
                HeatRedstoneCover::new,
                () -> () -> new SimpleCoverRenderer(GTCEu.id("block/cover/wireless_transmitter")));
    }
}
