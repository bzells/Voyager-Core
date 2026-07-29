package com.jzells.voyagercore.client.renderer.machine;

import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;
import com.jzells.voyagercore.client.renderer.machine.impl.SuperDonutRender;

public class VoyagerRenderHelper {
    public static DynamicRender<?,?> createSuperDonutRender() {
        return new SuperDonutRender();
    }
}
